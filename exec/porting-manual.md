
# GitLab 소스 클론 이후 빌드 및 배포할 수 있도록 정리한 문서

## 사용한 JVM, 웹서버, WAS, IDE 제품 종류, 설정, 버전

- 제품 및 버전 정보
    - open jdk 17
    - Spring Boot 3.3.3
    
    - React 18.3.1
    - Typescript 4.4.2
    - React-Query 5.56.2
    - Emotion/styled 11.13.0
    - Zustand 5.0.0-rc.2
    
    - MySQL 8.0.39
    - Redis 5.0.7
    - Nginx 1.18.0
    - Jenkins 2.480
    - Docker 27.2.0
    - Docker Compose 2.29.2
    
    - Visual Studio Code
    - IntelliJ IDEA Ultimate
    - RestDocs
    - Jira
    - Figma
    - GitLab
    - Mattermost
    - Notion
    - ERD Cloud

## 빌드 시 사용되는 환경 변수 등의 내용 상세 기재

- application-secret.yml
    
    ```yaml
    my-secret: "this is secret in develop server!"
    enable-header-auth: true
    
    spring:
      datasource:
        url: jdbc:mysql://172.17.0.1:3306/dbname?serverTimezone=Asia/Seoul&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true
        username: "{DB-USERNAME}"
        password: "{DB-PASSWORD}"
      ai:
        openai:
          api-key: "{OPEN-API-KEY}"
      data:
        redis:
          host: 172.17.0.1
          port: 6379
      session:
        redis:
          namespace: agijagi:session
      cloud:
        aws:
          s3:
            bucket: "{S3-BUCKET-NAME}"
          region:
            static: "{S3-REGION}"
          credentials:
            accessKey: "{S3-CREDENTIAL-ACCESS-KEY}"
            secretKey: "{S3-CREDENTIAL-SECRET-KEY}"
    ```
    

## 배포 시 특이사항

- 블루-그린 배포를 하려면 docker-compose에 포트와 색 정보를 주입해야 함
- Jenkins 블루-그린 배포 스크립트
    
    ```groovy
    sh(script: """docker ps -a""");
    sh """ echo 'before OLD_CONTAINER' """
    def OLD_CONTAINER = sh(script: """docker ps -a --format '{{.Names}}\\t{{.Status}}' | awk -F'\\t' '\$1 ~ /spring/ && \$2 !~ /Exited/ {print \$1}'""", returnStdout: true).trim()
    sh """ echo 'after OLD_CONTAINER' """
    
    def OLD_COLOR = 'green'
    def OLD_PORT = 8082
    def NEW_COLOR = 'blue'
    def NEW_PORT = 8081
    
    if (OLD_CONTAINER && OLD_CONTAINER.contains('blue')) {
    	echo 'Green!'
    	OLD_COLOR = 'blue'
    	OLD_PORT = 8081
    	NEW_COLOR = 'green'
    	NEW_PORT = 8082
    }
    
    def DOCKER_DIR = './backend/agijagi'
    
    sh """
    echo "OLD_COLOR=${OLD_COLOR}, NEW_COLOR=${NEW_COLOR}, NEW_PORT=${NEW_PORT}"
    
    echo "docker_dir => ${DOCKER_DIR}"
    
    cp "${DOCKER_DIR}/docker-compose.yml" "${DOCKER_DIR}/docker-compose-${OLD_COLOR}.yml"
    cp "${DOCKER_DIR}/docker-compose.yml" "${DOCKER_DIR}/docker-compose-${NEW_COLOR}.yml"
    
    sed -i "s/{COLOR}/${OLD_COLOR}/g" "${DOCKER_DIR}/docker-compose-${OLD_COLOR}.yml"
    sed -i "s/{PORT}/${OLD_PORT}/g" "${DOCKER_DIR}/docker-compose-${OLD_COLOR}.yml"
    sed -i "s/{COLOR}/${NEW_COLOR}/g" "${DOCKER_DIR}/docker-compose-${NEW_COLOR}.yml"
    sed -i "s/{PORT}/${NEW_PORT}/g" "${DOCKER_DIR}/docker-compose-${NEW_COLOR}.yml"
    
    cat "${DOCKER_DIR}/docker-compose-${OLD_COLOR}.yml"
    cat "${DOCKER_DIR}/docker-compose-${NEW_COLOR}.yml"
    
    docker-compose -f "${DOCKER_DIR}/docker-compose-${NEW_COLOR}.yml" up -d --build
    """
    
    timeout(time: 5, unit: 'MINUTES') {
    	waitUntil {
    		def response = sh(script: "curl -s http://localhost:${NEW_PORT}/actuator/health", returnStatus: true)
    		return response == 0
    	}
    }
    
    sh """
    docker-compose -f "${DOCKER_DIR}/docker-compose-${OLD_COLOR}.yml" down
    
    sudo sed -i "s/localhost:[0-9]*/localhost:${NEW_PORT}/g" /etc/nginx/sites-available/api.password926.site
    sudo nginx -t
    sudo nginx -s reload
    ```
    

---

# DB 덤프 파일 최신본

- Dump File Contents
    
    ```sql
    -- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
    --
    -- Host: localhost    Database: agijagi
    -- ------------------------------------------------------
    -- Server version	8.0.39-0ubuntu0.20.04.1
    
    /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
    /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
    /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
    /*!50503 SET NAMES utf8mb4 */;
    /*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
    /*!40103 SET TIME_ZONE='+00:00' */;
    /*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
    /*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
    /*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
    /*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
    
    --
    -- Current Database: `agijagi`
    --
    
    CREATE DATABASE /*!32312 IF NOT EXISTS*/ `agijagi` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
    
    USE `agijagi`;
    
    --
    -- Table structure for table `article`
    --
    
    DROP TABLE IF EXISTS `article`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `article` (
      `is_deleted` bit(1) NOT NULL,
      `created_at` datetime(6) NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `member_id` bigint NOT NULL,
      `content` varchar(255) NOT NULL,
      `title` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FK6l9vkfd5ixw8o8kph5rj1k7gu` (`member_id`),
      CONSTRAINT `FK6l9vkfd5ixw8o8kph5rj1k7gu` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `article`
    --
    
    LOCK TABLES `article` WRITE;
    /*!40000 ALTER TABLE `article` DISABLE KEYS */;
    /*!40000 ALTER TABLE `article` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `article_media`
    --
    
    DROP TABLE IF EXISTS `article_media`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `article_media` (
      `article_id` bigint NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `media_id` binary(16) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UKmla9ax0sovtpgovul63p6tuar` (`media_id`),
      KEY `FKaudccq0xg4ltnyi4ppn5qpds` (`article_id`),
      CONSTRAINT `FK75jsmnsrlrtso52lsw1ivkof5` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`),
      CONSTRAINT `FKaudccq0xg4ltnyi4ppn5qpds` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `article_media`
    --
    
    LOCK TABLES `article_media` WRITE;
    /*!40000 ALTER TABLE `article_media` DISABLE KEYS */;
    /*!40000 ALTER TABLE `article_media` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `child`
    --
    
    DROP TABLE IF EXISTS `child`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `child` (
      `birth_height` double DEFAULT NULL,
      `birth_weight` double DEFAULT NULL,
      `birthday` date NOT NULL,
      `is_deleted` bit(1) NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `image_id` binary(16) DEFAULT NULL,
      `gender` varchar(255) NOT NULL,
      `name` varchar(255) NOT NULL,
      `nickname` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UK76nbmahg2bnhjp239i4wn3fhf` (`image_id`),
      CONSTRAINT `FKr0wmqk11re156wy72if6cxq5o` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `child`
    --
    
    LOCK TABLES `child` WRITE;
    /*!40000 ALTER TABLE `child` DISABLE KEYS */;
    INSERT INTO `child` VALUES (51.2,3.5,'2024-05-24',_binary '\0',1,_binary 'G??\揚N＊청?8n\?,'?⑥븘','源?ㅼ슫','?꾧린?ㅼ슫'),(189,88.88,'2024-10-08',_binary '\0',2,_binary ')\홖졗뷛?w~촱\rF','?⑥븘','?꾧린?좎듅','?섎삓?쒕냸'),(51.2,3.5,'2024-05-24',_binary '',3,NULL,'?⑥븘','源?ㅼ슫','洹?붾?'),(105,4,'2024-01-01',_binary '\0',4,NULL,'?ъ븘','梨꾩뿰??,'?ㅼ콈');
    /*!40000 ALTER TABLE `child` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `comment`
    --
    
    DROP TABLE IF EXISTS `comment`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `comment` (
      `is_deleted` bit(1) NOT NULL,
      `created_at` datetime(6) NOT NULL,
      `id` bigint NOT NULL,
      `parent_comment_id` bigint DEFAULT NULL,
      `post_id` bigint NOT NULL,
      `writer_id` bigint NOT NULL,
      `content` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FKhvh0e2ybgg16bpu229a5teje7` (`parent_comment_id`),
      KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
      KEY `FKjfdaen6h2c8o1axvh7j6go3rq` (`writer_id`),
      CONSTRAINT `FKhvh0e2ybgg16bpu229a5teje7` FOREIGN KEY (`parent_comment_id`) REFERENCES `comment` (`id`),
      CONSTRAINT `FKjfdaen6h2c8o1axvh7j6go3rq` FOREIGN KEY (`writer_id`) REFERENCES `member` (`id`),
      CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `comment`
    --
    
    LOCK TABLES `comment` WRITE;
    /*!40000 ALTER TABLE `comment` DISABLE KEYS */;
    INSERT INTO `comment` VALUES (_binary '','2024-10-08 02:54:57.002474',1,NULL,2,1,'werwer'),(_binary '','2024-10-08 02:55:08.660787',2,NULL,2,1,'werwer'),(_binary '','2024-10-08 02:55:11.482328',3,NULL,2,1,'罹?),(_binary '','2024-10-08 02:55:16.236665',4,NULL,2,1,'?뉎뀋?담꽩?밤뀋'),(_binary '','2024-10-08 02:55:17.362690',5,NULL,2,1,'?곥뀍?룔꽰?덀꽬??),(_binary '','2024-10-08 02:55:18.146845',6,NULL,2,1,'?곥뀍?룔꽰?룔뀍??),(_binary '','2024-10-08 02:55:18.848054',7,NULL,2,1,'?곥꽬?덀꽰?덀꽬'),(_binary '','2024-10-08 02:55:45.353153',8,NULL,2,1,'?담꽩?뉎뀅'),(_binary '','2024-10-08 02:57:57.430177',9,NULL,2,1,'?곥꽩??),(_binary '','2024-10-08 02:58:56.301992',10,NULL,2,1,'t'),(_binary '','2024-10-08 02:58:57.513768',11,NULL,2,1,'45y'),(_binary '','2024-10-08 02:59:17.307588',12,NULL,2,1,'44'),(_binary '','2024-10-08 02:59:19.653301',13,NULL,1,1,'y'),(_binary '','2024-10-08 03:13:38.221742',14,NULL,4,1,'ppp'),(_binary '','2024-10-08 03:13:40.776215',15,NULL,4,1,'kk'),(_binary '','2024-10-08 03:13:42.339172',16,NULL,4,1,'jkkj'),(_binary '','2024-10-08 03:13:44.224387',17,NULL,4,1,'hjj'),(_binary '','2024-10-08 03:13:48.642343',18,NULL,4,1,'ljk'),(_binary '','2024-10-08 03:13:50.368243',19,NULL,4,1,'hhhhhhhhh'),(_binary '','2024-10-08 03:13:52.700208',20,NULL,4,1,'bbb'),(_binary '','2024-10-08 09:03:08.850153',21,NULL,2,1,'123'),(_binary '','2024-10-08 09:03:10.169838',22,NULL,2,1,'asd'),(_binary '','2024-10-08 09:03:11.672918',23,NULL,2,1,'zzz'),(_binary '','2024-10-08 09:53:03.233957',24,NULL,2,1,'==='),(_binary '\0','2024-10-08 10:25:28.860511',52,NULL,2,1,'怨좊냸 李??섎삓?섍쾶 ?앷꼈??),(_binary '','2024-10-08 15:53:18.873411',102,NULL,1,1,'gg'),(_binary '\0','2024-10-08 16:04:33.554207',103,NULL,2,1,'而ㅼ꽌 留덉씠 ?섎삓?섍쿋?붾뜲...'),(_binary '','2024-10-10 16:23:25.531616',152,NULL,7,1,'?덈Т,,,?꾩??섎뒗,,,?뺣낫?낅땲??,, ^~^'),(_binary '\0','2024-10-10 16:24:35.388261',153,NULL,7,3,'?덈Т,,,?꾩??섎뒗,,,,?뺣낫?ㅼ슂 ^~^');
    /*!40000 ALTER TABLE `comment` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `comment_seq`
    --
    
    DROP TABLE IF EXISTS `comment_seq`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `comment_seq` (
      `next_val` bigint DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `comment_seq`
    --
    
    LOCK TABLES `comment_seq` WRITE;
    /*!40000 ALTER TABLE `comment_seq` DISABLE KEYS */;
    INSERT INTO `comment_seq` VALUES (251);
    /*!40000 ALTER TABLE `comment_seq` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `diary`
    --
    
    DROP TABLE IF EXISTS `diary`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `diary` (
      `is_deleted` bit(1) NOT NULL,
      `wrote_at` date NOT NULL,
      `child_id` bigint NOT NULL,
      `created_at` datetime(6) NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `member_id` bigint NOT NULL,
      `content` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FK10gocfq6ly7wdtbmyomeao4a` (`child_id`),
      KEY `FKbyluyva0mxnf5jitf297oxlxd` (`member_id`),
      CONSTRAINT `FK10gocfq6ly7wdtbmyomeao4a` FOREIGN KEY (`child_id`) REFERENCES `child` (`id`),
      CONSTRAINT `FKbyluyva0mxnf5jitf297oxlxd` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `diary`
    --
    
    LOCK TABLES `diary` WRITE;
    /*!40000 ALTER TABLE `diary` DISABLE KEYS */;
    INSERT INTO `diary` VALUES (_binary '\0','2024-10-03',1,'2024-10-07 17:02:38.115081',1,1,'?ㅻ뒛 ?곕━ ?꾧린???됱냼蹂대떎 ???좊뱾?덈떎. ?좎갑?명삎??瑗??뚯뼱?덇퀬 ?먮뒗 紐⑥뒿???덈Т ?щ옉?ㅻ읇??'),(_binary '\0','2024-10-04',1,'2024-10-07 17:03:09.502222',2,1,'?대텋 ?띿뿉 ???⑥? ?곕━ ?꾧린\r\n?뱀븣?대?  ?섎뒗 寃?媛숇떎. \'?꾨쭏\' ?쇨퀬 遺덈윭二쇰뒗 ?좎씠 ?ㅺ린瑜?.'),(_binary '\0','2024-10-05',1,'2024-10-07 17:03:48.589791',3,1,'?곕━ ?꾧린???대뵖媛???ㅼ뼱媛??寃껋쓣 醫뗭븘?쒕떎. ?됯툑?됯툑 湲곗뼱??諛붽뎄?덉뿉 ???ㅼ뼱媛붾떎.'),(_binary '\0','2024-10-06',1,'2024-10-07 17:04:39.770580',4,1,'?ㅻ뒛? ?곕━ ?꾧린???섎뱾?대? 媛붾떎. ?ㅻ옖留뚯뿉 ?몄텧?대씪 湲곕텇??醫뗭븯?붿? ?앷??앷? ?껉퀬 ?덈떎.'),(_binary '\0','2024-10-08',2,'2024-10-08 01:22:51.207374',5,1,'留롮씠 而몃떎'),(_binary '\0','2024-10-08',1,'2024-10-08 01:27:12.446633',6,1,'?ㅻ뒛 ?≪븘???덈Т ?섎뱾?덈떎. 怨꾩냽 ?멸퀬 ?좊룄 蹂댁콈怨?. ?댁젣 ??醫 ?먯옄 ?꾧???.'),(_binary '\0','2024-10-07',1,'2024-10-08 10:31:25.211199',7,1,'?꾨Т嫄곕굹 吏묒뼱 癒뱀쑝硫??덈릺?붾뜲 ?닿컝?대? ?섎뒗吏 蹂댁씠??寃껊쭏???낆뿉 ?ｋ뒗??. ?뱀떆 紐⑤Ⅴ??議곗떖 ??議곗떖?댁빞吏'),(_binary '\0','2024-09-01',1,'2024-10-08 11:48:40.540617',8,1,'?ㅻ뒛? ?곕━ ?꾧린 100?쇱씠?? 100??湲곕뀗?쇰줈 ?ㅽ뒠?붿삤?먯꽌 ?ъ쭊??李띿뿀?? 苑껋쿂???댁걯??'),(_binary '\0','2024-09-03',1,'2024-10-08 11:51:33.583640',9,1,'?ㅻ뒛 ?≪븘???꾨튌 ?대떦?롢뀕 ?닿렐 ???≪븘 異쒓렐 ?꾨즺! ?좊뱺 ?곕━ ?꾧? ?낃퀬 ?ㅺ굅吏???섍퀬 硫뗭???),(_binary '\0','2024-09-04',1,'2024-10-08 11:52:09.775625',10,1,'?ㅻ뒛? ?곕━ ?꾧린瑜??꾪빐 臾대Ⅸ 二쎌쓣 留뚮뱾?덈떎. 留쏆엳寃?癒밴퀬 嫄닿컯?섍쾶 ?먮씪湲?),(_binary '\0','2024-09-07',1,'2024-10-08 11:53:22.972857',11,1,'?꾩씠? ?④퍡 媛議??⑥껜 ?ъ쭊??李띿뿀?? ???놁뿉???꾨쭏 ?꾨튌??臾댄븳 戮戮瑜?諛쏆? ?곕━ ?꾧린 ?щ옉 ?щ퓤 諛쏄퀬 嫄닿컯?섍쾶 ?먮씪??'),(_binary '\0','2024-09-06',1,'2024-10-08 11:54:13.834713',12,1,'諛붿걶 ?以묒뿉???꾩씠媛 ?꾨튌 蹂닿퀬?띕떎怨??곕땲源??덇퀬 ?낅Т ?섎뒗 ?꾨튌. ??⑦빐??),(_binary '\0','2024-09-08',1,'2024-10-08 11:55:03.569853',13,1,'?덇렐?덇렐 ?좊뱺 ?곕━ ?꾧린 ?ㅻ뒛? ?좏닾???덊븯怨?怨ㅽ엳 ???좊뱾?덈떎. ?≪븘 ?닿렐..'),(_binary '\0','2024-09-24',1,'2024-10-08 14:39:30.199817',14,1,'?ㅻ뒛 ?꾧린媛 怨좉컻瑜??꾩쟾???ㅼ뼱 二쇰????섎윭蹂대뒗 紐⑥뒿??蹂댁븯?? ?댁젣 怨좉컻瑜????뚮쭏??洹좏삎?????↔퀬 ?덉뿀?? ?먯쑝濡??λ궃媛먯쓣 ?≪쑝?ㅻ뒗 ?쒕룄瑜??먯＜ ?덇퀬, ?먭??쎌쓣 ?좉린????諛붾씪蹂대뒗 紐⑥뒿??洹?ъ썱?? ?뱁엳 ?덉쓣 留덉＜移??뚮쭏???묒? 誘몄냼瑜?吏볥뒗?? 洹??껋쓬?뚮━媛 李??щ옉?ㅻ윭?좊떎. ?섎（ 醫낆씪 ?꾧린? ?덉쓣 留욎텛硫??됰났???쒓컙??蹂대깉??'),(_binary '\0','2024-09-25',1,'2024-10-08 14:40:18.035817',15,1,'?ㅻ뒛? ?꾧린媛 ??옞??議곌툑 ???먯꽌 ?ㅽ썑???쎄컙 ?쇨낀??蹂댁??? 洹몃옒???대텋 ?꾩뿉???쒕컻?섍쾶 援대윭?ㅻ땲怨? ?놁쑝濡?紐몄쓣 ?ㅼ쭛?쇰젮???쒕룄??蹂댁??? ?덈줈???뚮━???뚯븙???ㅻ┫ ?뚮쭏??洹瑜?湲곗슱?대ŉ 諛섏쓳?덇퀬, ?먮컻??鍮좊Ⅴ寃??吏곸씠??紐⑥뒿??臾댁쿃 洹?ъ썱?? ??곸뿉???쇨낀?덈뒗吏 ?됱냼蹂대떎 ?쇱컢 ?좊뱾?덈떎.'),(_binary '\0','2024-09-26',1,'2024-10-08 14:41:32.134732',16,1,'?덈줈 ???좊겮 紐⑥옄瑜??⑤뇬?? ?ㅻ뒛? ?뚮━ ?섎뒗 ?λ궃媛먯쓣 蹂닿퀬 ?꾧린媛 ?먯＜ ?껋뿀?? ?λ궃媛먯쓽 ?됯퉼怨??뚮━??諛섏쓳?섎ŉ ?붿쓣 六쀪퀬 ?먯쑝濡??≪쑝?ㅻ뒗 ?쒕룄媛 ?덉뿉 ?꾧쾶 留롮븘議뚮떎. ?닿? 留먯쓣 嫄몃㈃ ?꾧린???뚮━瑜??대ŉ ??듯븯?ㅻ뒗 寃껋쿂??蹂댁??? ?ㅻ뒛 ?섎（???꾧린???援먭컧??源딆뼱吏??먮굦?댁뿀??'),(_binary '\0','2024-09-28',1,'2024-10-08 14:42:06.391320',17,1,'?꾩묠 遺꾩쑀瑜?二쇰땲 ?ㅻ뒛? ?꾩＜ ??癒뱀뿀?? ?낆뿉 臾쇰━?먮쭏???댁떖??鍮⑤㈃???덉쓣 媛?섍쾶 ?④퀬 ?섎? 諛붾씪蹂대뒗 紐⑥뒿???щ옉?ㅻ윭?좊떎. 遺꾩쑀瑜?留덉떆??留먭퀬 ?좎떆 硫덉텛?붾땲 ?먯쑝濡?遺꾩쑀蹂묒쓣 留뚯??묎굅由щŉ ?ㅼ떆 鍮④린 ?쒖옉?덈떎. ?꾧린媛 癒밸뒗 ?띾룄媛 苑?鍮⑤씪議뚭퀬, ??癒밴퀬 ?섏꽌???쒕룞??遺꾩쑀蹂묒쓣 ?먯쑝濡??↔퀬 ??섎떎. 留뚯”?ㅻ윭???쒖젙?쇰줈 ?몃┝???섍퀬??湲덉꽭 湲곕텇 醫뗭? ?쒖젙??吏?덈떎.'),(_binary '\0','2024-10-01',1,'2024-10-08 15:58:47.123762',18,1,'?ㅻ뒛 ?꾧린媛 怨좉컻瑜??꾩쟾???ㅼ뼱 二쇰????섎윭蹂대뒗 紐⑥뒿??蹂댁븯?? ?댁젣 怨좉컻瑜????뚮쭏??洹좏삎?????↔퀬 ?덉뿀?? ?먯쑝濡??λ궃媛먯쓣 ?≪쑝?ㅻ뒗 ?쒕룄瑜??먯＜ ?덇퀬, ?먭??쎌쓣 ?좉린????諛붾씪蹂대뒗 紐⑥뒿??洹?ъ썱?? ?뱁엳 ?덉쓣 留덉＜移??뚮쭏???묒? 誘몄냼瑜?吏볥뒗?? 洹??껋쓬?뚮━媛 李??щ옉?ㅻ윭?좊떎. ?섎（ 醫낆씪 ?꾧린? ?덉쓣 留욎텛硫??됰났???쒓컙??蹂대깉??'),(_binary '\0','2024-10-02',1,'2024-10-08 15:59:10.891971',19,1,'?ㅻ뒛? ?꾧린媛 ?덈줈???λ궃媛먯쓣 諛쏆븘 ?섎（ 醫낆씪 洹멸쾬??吏묒쨷?덈떎. ?λ궃媛먯쓣 ?먯뿉 伊먭퀬 ?ㅼ뒪濡??뚮━瑜??대ŉ 利먭굅?뚰븯??紐⑥뒿???몄긽?곸씠?덈떎. 怨좉컻瑜??먯쑀濡?쾶 ?뚮━硫?二쇰????댄뵾???쒓컙??留롮븘議뚭퀬, ?뚮━? 鍮쏆뿉 ???諛섏쓳???먯젏 ??紐낇솗?댁죱?? ?덉씠 留덉＜移??뚮쭏??誘몄냼瑜?吏?쇰ŉ ?멸린??媛?앺븳 ?섎（瑜?蹂대깉??'),(_binary '\0','2024-10-09',1,'2024-10-09 15:12:51.221338',20,1,'?ㅻ뒛? ?щ윭 媛쒖쓽 ?묒? ?λ궃媛먯쓣 二쇱뿀?붾땲 ?섎굹?섎굹 ?ㅻⅤ寃?諛섏쓳?덈떎. ?몃옉?대? ?붾뱾硫??뚮━瑜??ｋ뒗媛 ?섎㈃, 泥쒖쑝濡????λ궃媛먯? ?먯쑝濡?瑗?伊먭퀬 留뚯??묎굅?몃떎. ?댁젣???λ궃媛먯쓣 ?먯뿉???쎄쾶 ?볦? ?딄퀬 ?ㅻ옒 ?↔퀬 ?덈뒗 紐⑥뒿???몄긽?곸씠?덈떎. ?뱁엳 ?덉뿉 ?꾨뒗 嫄? ?λ궃媛먯씠 ?⑥뼱吏硫??ㅼ떆 ?≪쑝?ㅺ퀬 ?먯쓣 六쀫뒗 紐⑥뒿?댁뿀?? ?섎（?섎（ ?꾧린媛 ?λ궃媛먭낵 ?곹샇?묒슜?섎뒗 諛⑹떇???щ씪吏怨??덈뒗 寃??좉린?덈떎.'),(_binary '','2024-10-10',1,'2024-10-10 09:38:50.468768',21,1,'?뚯뒪???뚯뒪??),(_binary '','2024-10-10',1,'2024-10-10 09:38:52.207041',22,1,'?뚯뒪???뚯뒪??),(_binary '','2024-10-10',1,'2024-10-10 09:39:37.284719',23,1,'?뚯뒪??),(_binary '','2024-10-10',1,'2024-10-10 09:41:19.494208',24,1,''),(_binary '','2024-10-10',1,'2024-10-10 09:41:20.267831',25,1,''),(_binary '','2024-10-10',1,'2024-10-10 09:41:21.078209',26,1,''),(_binary '','2024-10-10',1,'2024-10-10 09:41:21.661159',27,1,''),(_binary '','2024-10-10',1,'2024-10-10 09:44:38.251416',28,1,'?뗣뀑??),(_binary '','2024-10-10',1,'2024-10-10 09:44:49.543842',29,1,'?뗣뀑??),(_binary '','2024-10-10',1,'2024-10-10 10:21:17.213311',30,1,'?꾧린?먭린???ㅼ떊寃껋쓣 ?섏쁺?⑸땲??'),(_binary '','2024-09-30',1,'2024-10-10 10:45:29.770715',31,1,'?뉎뀋??),(_binary '','2024-09-30',1,'2024-10-10 10:50:11.078318',32,1,'?뚣뀉??),(_binary '','2024-09-30',1,'2024-10-10 10:50:29.371779',33,1,'?뚣뀉??),(_binary '','2024-09-30',1,'2024-10-10 10:51:27.921154',34,1,'?뚣뀉??),(_binary '','2024-09-30',1,'2024-10-10 10:51:57.749481',35,1,'?뚣뀒?끹뀒'),(_binary '','2024-09-30',1,'2024-10-10 10:52:36.865962',36,1,'?뚯뒪??),(_binary '','2024-09-30',1,'2024-10-10 10:53:14.944013',37,1,'?뚯뒪??),(_binary '','2024-09-30',1,'2024-10-10 10:54:01.985112',38,1,'?뚯뒪??),(_binary '','2024-09-30',1,'2024-10-10 10:54:23.677666',39,1,'?뚯뒪??),(_binary '','2024-09-30',1,'2024-10-10 10:54:45.908305',40,1,'?뚯뒪??),(_binary '','2024-10-10',1,'2024-10-10 13:15:58.367828',41,1,'?곕━ ?꾩씠 理쒓퀬!'),(_binary '\0','2024-10-10',1,'2024-10-10 13:21:17.456611',42,1,'?곕━ ?꾩씠 理쒓퀬'),(_binary '\0','2024-02-24',1,'2024-10-10 14:01:49.666844',43,1,'?ㅻ뒛 珥덉쓬?뚮줈 ?곕━ ?꾧린???쇨뎬??泥섏쓬 遊ㅻ떎. ?묒? ?먮컻???吏곸씠??紐⑥뒿???뺣쭚 ?щ옉?ㅻ윭?좉퀬, 嫄닿컯?섍쾶 ?먮씪怨??덈떎???뚯떇??留덉쓬???볦씤?? ?꾨튌濡쒖꽌 ?꾧린瑜?留뚮궇 ?좎씠 ?먯젏 ??湲곕떎?ㅼ쭊???롢뀕'),(_binary '\0','2024-05-24',1,'2024-10-10 14:14:53.997896',44,1,'?ㅻ뒛, ?쒕뵒???곕━ ?꾧린瑜?留뚮궗?? ?묒? ?먯쑝濡????먭??쎌쓣 瑗?伊먭퀬 ?덈뒗 紐⑥뒿??留먮줈 ???????녿뒗 媛먮룞???먭펷?? ?곕━ 媛議깆쓽 ?덈줈???쒖옉??踰뚯뜥遺???ㅻ젅怨? ?욎쑝濡쒖쓽 ?쒓컙???뺣쭚 湲곕??쒕떎.');
    /*!40000 ALTER TABLE `diary` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `diary_media`
    --
    
    DROP TABLE IF EXISTS `diary_media`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `diary_media` (
      `diary_id` bigint NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `media_id` binary(16) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UK5cyolxdixnj52okmm5kuum67w` (`media_id`),
      KEY `FKivep7kefn3x37xtoknsno010m` (`diary_id`),
      CONSTRAINT `FKivep7kefn3x37xtoknsno010m` FOREIGN KEY (`diary_id`) REFERENCES `diary` (`id`),
      CONSTRAINT `FKmxfi0wkbkvjmdk12u9ct8vemq` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `diary_media`
    --
    
    LOCK TABLES `diary_media` WRITE;
    /*!40000 ALTER TABLE `diary_media` DISABLE KEYS */;
    INSERT INTO `diary_media` VALUES (1,1,_binary ';\휦@]\?찞퓧?풺'),(2,2,_binary 'Cp\Z?폠&??\????),(3,3,_binary '\???\?굘Y-a\?\?'),(4,4,_binary '\??MhK컝\?Ft촩j'),(6,9,_binary '?'얷>딫?\?\?\??),(7,11,_binary ' )??폀rS?멭\?),(5,12,_binary '?}*q\?????z\?'),(5,13,_binary '*A}?N쩦\?"\癖y\?),(8,14,_binary '\????M+?|?:8?),(9,15,_binary 's랠?L\\퀷c?푉'),(10,16,_binary ':e?@R?溯iZx7'),(11,17,_binary '\\&1펁\??~B?['),(12,18,_binary '깢\?9\?혤\?\??),(13,19,_binary '?\?*DH릵Y끌~?_'),(14,20,_binary 'E\??-YA\"븄???\?),(15,21,_binary '쐂R; F.쎙ehu@\?),(16,22,_binary '?A(킏MY?얭?\?),(17,23,_binary '+\??\?F．\??쐊'),(18,24,_binary '~\0??쟆]뭴\??\??),(19,25,_binary '??꼘?리\?憔\??''),(20,48,_binary ')\\IP\?\????\\'),(42,50,_binary 'y,\?\툽E둀\???'),(43,51,_binary '?錢1B;BB뿤????),(44,52,_binary '뮛1\?휯B[쪾\?^V');
    /*!40000 ALTER TABLE `diary_media` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `growth`
    --
    
    DROP TABLE IF EXISTS `growth`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `growth` (
      `height` double NOT NULL,
      `month` int NOT NULL,
      `weight` double NOT NULL,
      `child_id` bigint NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      PRIMARY KEY (`id`),
      KEY `FKtjd50qvlo4mc0m48vw2kcfqbp` (`child_id`),
      CONSTRAINT `FKtjd50qvlo4mc0m48vw2kcfqbp` FOREIGN KEY (`child_id`) REFERENCES `child` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `growth`
    --
    
    LOCK TABLES `growth` WRITE;
    /*!40000 ALTER TABLE `growth` DISABLE KEYS */;
    INSERT INTO `growth` VALUES (15,0,2,1,1),(40,1,4.2,1,2),(50,2,5,1,3),(60,3,5.9,1,4),(68,4,7.8,1,5);
    /*!40000 ALTER TABLE `growth` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `image`
    --
    
    DROP TABLE IF EXISTS `image`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `image` (
      `id` binary(16) NOT NULL,
      PRIMARY KEY (`id`),
      CONSTRAINT `FK8tbcatdpwrvwjlmst84ykwq22` FOREIGN KEY (`id`) REFERENCES `media` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `image`
    --
    
    LOCK TABLES `image` WRITE;
    /*!40000 ALTER TABLE `image` DISABLE KEYS */;
    INSERT INTO `image` VALUES (_binary '쐂R; F.쎙ehu@\?),(_binary '\???F??똄e챇'),(_binary '戮?A\粹???S'),(_binary '*A}?N쩦\?"\癖y\?),(_binary 's랠?L\\퀷c?푉'),(_binary ' )??폀rS?멭\?),(_binary '!\?lL쯓&춎F\??燦'),(_binary '&\生덩D\脆??t\?힓'),(_binary ')\\IP\?\????\\'),(_binary ')\홖졗뷛?w~촱\rF'),(_binary '+\??\?F．\??쐊'),(_binary '.DH>?N???\?\?),(_binary ':e?@R?溯iZx7'),(_binary ';\휦@]\?찞퓧?풺'),(_binary 'Cp\Z?폠&??\????),(_binary 'DI%gMG벖,Q???),(_binary 'E\??-YA\"븄???\?),(_binary 'G??\揚N＊청?8n\?),(_binary 'INu\흕껱˛na\?['),(_binary '\\&1펁\??~B?['),(_binary 'c혗#H쨲\烏~???),(_binary 'd=풗\?㈌&鄕??'),(_binary 'g??\Kァ\液\墓?b'),(_binary 'g\???聘\??\?\럝'),(_binary 'j퉔?It???둇\?),(_binary 'kA쑀\?\侏O\?u;?),(_binary 'o+|W쟏/\?_,\?Q'),(_binary 'p\??짡\?\?\?덠'),(_binary 'w쁬#%첛꼯쳪\훋\?\?),(_binary 'y,\?\툽E둀\???'),(_binary '~\0??쟆]뭴\??\??),(_binary '?~쩎G?~a\?ROp'),(_binary '깢\?9\?혤\?\??),(_binary '?'g???e┾샇???),(_binary '?'얷>딫?\?\?\??),(_binary '??L춣I戟?0.\?\?'),(_binary '?\?:;C\琇\???.\r'),(_binary '뭹C?\?뢲(\휯qUw'),(_binary '뮛1\?휯B[쪾\?^V'),(_binary '?\?*DH릵Y끌~?_'),(_binary '?껌\?컮\??T)\?),(_binary '?`??L=둏Z쩳??),(_binary '??꼘?리\?憔\??''),(_binary '럽m?DE쑇\?\?酩'),(_binary '?\\??I듋???-'),(_binary '?봳&jIm?쥶뾏2B'),(_binary '?X匍켔??P?\勵L'),(_binary '?}*q\?????z\?'),(_binary '\????M+?|?:8?),(_binary '\??MhK컝\?Ft촩j'),(_binary '慷KM\?Jm?\?藪p?'),(_binary '?뼾%턅?\쩡???'),(_binary '\?\肄\"\?\?\??Z?'),(_binary '\??cF?펰\?넞0'),(_binary '\酢?얤쌳1??n#'),(_binary '\???\?굘Y-a\?\?'),(_binary '?y꼳@s?\??LA'),(_binary '?A(킏MY?얭?\?),(_binary '?錢1B;BB뿤????);
    /*!40000 ALTER TABLE `image` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `invitation_code`
    --
    
    DROP TABLE IF EXISTS `invitation_code`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `invitation_code` (
      `is_deleted` bit(1) NOT NULL,
      `child_id` bigint NOT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `id` binary(16) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `invitation_code`
    --
    
    LOCK TABLES `invitation_code` WRITE;
    /*!40000 ALTER TABLE `invitation_code` DISABLE KEYS */;
    INSERT INTO `invitation_code` VALUES (_binary '\0',1,'2024-10-08 09:42:51.841079',_binary '슧딳\ZJ뾼$횖\r?\\r'),(_binary '\0',1,'2024-10-08 09:56:29.262371',_binary 'q[뭸묲???i긂'),(_binary '\0',1,'2024-10-08 09:40:33.457279',_binary '*\娟?Lr켥늩U\?),(_binary '\0',1,'2024-10-08 10:23:43.810117',_binary '2???L솮?뢡\誰?),(_binary '\0',1,'2024-10-08 09:53:55.038720',_binary 'p?MI$쭼m?'),(_binary '\0',1,'2024-10-08 09:47:25.815752',_binary '륻\?섿?Z\0肴\?\?),(_binary '\0',1,'2024-10-08 14:36:32.686981',_binary '%\??z퐕{?X\??),(_binary '\0',1,'2024-10-08 09:45:38.001017',_binary '??????c댃~'),(_binary '\0',1,'2024-10-08 09:45:48.489959',_binary ',\"~}sC\?p\?2??),(_binary '\0',1,'2024-10-08 09:42:17.146464',_binary '\"\??9F@i?rp?\??),(_binary '\0',1,'2024-10-08 09:41:27.758938',_binary '#c#?@쨜#꽗B'),(_binary '\0',1,'2024-10-08 09:59:46.027990',_binary '$\??RO?E7?悸'),(_binary '\0',1,'2024-10-08 09:39:24.903098',_binary ')Z?\??<냢\');v6'),(_binary '\0',1,'2024-10-08 09:54:48.182850',_binary '-V\?\?Ok빍A6??\?),(_binary '\0',1,'2024-10-08 09:45:12.252941',_binary '/V8x\?L?m7\??'),(_binary '\0',1,'2024-10-08 09:58:05.720616',_binary '0\?\?CC?!<?bR\?),(_binary '\0',1,'2024-10-09 16:49:46.075171',_binary '1\?꺄B=첛??x$?),(_binary '\0',1,'2024-10-08 09:41:23.690594',_binary '2\?쪭@^뻒0)劇?'),(_binary '\0',1,'2024-10-08 09:42:22.298848',_binary '2\??I@\?\n?r㎺?),(_binary '\0',1,'2024-10-08 10:53:47.661937',_binary '8嶸뻾1@I?0^?),(_binary '\0',1,'2024-10-10 15:19:45.533921',_binary ';폊\0숹H?촟B?5F'),(_binary '\0',1,'2024-10-09 15:34:10.374604',_binary 'Bg5卵J?+UO?'),(_binary '\0',1,'2024-10-08 09:54:08.478016',_binary 'Fa???@?R\??g'),(_binary '\0',1,'2024-10-08 09:53:28.320918',_binary 'G??\?M뺘\??뻏-?),(_binary '\0',1,'2024-10-08 09:41:41.528162',_binary 'K`&:\?Es?(\?x\?),(_binary '\0',1,'2024-10-08 09:52:27.401046',_binary 'N^8뱲?E뙾?;g?),(_binary '\0',1,'2024-10-08 09:47:59.609767',_binary 'N?B\헞─??쑬쿥'),(_binary '\0',1,'2024-10-08 09:57:00.879053',_binary 'W놝5DuH\?\?\???'),(_binary '\0',1,'2024-10-08 09:56:45.585925',_binary 'W쿂b\?D%퀕)	\?Z?),(_binary '\0',1,'2024-10-08 09:57:20.524109',_binary 'W\??IhF棄???'),(_binary '\0',1,'2024-10-10 10:43:02.824792',_binary 'XQ\????\?f\態'),(_binary '\0',1,'2024-10-10 15:53:12.546061',_binary 'Z칯?H???!뮙'),(_binary '\0',1,'2024-10-08 09:57:30.870799',_binary '\\S??N拷|zf?킼'),(_binary '\0',1,'2024-10-08 09:53:26.007304',_binary '\\뛟m0ON)?Y@_\??),(_binary '\0',1,'2024-10-08 09:55:57.388459',_binary 'aYd푪N?n걸뇽쮻'),(_binary '\0',1,'2024-10-09 16:50:14.273490',_binary 'c(????Y#뫙z'),(_binary '\0',1,'2024-10-08 09:55:42.771877',_binary 'f\"뉿??,??w%??'),(_binary '\0',1,'2024-10-08 09:56:51.298416',_binary 'f뀎\?F뜙[\?뾑'),(_binary '\0',1,'2024-10-08 14:45:14.500647',_binary 'rZ좫쬍퍧za\?'),(_binary '\0',1,'2024-10-08 09:41:16.048072',_binary 'ue<뜾rE츍g커쩀\??),(_binary '\0',1,'2024-10-08 09:41:19.983409',_binary 'v?Q\??究??c\?'),(_binary '\0',1,'2024-10-08 09:58:11.795561',_binary 'w?\?kG뿑顚????),(_binary '\0',1,'2024-10-08 09:56:04.992158',_binary 'zZ%?r쭴뒓9\'???\?),(_binary '\0',1,'2024-10-08 09:43:42.495562',_binary 'z?D?JB?k\?\榮\?),(_binary '\0',1,'2024-10-08 09:55:33.584337',_binary '{EeR촂A랝{	aA>'),(_binary '\0',1,'2024-10-08 09:40:37.920622',_binary '~ ?wIA??U\?\?),(_binary '\0',1,'2024-10-08 14:29:12.366331',_binary '?\???삋??L뻿'),(_binary '\0',1,'2024-10-08 09:44:33.302215',_binary '뇦X?L\?퍇2~ \n?),(_binary '\0',1,'2024-10-08 09:39:27.191218',_binary '뎛빒A$??3!e넮'),(_binary '\0',1,'2024-10-10 15:05:19.994423',_binary '땑퉪A\?O????L'),(_binary '\0',1,'2024-10-08 09:56:35.749505',_binary '??\?M?\?IP촜\?),(_binary '\0',1,'2024-10-08 14:26:52.484030',_binary '똉=\??N뿘e豊fW6?),(_binary '\0',1,'2024-10-08 09:53:50.855901',_binary '?原/團@뭿\?\?\r\홌'),(_binary '\0',1,'2024-10-08 09:40:39.557068',_binary '??zeO뮎{+뻟\?臾'),(_binary '\0',1,'2024-10-10 14:38:23.545309',_binary '럙90쬎??뾃\?)'),(_binary '\0',1,'2024-10-08 09:41:52.479602',_binary '?흜?,J6?????),(_binary '\0',1,'2024-10-08 09:54:59.395979',_binary '??~K\?t??r手?''),(_binary '\0',1,'2024-10-08 09:58:02.007267',_binary '?G:뛆\?9\???'),(_binary '\0',1,'2024-10-08 09:43:38.387285',_binary '???\鈿L샬칢`\??),(_binary '\0',1,'2024-10-10 16:15:25.364739',_binary '샭촯\r\?烈zQ\??\??),(_binary '\0',1,'2024-10-08 09:45:00.529533',_binary '슸\쬘?D6???\?'),(_binary '\0',1,'2024-10-08 09:39:02.379913',_binary '?뇯\?鼈\??묻X'),(_binary '\0',1,'2024-10-09 14:24:53.412160',_binary '???좴H┏??닆'),(_binary '\0',1,'2024-10-08 09:42:09.742070',_binary '쒵,t\劑I썮?植쓳D?),(_binary '\0',1,'2024-10-08 09:38:40.550653',_binary '??7?N]엾S??'),(_binary '\0',1,'2024-10-08 10:51:51.364062',_binary '줢 ^?B섶K㎼?\??),(_binary '\0',1,'2024-10-09 15:22:49.637492',_binary '?\?~\?쑌A????),(_binary '\0',1,'2024-10-08 09:47:42.422869',_binary '???귽뎁렣微-肩'),(_binary '\0',2,'2024-10-10 09:37:30.927744',_binary '챴l?\?@삃쪎r??'),(_binary '\0',1,'2024-10-08 09:43:56.242501',_binary '쵰?\?臼\??5?),(_binary '\0',1,'2024-10-08 09:42:04.133647',_binary '??\r쾑@?\??\훍??),(_binary '\0',1,'2024-10-08 09:56:48.054447',_binary '킕O>.쯓?/복늉\?'),(_binary '\0',2,'2024-10-10 09:37:23.805959',_binary '뒬촎쐱B즴L\?H?'),(_binary '\0',1,'2024-10-08 09:42:32.508305',_binary '????N뎱	\室?)'),(_binary '\0',1,'2024-10-08 09:57:34.780741',_binary '혬/x쁟G\??字TM'),(_binary '\0',1,'2024-10-08 09:52:48.393016',_binary '\??\鷗얣aŋw 뀛'),(_binary '\0',1,'2024-10-08 09:47:39.092758',_binary '\?od츸봄	ち5'),(_binary '\0',1,'2024-10-08 09:55:58.828607',_binary '\홙I\?\?Y믆?近?7'),(_binary '\0',1,'2024-10-08 09:57:07.900981',_binary '\?\\\0\飼D퍡\??vL'),(_binary '\0',1,'2024-10-08 09:42:25.762055',_binary '\?\'쒮@썉슲Wt'),(_binary '\0',1,'2024-10-08 09:51:35.549842',_binary '\?7?茹A벞첏發?'),(_binary '\0',1,'2024-10-08 10:53:39.362177',_binary '\?\?J\rA[??Hh쭾?'),(_binary '\0',1,'2024-10-08 09:41:44.065862',_binary '\???sH[?甁\"뇯?),(_binary '\0',1,'2024-10-08 09:42:59.753183',_binary '\?퀺칅I\碩\?{\'?'),(_binary '\0',1,'2024-10-08 09:42:54.356121',_binary '\?\?;?|엌WZ?\?),(_binary '\0',1,'2024-10-08 09:55:01.527359',_binary '\??	\?F?\???*'),(_binary '\0',1,'2024-10-08 09:57:14.179061',_binary '\?i\?쯎㉮\??h\?''),(_binary '\0',1,'2024-10-08 09:43:04.231764',_binary '\?李\湲E??兌kg?'),(_binary '\0',2,'2024-10-10 09:37:22.930668',_binary '\帥Ce]K렓컮WgZ'),(_binary '\0',1,'2024-10-09 14:48:52.951103',_binary '\?zg@,껼\???R?),(_binary '\0',1,'2024-10-08 09:41:51.676329',_binary '\?"BF{O땍뻛w%!'),(_binary '\0',1,'2024-10-08 09:42:56.311778',_binary '\?칆?Mn??9\??),(_binary '\0',1,'2024-10-08 09:42:28.915731',_binary '\??햘줐D퍯R\?\"3?),(_binary '\0',1,'2024-10-08 09:52:59.598108',_binary '\?<O캌X_???'),(_binary '\0',1,'2024-10-08 09:53:49.065087',_binary '\?\"wH푆)굋?\?\?),(_binary '\0',1,'2024-10-09 15:27:47.902566',_binary '焄?彭A굝C\?E\?');
    /*!40000 ALTER TABLE `invitation_code` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `media`
    --
    
    DROP TABLE IF EXISTS `media`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `media` (
      `id` binary(16) NOT NULL,
      `dtype` varchar(31) NOT NULL,
      `url` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `media`
    --
    
    LOCK TABLES `media` WRITE;
    /*!40000 ALTER TABLE `media` DISABLE KEYS */;
    INSERT INTO `media` VALUES (_binary '쐂R; F.쎙ehu@\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/0e9c6452-3b20-462e-9bc0-1665687540cd'),(_binary '\???F??똄e챇','Image','https://agijagi-bucket.s3.amazonaws.com/media/10d52695-ea74-46af-bb85-2a8c4565aa55'),(_binary '戮?A\粹???S','Image','https://agijagi-bucket.s3.amazonaws.com/media/14ebbd88-0118-41e2-b6fa-25bd7fd18453'),(_binary '*A}?N쩦\?"\癖y\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/1e2a417d-f99a-4e06-a557-f622dbfe79e5'),(_binary 's랠?L\\퀷c?푉','Image','https://agijagi-bucket.s3.amazonaws.com/media/1e73b7a4-9407-4c5c-b448-0863ab40be50'),(_binary ' )??폀rS?멭\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/20290b06-bef3-45bc-8f72-53e2949158d5'),(_binary '!\?lL쯓&춎F\??燦','Image','https://agijagi-bucket.s3.amazonaws.com/media/21d81d6c-4ca9-4f26-ad6d-0e46f4e7f3be'),(_binary '&\生덩D\脆??t\?힓','Image','https://agijagi-bucket.s3.amazonaws.com/media/260bdfe6-b5a2-44f6-aa3f-c68474d4c646'),(_binary ')\\IP\?\????\\','Image','https://agijagi-bucket.s3.amazonaws.com/media/295c4950-1ff2-41f6-919f-3e1da9c65d5c'),(_binary ')\홖졗뷛?w~촱\rF','Image','https://agijagi-bucket.s3.amazonaws.com/media/29c351a0-c395-47c7-907f-777eac6e0d46'),(_binary '+\??\?F．\??쐊','Image','https://agijagi-bucket.s3.amazonaws.com/media/2bcce04b-f462-46a3-aeed-7180fe5b9c6b'),(_binary '.DH>?N???\?\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/2e44483e-933c-4e08-953f-9bda49d75bc6'),(_binary ':e?@R?溯iZx7','Image','https://agijagi-bucket.s3.amazonaws.com/media/3ac9b465-be0b-4052-b6e1-bd69ff5a7837'),(_binary ';\휦@]\?찞퓧?풺','Image','https://agijagi-bucket.s3.amazonaws.com/media/3bc54e40-5dd0-4ba9-9cbf-90b8281bbf62'),(_binary 'Cp\Z?폠&??\????,'Image','https://agijagi-bucket.s3.amazonaws.com/media/43701a86-01bd-4926-9ade-73d77892daf4'),(_binary 'DI%gMG벖,Q???,'Image','https://agijagi-bucket.s3.amazonaws.com/media/44194925-674d-4793-b42c-51b128ae5eb8'),(_binary 'E\??-YA\"븄???\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/45e2c337-2d59-4122-956f-a72888f17cc3'),(_binary 'G??\揚N＊청?8n\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/479ae2a0-e5c0-4ea3-aac3-bbfd1b386eef'),(_binary 'INu\흕껱˛na\?[','Image','https://agijagi-bucket.s3.amazonaws.com/media/494e75c5-7584-49a2-ad6e-610814cf505b'),(_binary '\\&1펁\??~B?[','Image','https://agijagi-bucket.s3.amazonaws.com/media/5c2631bc-55e7-4a03-850e-0f7e42be2f5b'),(_binary 'c혗#H쨲\烏~???,'Image','https://agijagi-bucket.s3.amazonaws.com/media/631ec282-2313-48a4-8ce8-a17e86d9258c'),(_binary 'd=풗\?㈌&鄕??','Image','https://agijagi-bucket.s3.amazonaws.com/media/643d12be-9adc-44a9-bd26-0efac190f592'),(_binary 'g??\Kァ\液\墓?b','Image','https://agijagi-bucket.s3.amazonaws.com/media/6707fb7b-9b5c-4bab-a1e4-fbd9d78d2f62'),(_binary 'g\???聘\??\?\럝','Image','https://agijagi-bucket.s3.amazonaws.com/media/67d85186-1094-40de-bdc3-d03bdf5c8e7a'),(_binary 'j퉔?It???둇\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/6ab968b1-0119-4974-841e-bde3638a43db'),(_binary 'kA쑀\?\侏O\?u;?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/6b08419c-a0f5-4df1-ad4f-14e317753b8b'),(_binary 'o+|W쟏/\?_,\?Q','Image','https://agijagi-bucket.s3.amazonaws.com/media/6f2b7c04-57a0-4f2f-80cd-375f2cc81051'),(_binary 'p\??짡\?\?\?덠','Image','https://agijagi-bucket.s3.amazonaws.com/media/700fc8de-39a4-43e7-97ed-53e26b88fb1e'),(_binary 'w쁬#%첛꼯쳪\훋\?\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/77986d23-25ab-4684-8aab-8fc45af442c9'),(_binary 'y,\?\툽E둀\???','Image','https://agijagi-bucket.s3.amazonaws.com/media/792cd046-c5fa-4510-89fa-f094c78efb35'),(_binary '~\0??쟆]뭴\??\??,'Image','https://agijagi-bucket.s3.amazonaws.com/media/7e0094f7-94a0-4a5d-9283-ccf27ed74d8e'),(_binary '?~쩎G?~a\?ROp','Image','https://agijagi-bucket.s3.amazonaws.com/media/837c7e7f-a546-47d0-987e-61c533524f70'),(_binary '깢\?9\?혤\?\??,'Image','https://agijagi-bucket.s3.amazonaws.com/media/8395f380-39f1-48c8-a1e0-19d073111293'),(_binary '?'g???e┾샇???,'Image','https://agijagi-bucket.s3.amazonaws.com/media/872767c0-e1f7-4f65-a6da-98b8a019feec'),(_binary '?'얷>딫?\?\?\??,'Image','https://agijagi-bucket.s3.amazonaws.com/media/88279e61-3e8b-48ff-8333-f148e39cccc2'),(_binary '??L춣I戟?0.\?\?','Image','https://agijagi-bucket.s3.amazonaws.com/media/88f4ff4c-ad84-49d0-bd87-002ed104ef08'),(_binary '?\?:;C\琇\???.\r','Image','https://agijagi-bucket.s3.amazonaws.com/media/8c5ec87b-3a3b-43e2-afec-cef4382e0d05'),(_binary '뭹C?\?뢲(\휯qUw','Image','https://agijagi-bucket.s3.amazonaws.com/media/928843fc-6ee4-4f1d-8f71-28c553715577'),(_binary '뮛1\?휯B[쪾\?^V','Image','https://agijagi-bucket.s3.amazonaws.com/media/92a731ef-c553-425b-a655-10ea631e5e56'),(_binary '?\?*DH릵Y끌~?_','Image','https://agijagi-bucket.s3.amazonaws.com/media/9e11e04d-2a44-4890-9359-b2f87e8b2c5f'),(_binary '?껌\?컮\??T)\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/a523b2ad-04dc-4fb0-94d1-db3d54297fe6'),(_binary '?`??L=둏Z쩳??,'Image','https://agijagi-bucket.s3.amazonaws.com/media/a92860f8-de4b-4c3d-8a4b-5aa569a10191'),(_binary '??꼘?리\?憔\??'','Image','https://agijagi-bucket.s3.amazonaws.com/media/b3c74f84-6ffa-47b8-aee7-f4fbc6f62718'),(_binary '럽m?DE쑇\?\?酩','Image','https://agijagi-bucket.s3.amazonaws.com/media/b7b46df8-4144-459c-a7ca-3a0cc755d9ae'),(_binary '?\\??I듋???-','Image','https://agijagi-bucket.s3.amazonaws.com/media/b85cc814-be16-498a-be9c-e545b129012d'),(_binary '?봳&jIm?쥶뾏2B','Image','https://agijagi-bucket.s3.amazonaws.com/media/bc169474-266a-496d-9f19-a345974d3242'),(_binary '?X匍켔??P?\勵L','Image','https://agijagi-bucket.s3.amazonaws.com/media/c13a58f8-d1b1-4eec-9ba5-33503fe5fa4c'),(_binary '?}*q\?????z\?','Image','https://agijagi-bucket.s3.amazonaws.com/media/ca9f7d2a-71d5-460f-9ec6-eedc5f7acc09'),(_binary '\????M+?|?:8?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/caed64b6-d50c-4d2b-a00b-7cfc453a38be'),(_binary '\??MhK컝\?Ft촩j','Image','https://agijagi-bucket.s3.amazonaws.com/media/cb48ac01-4d68-4bb0-88ea-514674ac676a'),(_binary '慷KM\?Jm?\?藪p?','Image','https://agijagi-bucket.s3.amazonaws.com/media/cbaf4b4d-f25b-4a6d-8311-f5e2bf70fb0b'),(_binary '?뼾%턅?\쩡???','Image','https://agijagi-bucket.s3.amazonaws.com/media/e58c96c1-25b6-41c6-95c2-c4b4d245f981'),(_binary '\?\肄\"\?\?\??Z?','Image','https://agijagi-bucket.s3.amazonaws.com/media/ea2cecbd-22d9-48e7-951e-d669b91a8124'),(_binary '\??cF?펰\?넞0','Image','https://agijagi-bucket.s3.amazonaws.com/media/f49b19a8-1b63-46e1-81bc-82f45486a230'),(_binary '\酢?얤쌳1??n#','Image','https://agijagi-bucket.s3.amazonaws.com/media/f5b20bd8-8e9e-4b05-9b61-31b9ffb10a23'),(_binary '\???\?굘Y-a\?\?','Image','https://agijagi-bucket.s3.amazonaws.com/media/f5ceef3f-10e8-4fb1-b559-2d61c961d360'),(_binary '?y꼳@s?\??LA','Image','https://agijagi-bucket.s3.amazonaws.com/media/fb11791c-848c-4073-912d-f5cb574c1141'),(_binary '?A(킏MY?얭?\?,'Image','https://agijagi-bucket.s3.amazonaws.com/media/fc144128-b49b-4d59-a840-039e53c107d3'),(_binary '?錢1B;BB뿤????,'Image','https://agijagi-bucket.s3.amazonaws.com/media/fcefb131-423b-4242-97a4-bf36a6ed71f8');
    /*!40000 ALTER TABLE `media` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `member`
    --
    
    DROP TABLE IF EXISTS `member`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `member` (
      `is_deleted` bit(1) NOT NULL,
      `created_at` datetime(6) NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `profile_image_id` binary(16) DEFAULT NULL,
      `email` varchar(255) NOT NULL,
      `nickname` varchar(255) NOT NULL,
      `password` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UK6cheof1rxqhjd4h5wfi308jo2` (`profile_image_id`),
      CONSTRAINT `FKk6dolck5tod1q6j07u7qpjmrl` FOREIGN KEY (`profile_image_id`) REFERENCES `image` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `member`
    --
    
    LOCK TABLES `member` WRITE;
    /*!40000 ALTER TABLE `member` DISABLE KEYS */;
    INSERT INTO `member` VALUES (_binary '\0','2024-10-07 16:49:40.000000',1,_binary '\酢?얤쌳1??n#','abcd123@naver.com','?꾨튌 ?⑹쭊','$2a$10$YFnsdT03BJ8Sadp1l81JFOAppuyMli7qphRnD1NgoElhgIcl62KIq'),(_binary '\0','2024-10-07 16:49:40.000000',2,NULL,'abcd1234@naver.com','?ㅻⅤ?ㅻ떖??,'$2a$10$TaxwwF9QrH5NEqjo8LLQCealD5OsN8d3VCrYpc5nGDNfYg7P7/1zC'),(_binary '\0','2024-10-08 02:57:13.835051',3,_binary '.DH>?N???\?\?,'ssafy@naver.com','?좎듅 ?꾩???,'$2a$10$OJBfl.rjV0j/.4ONhkkl8.XrKw7j5H8cU9SYA/EUDwLI9mWu4gayG'),(_binary '\0','2024-10-08 09:44:42.940986',4,NULL,'test123@test.com','?뚯뒪??,'$2a$10$sp2BnmX.uyERJ8IcFojClevEqboz8t79uLuJZ0hp7bT6srLc0ZyHW'),(_binary '\0','2024-10-08 09:49:46.345369',5,NULL,'dydwls9703@naver.com','?뚯뒪??,'$2a$10$oesac3QshHi5SXTWFtiuMOlpGA2EVrLbxZeoFcA6kJ/wfOs0oJgjm'),(_binary '\0','2024-10-08 14:38:13.114791',6,NULL,'altys31@gmail.com','?꾧린?먭린','$2a$10$dO6QRznB07u4yGEhpuqY4uHBt9T3QdxzPkeVU/LBrlGotJVit0XyK'),(_binary '','2024-10-08 15:08:20.475379',7,NULL,'ssafy1@naver.com','?댁떥??,'$2a$10$vsC5asIVq8d..NrhYUWh9.8IwduBYIEZkeKrd0zXS8oTtCgopFFYu'),(_binary '','2024-10-08 15:12:13.377706',8,NULL,'ssafy2@naver.com','理쒖떥??,'$2a$10$1DG7Uv8MO.BnqtsLoMS0aOsnmkqR0gKlqM3cgRsP8MnLQGy/sW5yO'),(_binary '\0','2024-10-08 15:13:07.561279',9,NULL,'ssafy3@naver.com','源?명뵾123','$2a$10$BoI9WkXjQqBScs4.6umH2OsihvnENkLL19jLWyeRZEZ/qFyO3kAoK'),(_binary '','2024-10-08 17:44:35.547272',10,NULL,'ssafy1@naver.com','?댁떥??,'$2a$10$1mYK0PfFcNMhePVMYasgDeJBFXoiZGR4toIsl2oOrKyr8RxchC3OW'),(_binary '\0','2024-10-09 01:15:39.617619',11,NULL,'asdklsadkl@naver.cm','rgegreag','$2a$10$YI5/Asy.eIyJU5qrH.yngu5lY7iaJlNwSC.ZtF3XLr7XlTFpgivie'),(_binary '\0','2024-10-09 01:20:18.207697',12,NULL,'dkdkdka@naver.cok','?섎삓?쒕냸','$2a$10$sd1sFYfAH560y7o4.c9eZe3PABQtPmEV1yHGNIe7EByqdY79os3de'),(_binary '\0','2024-10-09 01:25:54.155350',13,NULL,'ssafy2@naver.com','源?명뵾','$2a$10$a5HnzdWvebVfsV5Ciu664O0kHKTXr63QH2r2lSVnIqX.lnNbA7Nqe'),(_binary '\0','2024-10-09 01:27:02.757527',14,NULL,'ssafy4@naver.com','理쒖떥??,'$2a$10$ZjNq2DTcrliwRK18/0TCkuZ6HHeyTfArs/8XD8ZC16igxXnZnZADS'),(_binary '\0','2024-10-09 02:44:58.708477',15,NULL,'qwddsa@naver.com','sadsadsa','$2a$10$4HvsVNw7K8PLK8VwkfcHSOIFMP2Ij8c7tbWy7hcVonlUkHlm8SpQK'),(_binary '\0','2024-10-09 07:11:28.255163',16,NULL,'ssafy5@naver.com','源?명뵾','$2a$10$Rq6GqxOm9vKEceajzNmob.4Dl19O4EYy6rD84lXoogy9mSJ1Jy1F2'),(_binary '\0','2024-10-09 07:22:21.797798',17,_binary '?껌\?컮\??T)\?,'ssafy6@naver.com','?щ? ?쇱큿','$2a$10$oRprj1iE7dUoH0JRPL0ZqOEusorlO62UkmE0.abI2PZ8RqYxrsTKK'),(_binary '\0','2024-10-09 14:23:00.638780',18,_binary 'j퉔?It???둇\?,'ssafy@ssafy.com','SSAFY','$2a$10$j/h64ZVCtRZyOiqDJJIj4OQfIymvy6NaRqTxpP6LKj7j6Vg9ZhhQ2'),(_binary '\0','2024-10-09 17:38:43.151176',19,NULL,'lnh0102@naver.com','?섎?','$2a$10$8Ra9lLd3BhhyDecK48JLu.HI8HmsOwMGr1jcSRbFaUMVmQA8Nyrh2'),(_binary '\0','2024-10-10 04:28:21.323952',20,NULL,'ssafy7@naver.com','源?명뵾','$2a$10$a/B5zYStZdrICWLFbymPT.PFbjKsz4MkRO6jCyvXpfbwpsUX1X5Ku');
    /*!40000 ALTER TABLE `member` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `member_child`
    --
    
    DROP TABLE IF EXISTS `member_child`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `member_child` (
      `child_id` bigint NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `member_id` bigint NOT NULL,
      `authority` enum('READ','WRITE') DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FKrfqgro6ewbex04tke15c138d9` (`child_id`),
      KEY `FKqr5oru7dycbfqcd2upnjosaca` (`member_id`),
      CONSTRAINT `FKqr5oru7dycbfqcd2upnjosaca` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
      CONSTRAINT `FKrfqgro6ewbex04tke15c138d9` FOREIGN KEY (`child_id`) REFERENCES `child` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `member_child`
    --
    
    LOCK TABLES `member_child` WRITE;
    /*!40000 ALTER TABLE `member_child` DISABLE KEYS */;
    INSERT INTO `member_child` VALUES (1,1,1,'WRITE'),(2,2,1,'WRITE'),(1,9,3,'READ'),(1,11,18,'WRITE'),(4,12,19,'WRITE'),(1,14,17,'READ');
    /*!40000 ALTER TABLE `member_child` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `milestone`
    --
    
    DROP TABLE IF EXISTS `milestone`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `milestone` (
      `month` int NOT NULL,
      `required_amount` int NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `content` varchar(255) NOT NULL,
      `title` varchar(255) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `milestone`
    --
    
    LOCK TABLES `milestone` WRITE;
    /*!40000 ALTER TABLE `milestone` DISABLE KEYS */;
    INSERT INTO `milestone` VALUES (0,8,1,'?롫뱶由??먯꽭?먯꽌 怨좉컻瑜??좉퉸 ?????덈떎','?吏곸엫 / ?좎껜諛쒕떖'),(0,8,2,'?먯뿉 ?용뒗 臾쇨굔??伊먮젮怨??쒕룄?쒕떎','?吏곸엫 / ?좎껜諛쒕떖'),(0,8,3,'?먭낵 ?붿쓣 紐?履쎌쑝濡?媛?몄삩??,'?吏곸엫 / ?좎껜諛쒕떖'),(0,8,4,'諛곌퀬?꾧굅??遺덊렪?????몄쓬?쇰줈 ?섏궗?뚰넻???쒕떎','?몄뼱 / ?섏궗?뚰넻'),(0,8,5,'?щ엺??紐⑹냼由щ? ?ｊ퀬 議곗슜?댁?嫄곕굹 諛섏쓳?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(0,8,6,'?뱀븣??鍮꾩듂???뚮━瑜??몃떎','?몄뼱 / ?섏궗?뚰넻'),(0,8,7,'?吏곸씠??臾쇱껜???쇨뎬???곕씪 ?쒖꽑???吏곸씤??,'?몄? (?숈뒿, ?ш퀬, 臾몄젣?닿껐)'),(0,8,8,'鍮쏄낵 ?뚮━??誘쇨컧?섍쾶 諛섏쓳?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣?닿껐)'),(0,8,9,'?쇨뎬??吏묒쨷?댁꽌 爾먮떎蹂몃떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣?닿껐)'),(0,8,10,'?꾨쭏??紐⑹냼由щ굹 ?쇨뎬???몄떇?섍퀬 諛섏쓳?섎젮怨??쒕떎','?ы쉶??/ ?뺤꽌'),(0,8,11,'?덉젙???먮겮硫??좎떆 ?숈븞 ?껉굅??誘몄냼瑜?吏?????덈떎','?ы쉶??/ ?뺤꽌'),(0,8,12,'遺덉븞?섍굅??遺덊렪?????몄쓬?쇰줈 ?쒗쁽?쒕떎','?ы쉶??/ ?뺤꽌'),(1,8,13,'?롫뱶由??먯꽭?먯꽌 ?좉퉸 ?숈븞 癒몃━瑜??좊떎','?吏곸엫 / ?좎껜 諛쒕떖'),(1,8,14,'?붽낵 ?ㅻ━瑜??붾뱾硫?紐몄쓣 ?吏곸씤??,'?吏곸엫 / ?좎껜 諛쒕떖'),(1,8,15,'?먯쓣 ?쇨뎬 洹쇱쿂濡?媛?멸컙??,'?吏곸엫 / ?좎껜 諛쒕떖'),(1,8,16,'?뚮━??諛섏쓳?섏뿬 怨좉컻瑜??뚮┛??,'?몄뼱 / ?섏궗?뚰넻'),(1,8,17,'吏㏐쾶 \'??' 媛숈? ?뚮━瑜??몃떎','?몄뼱 / ?섏궗?뚰넻'),(1,8,18,'遺紐⑥쓽 紐⑹냼由ъ뿉 諛섏쓳?섏뿬 ??留욎땄???쒕떎','?몄뼱 / ?섏궗?뚰넻'),(1,8,19,'媛뺥븳 鍮쏆뿉 諛섏쓳?섏뿬 ?덉쓣 媛먮뒗??,'?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(1,8,20,'臾쇱껜瑜??쒖꽑?쇰줈 已볦쑝???쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(1,8,21,'?ㅼ뼇???뚮━???좎떆 硫덉무?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(1,8,22,'遺紐⑤굹 ?듭닕???щ엺???쇨뎬??蹂닿퀬 誘몄냼瑜?吏볥뒗??,'?ы쉶??/ ?뺤꽌'),(1,8,23,'遺紐⑥쓽 ?쇨뎬??吏묒쨷?댁꽌 蹂몃떎','?ы쉶??/ ?뺤꽌'),(1,8,24,'?덉븘二쇰㈃ 吏꾩젙?쒕떎','?ы쉶??/ ?뺤꽌'),(2,8,25,'?롫뱶由??먯꽭?먯꽌 癒몃━瑜?45???뺣룄 ?좊떎','?吏곸엫 / ?좎껜 諛쒕떖'),(2,8,26,'?먯쓣 ?낆쑝濡?媛?멸컙??,'?吏곸엫 / ?좎껜 諛쒕떖'),(2,8,27,'?꾩슫 ?먯꽭?먯꽌 ?ㅻ━瑜?李⑤ŉ ?吏곸씤??,'?吏곸엫 / ?좎껜 諛쒕떖'),(2,8,28,'?ㅼ뼇???뚮━瑜??ｊ퀬 諛섏쓳?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(2,8,29,'媛꾨떒???뚮━瑜??됰궡 ?닿린 ?쒖옉?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(2,8,30,'誘몄냼 吏?쇰ŉ ?뚮━濡?諛섏쓳?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(2,8,31,'?吏곸씠??臾쇱껜瑜??덉쑝濡?已볥뒗??,'?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(2,8,32,'臾쇱껜瑜?蹂대ŉ ?먯쓣 六쀬쑝???쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(2,8,33,'?먯떊???먯쓣 諛붾씪蹂대ŉ 愿李고븳??,'?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(2,8,34,'?듭닕???щ엺??蹂닿퀬 誘몄냼瑜?吏볥뒗??,'?ы쉶??/ ?뺤꽌'),(2,8,35,'?덇린硫??몄븞?댁쭊??,'?ы쉶??/ ?뺤꽌'),(2,8,36,'遺紐⑥?????留욎땄??湲몄뼱吏꾨떎','?ы쉶??/ ?뺤꽌'),(3,8,37,'?롫뱶由??먯꽭?먯꽌 ?붾줈 紐몄쓣 吏?깊븯硫?癒몃━瑜??믪씠 ?좊떎','?吏곸엫 / ?좎껜 諛쒕떖'),(3,8,38,'?먯쓣 六쀬뼱 臾쇱껜瑜??≪쑝???쒕떎','?吏곸엫 / ?좎껜 諛쒕떖'),(3,8,39,'二쇰㉨???닿퀬 ?먯쓣 ?먯＜ 蹂몃떎','?吏곸엫 / ?좎껜 諛쒕떖'),(3,8,40,'?뚮━? 誘몄냼濡?遺紐⑥? ?섏궗?뚰넻?섎젮 ?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(3,8,41,'?뱀븣?대? ?쒖옉?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(3,8,42,'紐⑹냼由??ㅼ뿉 ?곕씪 諛섏쓳?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(3,8,43,'?щ엺 ?쇨뎬???λ?瑜??먮겮硫?吏묒쨷?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(3,8,44,'臾쇱껜媛 ?щ씪吏硫?洹??먮━瑜?怨꾩냽 ?묒떆?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(3,8,45,'嫄곗슱 ???먯떊??紐⑥뒿??諛섏쓳?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(3,8,46,'遺紐④? ?덉븘二쇰㈃ 吏꾩젙?쒕떎','?ы쉶??/ ?뺤꽌'),(3,8,47,'?몃? ?먭레??誘몄냼瑜?吏?쇰ŉ 諛섏쓳?쒕떎','?ы쉶??/ ?뺤꽌'),(3,8,48,'湲곕텇???곕씪 ?쇨뎬 ?쒖젙???ㅼ뼇?댁쭊??,'?ы쉶??/ ?뺤꽌'),(4,8,49,'?롫뱶由??곹깭?먯꽌 ?붽퓞移섎줈 紐몄쓣 吏?깊븯硫?癒몃━瑜??덉젙?곸쑝濡??좊떎','?吏곸엫 / ?좎껜 諛쒕떖'),(4,8,50,'臾쇨굔???먯쑝濡??↔퀬 ?낆쑝濡?媛?멸컙??,'?吏곸엫 / ?좎껜 諛쒕떖'),(4,8,51,'?깆쓣 諛붾떏???怨??꾩슫 ?곹깭?먯꽌 紐몄쓣 ?놁쑝濡?援щⅨ??,'?吏곸엫 / ?좎껜 諛쒕떖'),(4,8,52,'?껋쑝硫??뚮━瑜??닿퀬 遺紐⑥? ?곹샇?묒슜?섎젮 ?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(4,8,53,'?뱀븣?대? ???먯＜ ?섍퀬, ?ㅼ뼇???뚮━瑜??몃떎','?몄뼱 / ?섏궗?뚰넻'),(4,8,54,'?먯떊???대쫫??諛섏쓳?섍린 ?쒖옉?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(4,8,55,'?덉븵???덈뒗 臾쇨굔??爾먮떎蹂닿퀬 ?≪쑝???먯쓣 六쀫뒗??,'?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(4,8,56,'媛꾨떒???멸낵愿怨꾨? ?댄빐?섍린 ?쒖옉?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(4,8,57,'?덈줈??臾쇱껜瑜??먯깋?섎젮怨??먯쑝濡?留뚯쭊??,'?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(4,8,58,'遺紐⑥????쒓컖?? ?좎껜???묒큺??醫뗭븘?쒕떎','?ы쉶??/ ?뺤꽌'),(4,8,59,'嫄곗슱??鍮꾩튇 ?먯떊??紐⑥뒿??蹂닿퀬 諛섏쓳?쒕떎','?ы쉶??/ ?뺤꽌'),(4,8,60,'?듭닕???щ엺??蹂대㈃ ?껋쓬??蹂댁씤??,'?ы쉶??/ ?뺤꽌'),(5,8,61,'諛곕? 諛붾떏???怨??꾩슫 ?곹깭?먯꽌 紐몄쓣 醫뚯슦濡?援щⅨ??,'?吏곸엫 / ?좎껜 諛쒕떖'),(5,8,62,'?먭낵 諛쒖쓣 ?먯쑀濡?쾶 ?吏곸씠硫??먯깋?쒕떎','?吏곸엫 / ?좎껜 諛쒕떖'),(5,8,63,'臾쇨굔???↔퀬 ?붾뱾嫄곕굹 ?섏????됰룞???쒕떎','?吏곸엫 / ?좎껜 諛쒕떖'),(5,8,64,'?ㅼ뼇???뱀븣?대? ?ъ슜???섏궗?뚰넻?섎젮 ?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(5,8,65,'?껉굅???뚮━瑜??대ŉ 媛먯젙???쒗쁽?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(5,8,66,'遺紐⑥쓽 紐⑹냼由ъ뿉 ??媛뺥븯寃?諛섏쓳?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(5,8,67,'臾쇨굔???볦튂硫?洹??먮━瑜?怨꾩냽 爾먮떎蹂몃떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(5,8,68,'臾쇨굔???ш린??紐⑥뼇??????몄떇???쒖옉?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(5,8,69,'愿???덈뒗 臾쇱껜瑜??낆쑝濡?媛?멸?硫??먯깋?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(5,8,70,'??꽑 ?щ엺?먭쾶 ??쓣 媛由ш린 ?쒖옉?쒕떎','?ы쉶??/ ?뺤꽌'),(5,8,71,'遺紐⑥???援먭컧??利먭린怨????먯＜ 李얜뒗??,'?ы쉶??/ ?뺤꽌'),(5,8,72,'遺紐⑥쓽 ?쇨뎬??蹂닿퀬 ?쒖젙???곕씪 諛섏쓳?쒕떎','?ы쉶??/ ?뺤꽌'),(6,8,73,'諛곕? ?怨??롫뱶由??곹깭?먯꽌 ?ㅼ뒪濡??ㅼ쭛?붾떎','?吏곸엫 / ?좎껜 諛쒕떖'),(6,8,74,'?먭낵 ?먭??쎌쓣 ?ъ슜?섏뿬 ?묒? 臾쇨굔???〓뒗??,'?吏곸엫 / ?좎껜 諛쒕떖'),(6,8,75,'?됱? ?곹깭?먯꽌 ?좉퉸 ?숈븞 ?ㅼ뒪濡??먯꽭瑜??좎??쒕떎','?吏곸엫 / ?좎껜 諛쒕떖'),(6,8,76,'?뱀븣?닿? ??蹂듭옟?댁?怨??ㅼ뼇???뚮━瑜??몃떎','?몄뼱 / ?섏궗?뚰넻'),(6,8,77,'?먯떊???대쫫??遺瑜대㈃ 諛섏쓳?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(6,8,78,'?ㅻⅨ ?щ엺??留먯쓣 ?ㅼ쑝硫??껉굅???뚮━濡?諛섏쓳?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(6,8,79,'?④꺼吏?臾쇱껜瑜?李얠쑝?ㅻ뒗 ?쒕룄瑜??쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(6,8,80,'臾쇱껜瑜??⑥뼱?⑤━硫?洹??뚮━瑜??ｊ퀬 爾먮떎蹂몃떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(6,8,81,'?λ궃媛먯쓣 ?먮뱶由ш굅???붾뱾硫댁꽌 ?뚮━媛 ?섎뒗 寃껋쓣 利먭릿??,'?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(6,8,82,'?듭닕???щ엺?먭쾶 ??媛뺥븯寃??좎갑??蹂댁씤??,'?ы쉶??/ ?뺤꽌'),(6,8,83,'湲곕텇???곕씪 ?ㅼ뼇???쒖젙??吏볥뒗??,'?ы쉶??/ ?뺤꽌'),(6,8,84,'嫄곗슱 ???먯떊??紐⑥뒿??蹂닿퀬 ?껊뒗??,'?ы쉶??/ ?뺤꽌'),(7,8,85,'?됱? ?먯꽭?먯꽌 ?먯쓽 ?꾩? ?놁씠 吏㏃? ?쒓컙 ?숈븞 洹좏삎???〓뒗??,'?吏곸엫 / ?좎껜 諛쒕떖'),(7,8,86,'?롫뱶由??곹깭?먯꽌 諛곕? 諛硫??욎쑝濡?湲곗뼱媛???쒕떎','?吏곸엫 / ?좎껜 諛쒕떖'),(7,8,87,'?먯쓣 ?댁슜??臾쇨굔???↔퀬 ?붾뱾嫄곕굹 ?섏쭊??,'?吏곸엫 / ?좎껜 諛쒕떖'),(7,8,88,'?뱀븣?닿? ?붿슧 蹂듭옟?댁?怨??뚯젅??諛섎났?쒕떎 (?? \"諛붾컮諛?")','?몄뼱 / ?섏궗?뚰넻'),(7,8,89,'遺紐④? 留먰븷 ??洹瑜?湲곗슱?대ŉ 吏묒쨷?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(7,8,90,'?먯떊???대쫫??遺瑜대㈃ ?뺤떎?섍쾶 諛섏쓳?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(7,8,91,'遺遺꾩쟻?쇰줈 媛?ㅼ쭊 臾쇱껜瑜?李얠쑝?ㅺ퀬 ?쒕룄?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(7,8,92,'?먭낵 ?낆쓣 ?ъ슜?섏뿬 臾쇱껜瑜??먯깋?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(7,8,93,'?덉븵?먯꽌 ?⑥뼱吏?臾쇱껜瑜?李얘린 ?꾪빐 二쇰????댄???,'?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(7,8,94,'遺紐⑤굹 ?듭닕???щ엺??蹂대ŉ 媛뺥븯寃?諛섏쓳?쒕떎','?ы쉶??/ ?뺤꽌'),(7,8,95,'嫄곗슱 ?띿쓽 ?먯떊??紐⑥뒿??蹂닿퀬 利먭굅?뚰븳??,'?ы쉶??/ ?뺤꽌'),(7,8,96,'媛먯젙????紐낇솗?섍쾶 ?쒗쁽?섎ŉ ?껉굅???????댁쑀媛 遺꾨챸?섎떎','?ы쉶??/ ?뺤꽌'),(8,8,97,'?먭낵 臾대쫷???ъ슜??蹂멸꺽?곸쑝濡?湲곗뼱 ?ㅻ땶??,'?吏곸엫 / ?좎껜 諛쒕떖'),(8,8,98,'?됱? ?먯꽭?먯꽌 ?섏뼱吏吏 ?딄퀬 ?ㅻ옒 ?좎??????덈떎','?吏곸엫 / ?좎껜 諛쒕떖'),(8,8,99,'?먭??쎌쓣 ?ъ슜???묒? 臾쇨굔??吏묒쑝???쒕떎','?吏곸엫 / ?좎껜 諛쒕떖'),(8,8,100,'\"留덈쭏\", \"諛붾컮\" 媛숈? ???뚯젅???뚮━瑜??몃떎','?몄뼱 / ?섏궗?뚰넻'),(8,8,101,'媛꾨떒??吏?쒖뿉 諛섏쓳?섍린 ?쒖옉?쒕떎 (?? \"?遊?", \"?덈뤌\")','?몄뼱 / ?섏궗?뚰넻'),(8,8,102,'?ㅼ뼇??媛먯젙???곕씪 ?뚮━ ?대뒗 諛⑹떇???щ씪吏꾨떎','?몄뼱 / ?섏궗?뚰넻'),(8,8,103,'臾쇨굔?????먯뿉???ㅻⅨ ?먯쑝濡???만 ???덈떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(8,8,104,'臾쇨굔???⑥뼱?⑤━硫??⑥뼱吏??꾩튂瑜?爾먮떎蹂몃떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(8,8,105,'?④꺼吏?臾쇱껜瑜?李얠븘?대젮怨??쒕떎 (?곸냽??媛쒕뀗??諛쒕떖)','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(8,8,106,'??꽑 ?щ엺?????寃쎄퀎媛 而ㅼ쭊??(???由쇱씠 ?쒖옉??','?ы쉶??/ ?뺤꽌'),(8,8,107,'遺紐④? 諛⑹쓣 ?좊굹硫?遺덉븞?댄븯湲??쒖옉?쒕떎','?ы쉶??/ ?뺤꽌'),(8,8,108,'遺紐⑤굹 蹂댄샇?먯? ?④퍡 ?덉쓣 ???붿슧 ?덉젙???먮???,'?ы쉶??/ ?뺤꽌'),(9,8,109,'湲곗뼱 ?ㅻ땲???띾룄媛 鍮⑤씪吏怨????먯쑀濡?쾶 ?吏곸씤??,'?吏곸엫 / ?좎껜 諛쒕떖'),(9,8,110,'?↔퀬 ?쇱뼱?쒕젮???쒕룄瑜??쒕떎','?吏곸엫 / ?좎껜 諛쒕떖'),(9,8,111,'臾쇱껜瑜??먭??쎄낵 ?꾩?濡?吏묐뒗 ?뚭렐???대룞??諛쒕떖?쒕떎 (吏묎쾶 ?↔린)','?吏곸엫 / ?좎껜 諛쒕떖'),(9,8,112,'媛꾨떒???쒖뒪泥섎? ?곕씪 ?섍굅???ㅼ뒪濡??ъ슜?쒕떎 (?? ?먯쓣 ?붾뱾硫?\"?덈뀞\")','?몄뼱 / ?섏궗?뚰넻'),(9,8,113,'紐?媛吏 ?⑥뼱瑜??댄빐?섍퀬 諛섏쓳?쒕떎 (?? \"?꾨쭏\", \"?꾨튌\")','?몄뼱 / ?섏궗?뚰넻'),(9,8,114,'?뱀븣?대? ?듯빐 ?섏궗?뚰넻?섎젮 ?섎ŉ ?뚮━???듭뼇???ㅼ뼇?댁쭊??,'?몄뼱 / ?섏궗?뚰넻'),(9,8,115,'?λ궃媛먯쓣 ?붾뱾嫄곕굹 ?먮뱶由щŉ ?뚮━ ?섎뒗 寃껋쓣 利먭릿??,'?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(9,8,116,'臾쇱껜瑜?鍮꾧탳?섍퀬 援щ퀎?섍린 ?쒖옉?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(9,8,117,'臾쇱껜媛 ?④꺼?몃룄 李얠쑝?ㅺ퀬 ?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(9,8,118,'遺紐④? 蹂댁씠吏 ?딆쑝硫?遺덉븞?댄븯吏留? 吏㏃? ?쒓컙 ?숈븞? ?쇱옄 ?덉쓣 ???덈떎','?ы쉶??/ ?뺤꽌'),(9,8,119,'移쒖닕???щ엺?먭쾶 ???좎갑??蹂댁씠硫???꽑 ?щ엺?먭쾶??寃쎄퀎?쒕떎','?ы쉶??/ ?뺤꽌'),(9,8,120,'二쇰? ?щ엺?ㅼ쓽 媛먯젙??怨듦컧?섏뿬 ?껉굅???멸린???쒕떎','?ы쉶??/ ?뺤꽌'),(10,8,121,'媛援щ? 遺숈옟怨??쇱뼱?쒕ŉ 嫄룸뒗 ?쒕룄瑜??쒕떎 (媛援??↔퀬 嫄룰린)','?吏곸엫 / ?좎껜 諛쒕떖'),(10,8,122,'?먯쑝濡??묒? 臾쇨굔???뺢탳?섍쾶 吏묒쓣 ???덈떎 (吏묎쾶 ?↔린 ?꾩꽦)','?吏곸엫 / ?좎껜 諛쒕떖'),(10,8,123,'?롫뱶由??먯꽭?먯꽌 ?쇱옄 ?됱쓣 ???덈떎','?吏곸엫 / ?좎껜 諛쒕떖'),(10,8,124,'\"?꾨쭏\", \"?꾨튌\"? 媛숈? ?⑥뼱瑜??섎? ?덇쾶 ?ъ슜?섍린 ?쒖옉?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(10,8,125,'媛꾨떒??吏?쒖뿉 諛섏쓳?쒕떎 (?? \"?ш린 ?\", \"?꾨땲??")','?몄뼱 / ?섏궗?뚰넻'),(10,8,126,'?먯쭞?대굹 ?쒖뒪泥섎? ?ъ슜???먯떊???섏궗瑜??쒗쁽?쒕떎 (?? ???붾뱾湲?','?몄뼱 / ?섏궗?뚰넻'),(10,8,127,'臾쇨굔???④꼈????李얠쑝?ㅻ뒗 ?곴레?곸씤 ?쒕룄瑜??쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(10,8,128,'臾쇱껜??湲곕뒫??留욌뒗 ?됰룞???쒕떎 (?? ?꾪솕湲곕? 洹???????','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(10,8,129,'?먯씤怨?寃곌낵瑜??댄빐?섍린 ?쒖옉?쒕떎 (?? 踰꾪듉???꾨Ⅴ硫??뚮━媛 ??','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(10,8,130,'?듭닕???щ엺?먭쾶 ?좎갑??蹂댁씠硫???꽑 ?щ엺?먭쾶??寃쎄퀎?쒕떎','?ы쉶??/ ?뺤꽌'),(10,8,131,'遺紐④? 諛⑹쓣 ?좊굹硫??몃ŉ 遺덉븞?댄븷 ???덈떎 (遺꾨━ 遺덉븞)','?ы쉶??/ ?뺤꽌'),(10,8,132,'?ㅻⅨ ?щ엺??媛먯젙???곕씪 ?먯떊???됰룞??議곗젅?섎젮 ?쒕떎','?ы쉶??/ ?뺤꽌'),(11,8,133,'???먯쑝濡?媛援щ? ?↔퀬 ???먯쑀濡?쾶 嫄룸뒗??,'?吏곸엫 / ?좎껜 諛쒕떖'),(11,8,134,'?쇱옄 ?쇱뼱?쒕젮???쒕룄瑜??쒕떎','?吏곸엫 / ?좎껜 諛쒕떖'),(11,8,135,'?먭낵 ?붿쓣 ?ъ슜??臾쇨굔??諛嫄곕굹 ?밴만 ???덈떎','?吏곸엫 / ?좎껜 諛쒕떖'),(11,8,136,'??留롮? ?⑥뼱瑜??댄빐?섍린 ?쒖옉?쒕떎 (?? \"怨?", \"臾?" ??','?몄뼱 / ?섏궗?뚰넻'),(11,8,137,'?먯떊???붽뎄瑜??쒖뒪泥섎굹 ?뚮━濡??쒗쁽?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(11,8,138,'?뱀븣?닿? ??蹂듭옟?댁?怨???뷀븯???뱀븣?대? ?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(11,8,139,'臾쇨굔???④릿 ?μ냼瑜?湲곗뼲?섍퀬 李얠쓣 ???덈떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(11,8,140,'臾쇨굔???볤굅???뺣━?섎뒗 媛꾨떒????대? ?쒖옉?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(11,8,141,'?곹솴??留욊쾶 ?됰룞??議곗젅?섎젮???쒕룄瑜??쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(11,8,142,'遺紐⑥? ?⑥뼱吏???遺덉븞?댄븯硫댁꽌??怨??덉젙??李얜뒗??,'?ы쉶??/ ?뺤꽌'),(11,8,143,'?덈줈???곹솴?대굹 ?섍꼍?먯꽌 遺紐⑤? 李얠쑝硫??섏??쒕떎','?ы쉶??/ ?뺤꽌'),(11,8,144,'二쇰? ?щ엺?ㅼ쓽 媛먯젙???댄뵾怨??곕씪 ?섎젮??寃쏀뼢???덈떎','?ы쉶??/ ?뺤꽌'),(12,8,145,'?쇱옄??紐?嫄몄쓬 嫄몄쓣 ???덈떎 (泥섏쓬 嫄룰린 ?쒖옉)','?吏곸엫 / ?좎껜 諛쒕떖'),(12,8,146,'?됱븯???쇱뼱?쒕뒗 ?숈옉???ㅼ뒪濡??????덈떎','?吏곸엫 / ?좎껜 諛쒕떖'),(12,8,147,'?먭??쎌쑝濡??묒? 臾쇨굔??吏묒뼱 ?щ━???λ젰??諛쒕떖?쒕떎','?吏곸엫 / ?좎껜 諛쒕떖'),(12,8,148,'媛꾨떒???⑥뼱瑜??ъ슜???섏궗?뚰넻???쒖옉?쒕떎 (?? \"?꾨쭏\", \"?꾨튌\", \"?덈뤌\")','?몄뼱 / ?섏궗?뚰넻'),(12,8,149,'?⑥뼱? ?쒖뒪泥섎? ?쇳빀???먯떊???섏궗瑜??쒗쁽?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(12,8,150,'遺紐④? ?섎뒗 留먯쓣 ?됰궡 ?닿린 ?쒖옉?쒕떎','?몄뼱 / ?섏궗?뚰넻'),(12,8,151,'臾쇨굔???붾뱾嫄곕굹 ?섏??????ㅼ뼇??諛⑹떇?쇰줈 臾쇱껜瑜??먯깋?쒕떎','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(12,8,152,'臾쇱껜瑜??뺣━?섍굅???뱀젙??諛⑸쾿?쇰줈 諛곗뿴?섎뒗 ??대? 利먭릿??,'?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(12,8,153,'媛꾨떒??臾몄젣瑜??닿껐?섎젮???쒕룄瑜??쒕떎 (?? ?λ궃媛??곸옄?먯꽌 臾쇨굔 爰쇰궡湲?','?몄? (?숈뒿, ?ш퀬, 臾몄젣 ?닿껐)'),(12,8,154,'遺꾨━ 遺덉븞???뺤젏???꾨떖?????덈떎','?ы쉶??/ ?뺤꽌'),(12,8,155,'遺紐⑤굹 移쒖닕???щ엺?먭쾶 ??媛뺥븯寃??섏??쒕떎','?ы쉶??/ ?뺤꽌'),(12,8,156,'?ㅻⅨ ?щ엺???됰룞??愿李고븯怨?紐⑤갑?섎젮??寃쏀뼢??媛뺥빐吏꾨떎','?ы쉶??/ ?뺤꽌');
    /*!40000 ALTER TABLE `milestone` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `milestone_state`
    --
    
    DROP TABLE IF EXISTS `milestone_state`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `milestone_state` (
      `current_amount` int NOT NULL,
      `date` date DEFAULT NULL,
      `is_achieved` bit(1) NOT NULL,
      `child_id` bigint NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `milestaone_id` bigint NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FKfmlghmw33iiy51j46vskyia15` (`child_id`),
      KEY `FK7t9by6vfwqlxo7nlhvpr2frjv` (`milestaone_id`),
      CONSTRAINT `FK7t9by6vfwqlxo7nlhvpr2frjv` FOREIGN KEY (`milestaone_id`) REFERENCES `milestone` (`id`),
      CONSTRAINT `FKfmlghmw33iiy51j46vskyia15` FOREIGN KEY (`child_id`) REFERENCES `child` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=625 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `milestone_state`
    --
    
    LOCK TABLES `milestone_state` WRITE;
    /*!40000 ALTER TABLE `milestone_state` DISABLE KEYS */;
    INSERT INTO `milestone_state` VALUES (0,NULL,_binary '\0',1,1,1),(0,NULL,_binary '\0',1,2,2),(0,NULL,_binary '\0',1,3,3),(0,NULL,_binary '\0',1,4,4),(0,NULL,_binary '\0',1,5,5),(0,NULL,_binary '\0',1,6,6),(0,NULL,_binary '\0',1,7,7),(0,NULL,_binary '\0',1,8,8),(0,NULL,_binary '\0',1,9,9),(0,NULL,_binary '\0',1,10,10),(0,NULL,_binary '\0',1,11,11),(0,NULL,_binary '\0',1,12,12),(0,NULL,_binary '\0',1,13,13),(0,NULL,_binary '\0',1,14,14),(0,NULL,_binary '\0',1,15,15),(0,NULL,_binary '\0',1,16,16),(0,NULL,_binary '\0',1,17,17),(0,NULL,_binary '\0',1,18,18),(0,NULL,_binary '\0',1,19,19),(0,NULL,_binary '\0',1,20,20),(0,NULL,_binary '\0',1,21,21),(0,NULL,_binary '\0',1,22,22),(0,NULL,_binary '\0',1,23,23),(0,NULL,_binary '\0',1,24,24),(0,NULL,_binary '\0',1,25,25),(0,NULL,_binary '\0',1,26,26),(0,NULL,_binary '\0',1,27,27),(0,NULL,_binary '\0',1,28,28),(0,NULL,_binary '\0',1,29,29),(0,NULL,_binary '\0',1,30,30),(0,NULL,_binary '\0',1,31,31),(0,NULL,_binary '\0',1,32,32),(0,NULL,_binary '\0',1,33,33),(0,NULL,_binary '\0',1,34,34),(0,NULL,_binary '\0',1,35,35),(0,NULL,_binary '\0',1,36,36),(0,NULL,_binary '\0',1,37,37),(0,NULL,_binary '\0',1,38,38),(0,NULL,_binary '\0',1,39,39),(0,NULL,_binary '\0',1,40,40),(0,NULL,_binary '\0',1,41,41),(0,NULL,_binary '\0',1,42,42),(0,NULL,_binary '\0',1,43,43),(0,NULL,_binary '\0',1,44,44),(0,NULL,_binary '\0',1,45,45),(0,NULL,_binary '\0',1,46,46),(0,NULL,_binary '\0',1,47,47),(0,NULL,_binary '\0',1,48,48),(6,NULL,_binary '\0',1,49,49),(2,NULL,_binary '\0',1,50,50),(4,NULL,_binary '\0',1,51,51),(2,NULL,_binary '\0',1,52,52),(2,NULL,_binary '\0',1,53,53),(3,NULL,_binary '\0',1,54,54),(1,NULL,_binary '\0',1,55,55),(1,NULL,_binary '\0',1,56,56),(0,NULL,_binary '\0',1,57,57),(0,NULL,_binary '\0',1,58,58),(1,NULL,_binary '\0',1,59,59),(0,NULL,_binary '\0',1,60,60),(0,NULL,_binary '\0',1,61,61),(0,NULL,_binary '\0',1,62,62),(0,NULL,_binary '\0',1,63,63),(0,NULL,_binary '\0',1,64,64),(0,NULL,_binary '\0',1,65,65),(0,NULL,_binary '\0',1,66,66),(0,NULL,_binary '\0',1,67,67),(0,NULL,_binary '\0',1,68,68),(0,NULL,_binary '\0',1,69,69),(0,NULL,_binary '\0',1,70,70),(0,NULL,_binary '\0',1,71,71),(0,NULL,_binary '\0',1,72,72),(0,NULL,_binary '\0',1,73,73),(0,NULL,_binary '\0',1,74,74),(0,NULL,_binary '\0',1,75,75),(0,NULL,_binary '\0',1,76,76),(0,NULL,_binary '\0',1,77,77),(0,NULL,_binary '\0',1,78,78),(0,NULL,_binary '\0',1,79,79),(0,NULL,_binary '\0',1,80,80),(0,NULL,_binary '\0',1,81,81),(0,NULL,_binary '\0',1,82,82),(0,NULL,_binary '\0',1,83,83),(0,NULL,_binary '\0',1,84,84),(0,NULL,_binary '\0',1,85,85),(0,NULL,_binary '\0',1,86,86),(0,NULL,_binary '\0',1,87,87),(0,NULL,_binary '\0',1,88,88),(0,NULL,_binary '\0',1,89,89),(0,NULL,_binary '\0',1,90,90),(0,NULL,_binary '\0',1,91,91),(0,NULL,_binary '\0',1,92,92),(0,NULL,_binary '\0',1,93,93),(0,NULL,_binary '\0',1,94,94),(0,NULL,_binary '\0',1,95,95),(0,NULL,_binary '\0',1,96,96),(0,NULL,_binary '\0',1,97,97),(0,NULL,_binary '\0',1,98,98),(0,NULL,_binary '\0',1,99,99),(0,NULL,_binary '\0',1,100,100),(0,NULL,_binary '\0',1,101,101),(0,NULL,_binary '\0',1,102,102),(0,NULL,_binary '\0',1,103,103),(0,NULL,_binary '\0',1,104,104),(0,NULL,_binary '\0',1,105,105),(0,NULL,_binary '\0',1,106,106),(0,NULL,_binary '\0',1,107,107),(0,NULL,_binary '\0',1,108,108),(0,NULL,_binary '\0',1,109,109),(0,NULL,_binary '\0',1,110,110),(0,NULL,_binary '\0',1,111,111),(0,NULL,_binary '\0',1,112,112),(0,NULL,_binary '\0',1,113,113),(0,NULL,_binary '\0',1,114,114),(0,NULL,_binary '\0',1,115,115),(0,NULL,_binary '\0',1,116,116),(0,NULL,_binary '\0',1,117,117),(0,NULL,_binary '\0',1,118,118),(0,NULL,_binary '\0',1,119,119),(0,NULL,_binary '\0',1,120,120),(0,NULL,_binary '\0',1,121,121),(0,NULL,_binary '\0',1,122,122),(0,NULL,_binary '\0',1,123,123),(0,NULL,_binary '\0',1,124,124),(0,NULL,_binary '\0',1,125,125),(0,NULL,_binary '\0',1,126,126),(0,NULL,_binary '\0',1,127,127),(0,NULL,_binary '\0',1,128,128),(0,NULL,_binary '\0',1,129,129),(0,NULL,_binary '\0',1,130,130),(0,NULL,_binary '\0',1,131,131),(0,NULL,_binary '\0',1,132,132),(0,NULL,_binary '\0',1,133,133),(0,NULL,_binary '\0',1,134,134),(0,NULL,_binary '\0',1,135,135),(0,NULL,_binary '\0',1,136,136),(0,NULL,_binary '\0',1,137,137),(0,NULL,_binary '\0',1,138,138),(0,NULL,_binary '\0',1,139,139),(0,NULL,_binary '\0',1,140,140),(0,NULL,_binary '\0',1,141,141),(0,NULL,_binary '\0',1,142,142),(0,NULL,_binary '\0',1,143,143),(0,NULL,_binary '\0',1,144,144),(0,NULL,_binary '\0',1,145,145),(0,NULL,_binary '\0',1,146,146),(0,NULL,_binary '\0',1,147,147),(0,NULL,_binary '\0',1,148,148),(0,NULL,_binary '\0',1,149,149),(0,NULL,_binary '\0',1,150,150),(0,NULL,_binary '\0',1,151,151),(0,NULL,_binary '\0',1,152,152),(0,NULL,_binary '\0',1,153,153),(0,NULL,_binary '\0',1,154,154),(0,NULL,_binary '\0',1,155,155),(0,NULL,_binary '\0',1,156,156),(0,NULL,_binary '\0',2,157,1),(0,NULL,_binary '\0',2,158,2),(0,NULL,_binary '\0',2,159,3),(0,NULL,_binary '\0',2,160,4),(0,NULL,_binary '\0',2,161,5),(0,NULL,_binary '\0',2,162,6),(0,NULL,_binary '\0',2,163,7),(0,NULL,_binary '\0',2,164,8),(0,NULL,_binary '\0',2,165,9),(0,NULL,_binary '\0',2,166,10),(0,NULL,_binary '\0',2,167,11),(0,NULL,_binary '\0',2,168,12),(0,NULL,_binary '\0',2,169,13),(0,NULL,_binary '\0',2,170,14),(0,NULL,_binary '\0',2,171,15),(0,NULL,_binary '\0',2,172,16),(0,NULL,_binary '\0',2,173,17),(0,NULL,_binary '\0',2,174,18),(0,NULL,_binary '\0',2,175,19),(0,NULL,_binary '\0',2,176,20),(0,NULL,_binary '\0',2,177,21),(0,NULL,_binary '\0',2,178,22),(0,NULL,_binary '\0',2,179,23),(0,NULL,_binary '\0',2,180,24),(0,NULL,_binary '\0',2,181,25),(0,NULL,_binary '\0',2,182,26),(0,NULL,_binary '\0',2,183,27),(0,NULL,_binary '\0',2,184,28),(0,NULL,_binary '\0',2,185,29),(0,NULL,_binary '\0',2,186,30),(0,NULL,_binary '\0',2,187,31),(0,NULL,_binary '\0',2,188,32),(0,NULL,_binary '\0',2,189,33),(0,NULL,_binary '\0',2,190,34),(0,NULL,_binary '\0',2,191,35),(0,NULL,_binary '\0',2,192,36),(0,NULL,_binary '\0',2,193,37),(0,NULL,_binary '\0',2,194,38),(0,NULL,_binary '\0',2,195,39),(0,NULL,_binary '\0',2,196,40),(0,NULL,_binary '\0',2,197,41),(0,NULL,_binary '\0',2,198,42),(0,NULL,_binary '\0',2,199,43),(0,NULL,_binary '\0',2,200,44),(0,NULL,_binary '\0',2,201,45),(0,NULL,_binary '\0',2,202,46),(0,NULL,_binary '\0',2,203,47),(0,NULL,_binary '\0',2,204,48),(0,NULL,_binary '\0',2,205,49),(0,NULL,_binary '\0',2,206,50),(0,NULL,_binary '\0',2,207,51),(0,NULL,_binary '\0',2,208,52),(0,NULL,_binary '\0',2,209,53),(0,NULL,_binary '\0',2,210,54),(0,NULL,_binary '\0',2,211,55),(0,NULL,_binary '\0',2,212,56),(0,NULL,_binary '\0',2,213,57),(0,NULL,_binary '\0',2,214,58),(0,NULL,_binary '\0',2,215,59),(0,NULL,_binary '\0',2,216,60),(0,NULL,_binary '\0',2,217,61),(0,NULL,_binary '\0',2,218,62),(0,NULL,_binary '\0',2,219,63),(0,NULL,_binary '\0',2,220,64),(0,NULL,_binary '\0',2,221,65),(0,NULL,_binary '\0',2,222,66),(0,NULL,_binary '\0',2,223,67),(0,NULL,_binary '\0',2,224,68),(0,NULL,_binary '\0',2,225,69),(0,NULL,_binary '\0',2,226,70),(0,NULL,_binary '\0',2,227,71),(0,NULL,_binary '\0',2,228,72),(0,NULL,_binary '\0',2,229,73),(0,NULL,_binary '\0',2,230,74),(0,NULL,_binary '\0',2,231,75),(0,NULL,_binary '\0',2,232,76),(0,NULL,_binary '\0',2,233,77),(0,NULL,_binary '\0',2,234,78),(0,NULL,_binary '\0',2,235,79),(0,NULL,_binary '\0',2,236,80),(0,NULL,_binary '\0',2,237,81),(0,NULL,_binary '\0',2,238,82),(0,NULL,_binary '\0',2,239,83),(0,NULL,_binary '\0',2,240,84),(0,NULL,_binary '\0',2,241,85),(0,NULL,_binary '\0',2,242,86),(0,NULL,_binary '\0',2,243,87),(0,NULL,_binary '\0',2,244,88),(0,NULL,_binary '\0',2,245,89),(0,NULL,_binary '\0',2,246,90),(0,NULL,_binary '\0',2,247,91),(0,NULL,_binary '\0',2,248,92),(0,NULL,_binary '\0',2,249,93),(0,NULL,_binary '\0',2,250,94),(0,NULL,_binary '\0',2,251,95),(0,NULL,_binary '\0',2,252,96),(0,NULL,_binary '\0',2,253,97),(0,NULL,_binary '\0',2,254,98),(0,NULL,_binary '\0',2,255,99),(0,NULL,_binary '\0',2,256,100),(0,NULL,_binary '\0',2,257,101),(0,NULL,_binary '\0',2,258,102),(0,NULL,_binary '\0',2,259,103),(0,NULL,_binary '\0',2,260,104),(0,NULL,_binary '\0',2,261,105),(0,NULL,_binary '\0',2,262,106),(0,NULL,_binary '\0',2,263,107),(0,NULL,_binary '\0',2,264,108),(0,NULL,_binary '\0',2,265,109),(0,NULL,_binary '\0',2,266,110),(0,NULL,_binary '\0',2,267,111),(0,NULL,_binary '\0',2,268,112),(0,NULL,_binary '\0',2,269,113),(0,NULL,_binary '\0',2,270,114),(0,NULL,_binary '\0',2,271,115),(0,NULL,_binary '\0',2,272,116),(0,NULL,_binary '\0',2,273,117),(0,NULL,_binary '\0',2,274,118),(0,NULL,_binary '\0',2,275,119),(0,NULL,_binary '\0',2,276,120),(0,NULL,_binary '\0',2,277,121),(0,NULL,_binary '\0',2,278,122),(0,NULL,_binary '\0',2,279,123),(0,NULL,_binary '\0',2,280,124),(0,NULL,_binary '\0',2,281,125),(0,NULL,_binary '\0',2,282,126),(0,NULL,_binary '\0',2,283,127),(0,NULL,_binary '\0',2,284,128),(0,NULL,_binary '\0',2,285,129),(0,NULL,_binary '\0',2,286,130),(0,NULL,_binary '\0',2,287,131),(0,NULL,_binary '\0',2,288,132),(0,NULL,_binary '\0',2,289,133),(0,NULL,_binary '\0',2,290,134),(0,NULL,_binary '\0',2,291,135),(0,NULL,_binary '\0',2,292,136),(0,NULL,_binary '\0',2,293,137),(0,NULL,_binary '\0',2,294,138),(0,NULL,_binary '\0',2,295,139),(0,NULL,_binary '\0',2,296,140),(0,NULL,_binary '\0',2,297,141),(0,NULL,_binary '\0',2,298,142),(0,NULL,_binary '\0',2,299,143),(0,NULL,_binary '\0',2,300,144),(0,NULL,_binary '\0',2,301,145),(0,NULL,_binary '\0',2,302,146),(0,NULL,_binary '\0',2,303,147),(0,NULL,_binary '\0',2,304,148),(0,NULL,_binary '\0',2,305,149),(0,NULL,_binary '\0',2,306,150),(0,NULL,_binary '\0',2,307,151),(0,NULL,_binary '\0',2,308,152),(0,NULL,_binary '\0',2,309,153),(0,NULL,_binary '\0',2,310,154),(0,NULL,_binary '\0',2,311,155),(0,NULL,_binary '\0',2,312,156),(0,NULL,_binary '\0',4,469,1),(0,NULL,_binary '\0',4,470,2),(0,NULL,_binary '\0',4,471,3),(0,NULL,_binary '\0',4,472,4),(0,NULL,_binary '\0',4,473,5),(0,NULL,_binary '\0',4,474,6),(0,NULL,_binary '\0',4,475,7),(0,NULL,_binary '\0',4,476,8),(0,NULL,_binary '\0',4,477,9),(0,NULL,_binary '\0',4,478,10),(0,NULL,_binary '\0',4,479,11),(0,NULL,_binary '\0',4,480,12),(0,NULL,_binary '\0',4,481,13),(0,NULL,_binary '\0',4,482,14),(0,NULL,_binary '\0',4,483,15),(0,NULL,_binary '\0',4,484,16),(0,NULL,_binary '\0',4,485,17),(0,NULL,_binary '\0',4,486,18),(0,NULL,_binary '\0',4,487,19),(0,NULL,_binary '\0',4,488,20),(0,NULL,_binary '\0',4,489,21),(0,NULL,_binary '\0',4,490,22),(0,NULL,_binary '\0',4,491,23),(0,NULL,_binary '\0',4,492,24),(0,NULL,_binary '\0',4,493,25),(0,NULL,_binary '\0',4,494,26),(0,NULL,_binary '\0',4,495,27),(0,NULL,_binary '\0',4,496,28),(0,NULL,_binary '\0',4,497,29),(0,NULL,_binary '\0',4,498,30),(0,NULL,_binary '\0',4,499,31),(0,NULL,_binary '\0',4,500,32),(0,NULL,_binary '\0',4,501,33),(0,NULL,_binary '\0',4,502,34),(0,NULL,_binary '\0',4,503,35),(0,NULL,_binary '\0',4,504,36),(0,NULL,_binary '\0',4,505,37),(0,NULL,_binary '\0',4,506,38),(0,NULL,_binary '\0',4,507,39),(0,NULL,_binary '\0',4,508,40),(0,NULL,_binary '\0',4,509,41),(0,NULL,_binary '\0',4,510,42),(0,NULL,_binary '\0',4,511,43),(0,NULL,_binary '\0',4,512,44),(0,NULL,_binary '\0',4,513,45),(0,NULL,_binary '\0',4,514,46),(0,NULL,_binary '\0',4,515,47),(0,NULL,_binary '\0',4,516,48),(0,NULL,_binary '\0',4,517,49),(0,NULL,_binary '\0',4,518,50),(0,NULL,_binary '\0',4,519,51),(0,NULL,_binary '\0',4,520,52),(0,NULL,_binary '\0',4,521,53),(0,NULL,_binary '\0',4,522,54),(0,NULL,_binary '\0',4,523,55),(0,NULL,_binary '\0',4,524,56),(0,NULL,_binary '\0',4,525,57),(0,NULL,_binary '\0',4,526,58),(0,NULL,_binary '\0',4,527,59),(0,NULL,_binary '\0',4,528,60),(0,NULL,_binary '\0',4,529,61),(0,NULL,_binary '\0',4,530,62),(0,NULL,_binary '\0',4,531,63),(0,NULL,_binary '\0',4,532,64),(0,NULL,_binary '\0',4,533,65),(0,NULL,_binary '\0',4,534,66),(0,NULL,_binary '\0',4,535,67),(0,NULL,_binary '\0',4,536,68),(0,NULL,_binary '\0',4,537,69),(0,NULL,_binary '\0',4,538,70),(0,NULL,_binary '\0',4,539,71),(0,NULL,_binary '\0',4,540,72),(0,NULL,_binary '\0',4,541,73),(0,NULL,_binary '\0',4,542,74),(0,NULL,_binary '\0',4,543,75),(0,NULL,_binary '\0',4,544,76),(0,NULL,_binary '\0',4,545,77),(0,NULL,_binary '\0',4,546,78),(0,NULL,_binary '\0',4,547,79),(0,NULL,_binary '\0',4,548,80),(0,NULL,_binary '\0',4,549,81),(0,NULL,_binary '\0',4,550,82),(0,NULL,_binary '\0',4,551,83),(0,NULL,_binary '\0',4,552,84),(0,NULL,_binary '\0',4,553,85),(0,NULL,_binary '\0',4,554,86),(0,NULL,_binary '\0',4,555,87),(0,NULL,_binary '\0',4,556,88),(0,NULL,_binary '\0',4,557,89),(0,NULL,_binary '\0',4,558,90),(0,NULL,_binary '\0',4,559,91),(0,NULL,_binary '\0',4,560,92),(0,NULL,_binary '\0',4,561,93),(0,NULL,_binary '\0',4,562,94),(0,NULL,_binary '\0',4,563,95),(0,NULL,_binary '\0',4,564,96),(0,NULL,_binary '\0',4,565,97),(0,NULL,_binary '\0',4,566,98),(0,NULL,_binary '\0',4,567,99),(0,NULL,_binary '\0',4,568,100),(0,NULL,_binary '\0',4,569,101),(0,NULL,_binary '\0',4,570,102),(0,NULL,_binary '\0',4,571,103),(0,NULL,_binary '\0',4,572,104),(0,NULL,_binary '\0',4,573,105),(0,NULL,_binary '\0',4,574,106),(0,NULL,_binary '\0',4,575,107),(0,NULL,_binary '\0',4,576,108),(0,NULL,_binary '\0',4,577,109),(0,NULL,_binary '\0',4,578,110),(0,NULL,_binary '\0',4,579,111),(0,NULL,_binary '\0',4,580,112),(0,NULL,_binary '\0',4,581,113),(0,NULL,_binary '\0',4,582,114),(0,NULL,_binary '\0',4,583,115),(0,NULL,_binary '\0',4,584,116),(0,NULL,_binary '\0',4,585,117),(0,NULL,_binary '\0',4,586,118),(0,NULL,_binary '\0',4,587,119),(0,NULL,_binary '\0',4,588,120),(0,NULL,_binary '\0',4,589,121),(0,NULL,_binary '\0',4,590,122),(0,NULL,_binary '\0',4,591,123),(0,NULL,_binary '\0',4,592,124),(0,NULL,_binary '\0',4,593,125),(0,NULL,_binary '\0',4,594,126),(0,NULL,_binary '\0',4,595,127),(0,NULL,_binary '\0',4,596,128),(0,NULL,_binary '\0',4,597,129),(0,NULL,_binary '\0',4,598,130),(0,NULL,_binary '\0',4,599,131),(0,NULL,_binary '\0',4,600,132),(0,NULL,_binary '\0',4,601,133),(0,NULL,_binary '\0',4,602,134),(0,NULL,_binary '\0',4,603,135),(0,NULL,_binary '\0',4,604,136),(0,NULL,_binary '\0',4,605,137),(0,NULL,_binary '\0',4,606,138),(0,NULL,_binary '\0',4,607,139),(0,NULL,_binary '\0',4,608,140),(0,NULL,_binary '\0',4,609,141),(0,NULL,_binary '\0',4,610,142),(0,NULL,_binary '\0',4,611,143),(0,NULL,_binary '\0',4,612,144),(0,NULL,_binary '\0',4,613,145),(0,NULL,_binary '\0',4,614,146),(0,NULL,_binary '\0',4,615,147),(0,NULL,_binary '\0',4,616,148),(0,NULL,_binary '\0',4,617,149),(0,NULL,_binary '\0',4,618,150),(0,NULL,_binary '\0',4,619,151),(0,NULL,_binary '\0',4,620,152),(0,NULL,_binary '\0',4,621,153),(0,NULL,_binary '\0',4,622,154),(0,NULL,_binary '\0',4,623,155),(0,NULL,_binary '\0',4,624,156);
    /*!40000 ALTER TABLE `milestone_state` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `post`
    --
    
    DROP TABLE IF EXISTS `post`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `post` (
      `is_deleted` bit(1) NOT NULL,
      `next_media_order_seq` int NOT NULL,
      `created_at` datetime(6) NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `writer_id` bigint NOT NULL,
      `content` varchar(255) NOT NULL,
      `title` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FKh3voybp05rhyyvwlhflfrlti2` (`writer_id`),
      CONSTRAINT `FKh3voybp05rhyyvwlhflfrlti2` FOREIGN KEY (`writer_id`) REFERENCES `member` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `post`
    --
    
    LOCK TABLES `post` WRITE;
    /*!40000 ALTER TABLE `post` DISABLE KEYS */;
    INSERT INTO `post` VALUES (_binary '',1,'2024-10-07 16:58:51.766733',1,1,'dsazz','asd'),(_binary '',3,'2024-10-07 17:35:46.278651',2,1,'?롢뀕','?섎뒗 ?꾧린 ?좎듅'),(_binary '',1,'2024-10-08 03:11:30.520462',3,1,'?켳\r\njj\r\nqw\r\nw\r\new\r\nqwe\r\nqwe\r\nweq\r\nwe\r\nwe\r\nweq\r\n\r\nweq','ded'),(_binary '',1,'2024-10-08 03:11:55.022676',4,1,'daddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddaddadd','dsda'),(_binary '',1,'2024-10-09 17:20:54.552084',5,18,'?덈뀞?섏꽭??,'?덈뀞?섏꽭??),(_binary '\0',2,'2024-10-09 17:51:37.873009',6,19,'?ㅼ콈 ?대퍙','洹?붾?'),(_binary '\0',2,'2024-10-10 15:37:12.278969',7,1,'?꾩???留롮씠 ?섏뿀???댁슜?낅땲?? ?留??뚭린???꾧퉴?뚯꽌 ?щ┰?덈떎. ^^~','?곸븘 ?≪븘 轅???뚯븘媛?몄슂~~'),(_binary '\0',2,'2024-10-10 15:39:07.798867',8,3,'?댁젣 媛볥룎???곕━ ?꾩씠?낅땲??^^','?곕━ ?꾧린 洹?쎌짛?');
    /*!40000 ALTER TABLE `post` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `post_media`
    --
    
    DROP TABLE IF EXISTS `post_media`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `post_media` (
      `media_order` int DEFAULT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `post_id` bigint NOT NULL,
      `media_id` binary(16) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UKcbh3kwx9ocobb3y3jn93nth0o` (`media_id`),
      KEY `FKo5e3or8sh0maaq8jy948d3tf9` (`post_id`),
      CONSTRAINT `FKedvegnxhyt4dke852jfbbq1wp` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`),
      CONSTRAINT `FKo5e3or8sh0maaq8jy948d3tf9` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `post_media`
    --
    
    LOCK TABLES `post_media` WRITE;
    /*!40000 ALTER TABLE `post_media` DISABLE KEYS */;
    INSERT INTO `post_media` VALUES (1,1,2,_binary '?봳&jIm?쥶뾏2B'),(2,2,2,_binary '?\\??I듋???-'),(1,3,6,_binary 'w쁬#%첛꼯쳪\훋\?\?),(1,4,7,_binary '戮?A\粹???S'),(1,5,8,_binary '\??cF?펰\?넞0');
    /*!40000 ALTER TABLE `post_media` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `record`
    --
    
    DROP TABLE IF EXISTS `record`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `record` (
      `child_id` bigint NOT NULL,
      `created_at` datetime(6) NOT NULL,
      `end_date_time` datetime(6) DEFAULT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `start_date_time` datetime(6) NOT NULL,
      `type` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FK3967dl16sei05uxpektb32vos` (`child_id`),
      CONSTRAINT `FK3967dl16sei05uxpektb32vos` FOREIGN KEY (`child_id`) REFERENCES `child` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1047 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `record`
    --
    
    LOCK TABLES `record` WRITE;
    /*!40000 ALTER TABLE `record` DISABLE KEYS */;
    INSERT INTO `record` VALUES (1,'2024-10-07 16:50:42.166511',NULL,1,'2024-10-07 16:50:41.000000','?蹂'),(1,'2024-10-07 16:50:45.450068',NULL,2,'2024-10-07 16:50:45.000000','?뚮?'),(1,'2024-10-07 16:51:01.494018',NULL,3,'2024-10-07 16:51:01.000000','?앹궗'),(1,'2024-10-07 16:51:02.621390',NULL,4,'2024-10-07 16:51:02.000000','?좎텞'),(1,'2024-10-07 16:51:03.606577',NULL,5,'2024-10-07 16:51:03.000000','??),(1,'2024-10-07 16:51:48.063745','2024-10-07 16:51:47.000000',6,'2024-10-07 16:50:46.000000','?섎㈃'),(1,'2024-10-07 23:42:27.433095',NULL,12,'2024-10-07 23:42:27.000000','??),(1,'2024-10-08 01:22:23.772506',NULL,13,'2024-10-08 01:22:22.000000','?蹂'),(1,'2024-10-08 01:22:25.046056',NULL,14,'2024-10-08 01:22:23.000000','?뚮?'),(1,'2024-10-08 01:22:26.171085',NULL,15,'2024-10-08 01:22:25.000000','?蹂'),(1,'2024-10-08 01:22:26.814992',NULL,16,'2024-10-08 01:22:25.000000','?뚮?'),(1,'2024-10-08 01:22:30.146232','2024-10-08 01:22:29.000000',17,'2024-10-08 01:22:28.000000','?섎㈃'),(1,'2024-10-08 01:22:30.392538','2024-10-08 01:22:29.000000',18,'2024-10-08 01:22:29.000000','?섎㈃'),(1,'2024-10-08 01:22:30.632459','2024-10-08 01:22:29.000000',19,'2024-10-08 01:22:29.000000','?섎㈃'),(1,'2024-10-08 01:22:30.879728','2024-10-08 01:22:29.000000',20,'2024-10-08 01:22:29.000000','?섎㈃'),(1,'2024-10-08 01:22:31.120228','2024-10-08 01:22:30.000000',21,'2024-10-08 01:22:29.000000','?섎㈃'),(1,'2024-10-08 01:22:31.359731','2024-10-08 01:22:30.000000',22,'2024-10-08 01:22:30.000000','?섎㈃'),(1,'2024-10-08 01:22:31.608877','2024-10-08 01:22:30.000000',23,'2024-10-08 01:22:30.000000','?섎㈃'),(1,'2024-10-08 01:22:31.879071','2024-10-08 01:22:30.000000',24,'2024-10-08 01:22:30.000000','?섎㈃'),(1,'2024-10-08 01:22:32.134278','2024-10-08 01:22:31.000000',25,'2024-10-08 01:22:30.000000','?섎㈃'),(1,'2024-10-08 01:22:32.394721','2024-10-08 01:22:31.000000',26,'2024-10-08 01:22:31.000000','?섎㈃'),(1,'2024-10-08 01:22:32.665468','2024-10-08 01:22:31.000000',27,'2024-10-08 01:22:31.000000','?섎㈃'),(1,'2024-10-08 01:22:32.941928','2024-10-08 01:22:31.000000',28,'2024-10-08 01:22:31.000000','?섎㈃'),(1,'2024-10-08 01:22:33.219716','2024-10-08 01:22:32.000000',29,'2024-10-08 01:22:31.000000','?섎㈃'),(1,'2024-10-08 01:22:33.512952','2024-10-08 01:22:32.000000',30,'2024-10-08 01:22:32.000000','?섎㈃'),(1,'2024-10-08 01:22:33.819496','2024-10-08 01:22:32.000000',31,'2024-10-08 01:22:32.000000','?섎㈃'),(1,'2024-10-08 01:22:34.105465','2024-10-08 01:22:33.000000',32,'2024-10-08 01:22:32.000000','?섎㈃'),(1,'2024-10-08 01:22:34.404421','2024-10-08 01:22:33.000000',33,'2024-10-08 01:22:33.000000','?섎㈃'),(1,'2024-10-08 01:22:35.464280','2024-10-08 01:22:34.000000',34,'2024-10-08 01:22:34.000000','?섎㈃'),(1,'2024-10-08 01:22:35.641900','2024-10-08 01:22:34.000000',35,'2024-10-08 01:22:34.000000','?섎㈃'),(1,'2024-10-08 01:22:35.837671','2024-10-08 01:22:34.000000',36,'2024-10-08 01:22:34.000000','?섎㈃'),(1,'2024-10-08 01:22:36.040521','2024-10-08 01:22:34.000000',37,'2024-10-08 01:22:34.000000','?섎㈃'),(1,'2024-10-08 01:22:36.235081','2024-10-08 01:22:35.000000',38,'2024-10-08 01:22:35.000000','?섎㈃'),(1,'2024-10-08 01:22:36.432196','2024-10-08 01:22:35.000000',39,'2024-10-08 01:22:35.000000','?섎㈃'),(1,'2024-10-08 01:22:36.625460','2024-10-08 01:22:35.000000',40,'2024-10-08 01:22:35.000000','?섎㈃'),(1,'2024-10-08 01:22:36.826565','2024-10-08 01:22:35.000000',41,'2024-10-08 01:22:35.000000','?섎㈃'),(1,'2024-10-08 01:22:37.059958','2024-10-08 01:22:35.000000',42,'2024-10-08 01:22:35.000000','?섎㈃'),(1,'2024-10-08 01:22:37.277242','2024-10-08 01:22:36.000000',43,'2024-10-08 01:22:36.000000','?섎㈃'),(1,'2024-10-08 01:22:37.460097','2024-10-08 01:22:36.000000',44,'2024-10-08 01:22:36.000000','?섎㈃'),(1,'2024-10-08 01:22:37.654559','2024-10-08 01:22:36.000000',45,'2024-10-08 01:22:36.000000','?섎㈃'),(1,'2024-10-08 01:22:37.862886','2024-10-08 01:22:36.000000',46,'2024-10-08 01:22:36.000000','?섎㈃'),(1,'2024-10-08 01:22:38.088330','2024-10-08 01:22:36.000000',47,'2024-10-08 01:22:36.000000','?섎㈃'),(1,'2024-10-08 01:22:38.312758','2024-10-08 01:22:37.000000',48,'2024-10-08 01:22:37.000000','?섎㈃'),(1,'2024-10-08 01:22:38.560387','2024-10-08 01:22:37.000000',49,'2024-10-08 01:22:37.000000','?섎㈃'),(1,'2024-10-08 01:22:38.799174','2024-10-08 01:22:37.000000',50,'2024-10-08 01:22:37.000000','?섎㈃'),(1,'2024-10-08 01:22:39.026276','2024-10-08 01:22:37.000000',51,'2024-10-08 01:22:37.000000','?섎㈃'),(1,'2024-10-08 01:22:39.243333','2024-10-08 01:22:38.000000',52,'2024-10-08 01:22:38.000000','?섎㈃'),(1,'2024-10-08 01:22:39.474244','2024-10-08 01:22:38.000000',53,'2024-10-08 01:22:38.000000','?섎㈃'),(1,'2024-10-08 01:22:39.707046','2024-10-08 01:22:38.000000',54,'2024-10-08 01:22:38.000000','?섎㈃'),(1,'2024-10-08 01:22:39.924373','2024-10-08 01:22:38.000000',55,'2024-10-08 01:22:38.000000','?섎㈃'),(1,'2024-10-08 01:22:40.142418','2024-10-08 01:22:39.000000',56,'2024-10-08 01:22:38.000000','?섎㈃'),(1,'2024-10-08 01:22:40.338659','2024-10-08 01:22:39.000000',57,'2024-10-08 01:22:39.000000','?섎㈃'),(1,'2024-10-08 01:22:43.473104',NULL,58,'2024-10-08 01:22:42.000000','?좎텞'),(1,'2024-10-08 01:22:43.569382',NULL,59,'2024-10-08 01:22:42.000000','?좎텞'),(1,'2024-10-08 01:22:43.697318',NULL,60,'2024-10-08 01:22:42.000000','?좎텞'),(1,'2024-10-08 01:22:43.808007',NULL,61,'2024-10-08 01:22:42.000000','?좎텞'),(1,'2024-10-08 01:22:43.909099',NULL,62,'2024-10-08 01:22:42.000000','?좎텞'),(1,'2024-10-08 01:22:44.035986',NULL,63,'2024-10-08 01:22:42.000000','?좎텞'),(1,'2024-10-08 01:22:44.124513',NULL,64,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:44.247694',NULL,65,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:44.333865',NULL,66,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:44.446649',NULL,67,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:44.538629',NULL,68,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:44.649765',NULL,69,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:44.754919',NULL,70,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:44.868944',NULL,71,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:44.964805',NULL,72,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:45.084365',NULL,73,'2024-10-08 01:22:43.000000','?좎텞'),(1,'2024-10-08 01:22:45.183241',NULL,74,'2024-10-08 01:22:44.000000','?좎텞'),(1,'2024-10-08 01:22:45.302362',NULL,75,'2024-10-08 01:22:44.000000','?좎텞'),(1,'2024-10-08 01:22:45.400902',NULL,76,'2024-10-08 01:22:44.000000','?좎텞'),(1,'2024-10-08 01:22:45.535393',NULL,77,'2024-10-08 01:22:44.000000','?좎텞'),(1,'2024-10-08 01:22:45.625041',NULL,78,'2024-10-08 01:22:44.000000','?좎텞'),(1,'2024-10-08 01:22:45.766828',NULL,79,'2024-10-08 01:22:44.000000','?좎텞'),(1,'2024-10-08 01:22:45.873594',NULL,80,'2024-10-08 01:22:44.000000','?좎텞'),(1,'2024-10-08 01:22:45.984382',NULL,81,'2024-10-08 01:22:44.000000','?좎텞'),(1,'2024-10-08 01:22:46.089768',NULL,82,'2024-10-08 01:22:44.000000','?좎텞'),(1,'2024-10-08 01:22:46.209619',NULL,83,'2024-10-08 01:22:45.000000','?좎텞'),(1,'2024-10-08 01:22:46.322114',NULL,84,'2024-10-08 01:22:45.000000','?좎텞'),(1,'2024-10-08 01:22:46.457625',NULL,85,'2024-10-08 01:22:45.000000','?좎텞'),(1,'2024-10-08 01:22:46.547249',NULL,86,'2024-10-08 01:22:45.000000','?좎텞'),(1,'2024-10-08 01:22:46.666841',NULL,87,'2024-10-08 01:22:45.000000','?좎텞'),(1,'2024-10-08 01:22:46.772019',NULL,88,'2024-10-08 01:22:45.000000','?좎텞'),(1,'2024-10-08 01:22:46.907888',NULL,89,'2024-10-08 01:22:45.000000','?좎텞'),(1,'2024-10-08 01:22:46.998803',NULL,90,'2024-10-08 01:22:45.000000','?좎텞'),(1,'2024-10-08 01:22:47.132167',NULL,91,'2024-10-08 01:22:46.000000','?좎텞'),(1,'2024-10-08 01:22:47.229981',NULL,92,'2024-10-08 01:22:46.000000','?좎텞'),(1,'2024-10-08 01:22:47.379921',NULL,93,'2024-10-08 01:22:46.000000','?좎텞'),(1,'2024-10-08 01:22:47.470871',NULL,94,'2024-10-08 01:22:46.000000','?좎텞'),(1,'2024-10-08 01:22:47.589520',NULL,95,'2024-10-08 01:22:46.000000','?좎텞'),(1,'2024-10-08 01:22:47.679922',NULL,96,'2024-10-08 01:22:46.000000','?좎텞'),(1,'2024-10-08 01:22:47.822706',NULL,97,'2024-10-08 01:22:46.000000','?좎텞'),(1,'2024-10-08 01:22:47.944081',NULL,98,'2024-10-08 01:22:46.000000','?좎텞'),(1,'2024-10-08 01:22:48.077424',NULL,99,'2024-10-08 01:22:46.000000','?좎텞'),(1,'2024-10-08 01:22:48.189067',NULL,100,'2024-10-08 01:22:47.000000','?좎텞'),(1,'2024-10-08 01:22:48.310017',NULL,101,'2024-10-08 01:22:47.000000','?좎텞'),(1,'2024-10-08 01:22:48.423489',NULL,102,'2024-10-08 01:22:47.000000','?좎텞'),(1,'2024-10-08 01:22:48.549172',NULL,103,'2024-10-08 01:22:47.000000','?좎텞'),(1,'2024-10-08 01:22:48.669548',NULL,104,'2024-10-08 01:22:47.000000','?좎텞'),(1,'2024-10-08 01:22:48.790012',NULL,105,'2024-10-08 01:22:47.000000','?좎텞'),(1,'2024-10-08 01:22:48.910374',NULL,106,'2024-10-08 01:22:47.000000','?좎텞'),(1,'2024-10-08 01:22:49.030418',NULL,107,'2024-10-08 01:22:47.000000','?좎텞'),(1,'2024-10-08 01:22:49.135698',NULL,108,'2024-10-08 01:22:48.000000','?좎텞'),(1,'2024-10-08 01:22:49.262301',NULL,109,'2024-10-08 01:22:48.000000','?좎텞'),(1,'2024-10-08 01:22:49.374924',NULL,110,'2024-10-08 01:22:48.000000','?좎텞'),(1,'2024-10-08 01:22:49.510749',NULL,111,'2024-10-08 01:22:48.000000','?좎텞'),(1,'2024-10-08 01:22:49.849256',NULL,112,'2024-10-08 01:22:48.000000','??),(1,'2024-10-08 01:22:49.967019',NULL,113,'2024-10-08 01:22:48.000000','??),(1,'2024-10-08 01:22:50.200324',NULL,114,'2024-10-08 01:22:49.000000','??),(1,'2024-10-08 01:22:50.327991',NULL,115,'2024-10-08 01:22:49.000000','??),(1,'2024-10-08 01:22:50.434492',NULL,116,'2024-10-08 01:22:49.000000','??),(1,'2024-10-08 01:22:50.553553',NULL,117,'2024-10-08 01:22:49.000000','??),(1,'2024-10-08 01:22:50.650438',NULL,118,'2024-10-08 01:22:49.000000','??),(1,'2024-10-08 01:22:50.769967',NULL,119,'2024-10-08 01:22:49.000000','??),(1,'2024-10-08 01:22:50.868224',NULL,120,'2024-10-08 01:22:49.000000','??),(1,'2024-10-08 01:22:50.995143',NULL,121,'2024-10-08 01:22:49.000000','??),(1,'2024-10-08 01:22:51.099873',NULL,122,'2024-10-08 01:22:49.000000','??),(1,'2024-10-08 01:22:51.220414',NULL,123,'2024-10-08 01:22:50.000000','??),(1,'2024-10-08 01:22:51.340194',NULL,124,'2024-10-08 01:22:50.000000','??),(1,'2024-10-08 01:22:51.460135',NULL,125,'2024-10-08 01:22:50.000000','??),(1,'2024-10-08 01:22:51.579552',NULL,126,'2024-10-08 01:22:50.000000','??),(1,'2024-10-08 01:22:51.686111',NULL,127,'2024-10-08 01:22:50.000000','??),(1,'2024-10-08 01:22:51.797522',NULL,128,'2024-10-08 01:22:50.000000','??),(1,'2024-10-08 01:22:51.918261',NULL,129,'2024-10-08 01:22:50.000000','??),(1,'2024-10-08 01:22:52.021917',NULL,130,'2024-10-08 01:22:50.000000','??),(1,'2024-10-08 01:22:52.143474',NULL,131,'2024-10-08 01:22:51.000000','??),(1,'2024-10-08 01:22:52.233542',NULL,132,'2024-10-08 01:22:51.000000','??),(1,'2024-10-08 01:22:52.376683',NULL,133,'2024-10-08 01:22:51.000000','??),(1,'2024-10-08 01:22:52.482498',NULL,134,'2024-10-08 01:22:51.000000','??),(1,'2024-10-08 01:22:52.599712',NULL,135,'2024-10-08 01:22:51.000000','??),(1,'2024-10-08 01:22:52.697340',NULL,136,'2024-10-08 01:22:51.000000','??),(1,'2024-10-08 01:22:52.833303',NULL,137,'2024-10-08 01:22:51.000000','??),(1,'2024-10-08 01:22:52.952641',NULL,138,'2024-10-08 01:22:51.000000','??),(1,'2024-10-08 01:22:53.065425',NULL,139,'2024-10-08 01:22:51.000000','??),(1,'2024-10-08 01:22:53.172193',NULL,140,'2024-10-08 01:22:52.000000','??),(1,'2024-10-08 01:22:53.297739',NULL,141,'2024-10-08 01:22:52.000000','??),(1,'2024-10-08 01:22:53.409857',NULL,142,'2024-10-08 01:22:52.000000','??),(1,'2024-10-08 01:22:53.514675',NULL,143,'2024-10-08 01:22:52.000000','??),(1,'2024-10-08 01:22:53.620200',NULL,144,'2024-10-08 01:22:52.000000','??),(1,'2024-10-08 01:22:53.740797',NULL,145,'2024-10-08 01:22:52.000000','??),(1,'2024-10-08 01:22:53.844635',NULL,146,'2024-10-08 01:22:52.000000','??),(1,'2024-10-08 01:22:53.973142',NULL,147,'2024-10-08 01:22:52.000000','??),(1,'2024-10-08 01:22:54.070098',NULL,148,'2024-10-08 01:22:52.000000','??),(1,'2024-10-08 01:22:54.206180',NULL,149,'2024-10-08 01:22:53.000000','??),(1,'2024-10-08 01:22:54.295228',NULL,150,'2024-10-08 01:22:53.000000','??),(1,'2024-10-08 01:22:54.429215',NULL,151,'2024-10-08 01:22:53.000000','??),(1,'2024-10-08 01:22:54.527940',NULL,152,'2024-10-08 01:22:53.000000','??),(1,'2024-10-08 01:22:54.655512',NULL,153,'2024-10-08 01:22:53.000000','??),(1,'2024-10-08 01:22:54.768408',NULL,154,'2024-10-08 01:22:53.000000','??),(1,'2024-10-08 01:22:55.017430',NULL,155,'2024-10-08 01:22:53.000000','??),(1,'2024-10-08 01:22:55.196546',NULL,156,'2024-10-08 01:22:54.000000','??),(1,'2024-10-08 01:22:59.763851',NULL,157,'2024-10-08 01:22:58.000000','?좎텞'),(1,'2024-10-08 01:22:59.906143',NULL,158,'2024-10-08 01:22:58.000000','?좎텞'),(1,'2024-10-08 01:23:00.040377',NULL,159,'2024-10-08 01:22:58.000000','?좎텞'),(1,'2024-10-08 01:23:00.182017',NULL,160,'2024-10-08 01:22:59.000000','?좎텞'),(1,'2024-10-08 01:23:00.311558',NULL,161,'2024-10-08 01:22:59.000000','?좎텞'),(1,'2024-10-08 01:23:02.972307',NULL,162,'2024-10-08 01:23:01.000000','?뚮?'),(1,'2024-10-08 01:23:03.114864',NULL,163,'2024-10-08 01:23:02.000000','?뚮?'),(1,'2024-10-08 01:23:03.234977',NULL,164,'2024-10-08 01:23:02.000000','?뚮?'),(1,'2024-10-08 01:23:03.499613',NULL,165,'2024-10-08 01:23:02.000000','?뚮?'),(1,'2024-10-08 01:23:03.640892',NULL,166,'2024-10-08 01:23:02.000000','?뚮?'),(1,'2024-10-08 01:23:05.448547',NULL,167,'2024-10-08 01:23:04.000000','?뚮?'),(1,'2024-10-08 01:23:05.822708',NULL,168,'2024-10-08 01:23:04.000000','?뚮?'),(1,'2024-10-08 01:23:05.950780',NULL,169,'2024-10-08 01:23:04.000000','?뚮?'),(1,'2024-10-08 01:23:06.062669',NULL,170,'2024-10-08 01:23:04.000000','?뚮?'),(1,'2024-10-08 01:23:06.220199',NULL,171,'2024-10-08 01:23:05.000000','?뚮?'),(1,'2024-10-08 01:23:06.313251',NULL,172,'2024-10-08 01:23:05.000000','?뚮?'),(1,'2024-10-08 01:23:06.472246',NULL,173,'2024-10-08 01:23:05.000000','?뚮?'),(1,'2024-10-08 01:23:06.571713',NULL,174,'2024-10-08 01:23:05.000000','?뚮?'),(1,'2024-10-08 01:23:06.707769',NULL,175,'2024-10-08 01:23:05.000000','?뚮?'),(1,'2024-10-08 01:23:06.797314',NULL,176,'2024-10-08 01:23:05.000000','?뚮?'),(1,'2024-10-08 01:23:06.954227',NULL,177,'2024-10-08 01:23:05.000000','?뚮?'),(1,'2024-10-08 01:23:07.058748',NULL,178,'2024-10-08 01:23:05.000000','?뚮?'),(1,'2024-10-08 01:23:07.195079',NULL,179,'2024-10-08 01:23:06.000000','?뚮?'),(1,'2024-10-08 01:23:07.418440',NULL,180,'2024-10-08 01:23:06.000000','?뚮?'),(1,'2024-10-08 01:23:07.638855',NULL,181,'2024-10-08 01:23:06.000000','?뚮?'),(1,'2024-10-08 01:23:07.734009',NULL,182,'2024-10-08 01:23:06.000000','?뚮?'),(1,'2024-10-08 01:23:07.871854',NULL,183,'2024-10-08 01:23:06.000000','?뚮?'),(1,'2024-10-08 01:23:07.981757',NULL,184,'2024-10-08 01:23:06.000000','?뚮?'),(1,'2024-10-08 01:23:08.124966',NULL,185,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:08.214507',NULL,186,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:08.334010',NULL,187,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:08.439082',NULL,188,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:08.528897',NULL,189,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:08.673298',NULL,190,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:08.808361',NULL,191,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:08.901288',NULL,192,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:09.012427',NULL,193,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:09.104171',NULL,194,'2024-10-08 01:23:07.000000','?뚮?'),(1,'2024-10-08 01:23:09.220919',NULL,195,'2024-10-08 01:23:08.000000','?뚮?'),(1,'2024-10-08 01:23:09.318632',NULL,196,'2024-10-08 01:23:08.000000','?뚮?'),(1,'2024-10-08 01:23:09.453198',NULL,197,'2024-10-08 01:23:08.000000','?뚮?'),(1,'2024-10-08 01:23:09.563675',NULL,198,'2024-10-08 01:23:08.000000','?뚮?'),(1,'2024-10-08 01:23:09.678772',NULL,199,'2024-10-08 01:23:08.000000','?뚮?'),(1,'2024-10-08 01:23:09.796928',NULL,200,'2024-10-08 01:23:08.000000','?뚮?'),(1,'2024-10-08 01:23:09.917646',NULL,201,'2024-10-08 01:23:08.000000','?뚮?'),(1,'2024-10-08 01:23:10.037672',NULL,202,'2024-10-08 01:23:08.000000','?뚮?'),(1,'2024-10-08 01:23:10.195225',NULL,203,'2024-10-08 01:23:09.000000','?뚮?'),(1,'2024-10-08 01:23:10.315130',NULL,204,'2024-10-08 01:23:09.000000','?뚮?'),(1,'2024-10-08 01:23:10.457802',NULL,205,'2024-10-08 01:23:09.000000','?뚮?'),(1,'2024-10-08 01:23:10.585186',NULL,206,'2024-10-08 01:23:09.000000','?뚮?'),(1,'2024-10-08 01:23:10.705105',NULL,207,'2024-10-08 01:23:09.000000','?뚮?'),(1,'2024-10-08 01:23:10.828276',NULL,208,'2024-10-08 01:23:09.000000','?뚮?'),(1,'2024-10-08 01:23:10.960420',NULL,209,'2024-10-08 01:23:09.000000','?뚮?'),(1,'2024-10-08 01:23:11.065191',NULL,210,'2024-10-08 01:23:09.000000','?뚮?'),(1,'2024-10-08 01:23:11.215700',NULL,211,'2024-10-08 01:23:10.000000','?뚮?'),(1,'2024-10-08 01:23:11.365261',NULL,212,'2024-10-08 01:23:10.000000','?뚮?'),(1,'2024-10-08 01:23:11.507292',NULL,213,'2024-10-08 01:23:10.000000','?뚮?'),(1,'2024-10-08 01:23:11.627626',NULL,214,'2024-10-08 01:23:10.000000','?뚮?'),(1,'2024-10-08 01:23:11.781578',NULL,215,'2024-10-08 01:23:10.000000','?뚮?'),(1,'2024-10-08 01:23:11.896886',NULL,216,'2024-10-08 01:23:10.000000','?뚮?'),(1,'2024-10-08 01:23:12.070782',NULL,217,'2024-10-08 01:23:10.000000','?뚮?'),(1,'2024-10-08 01:23:12.198281',NULL,218,'2024-10-08 01:23:11.000000','?뚮?'),(1,'2024-10-08 01:23:12.346862',NULL,219,'2024-10-08 01:23:11.000000','?뚮?'),(1,'2024-10-08 01:23:12.578993',NULL,220,'2024-10-08 01:23:11.000000','?뚮?'),(1,'2024-10-08 01:23:12.676198',NULL,221,'2024-10-08 01:23:11.000000','?뚮?'),(1,'2024-10-08 01:23:12.805209',NULL,222,'2024-10-08 01:23:11.000000','?뚮?'),(1,'2024-10-08 01:23:12.913248',NULL,223,'2024-10-08 01:23:11.000000','?뚮?'),(1,'2024-10-08 01:23:13.045833',NULL,224,'2024-10-08 01:23:11.000000','?뚮?'),(1,'2024-10-08 01:23:13.172121',NULL,225,'2024-10-08 01:23:12.000000','?뚮?'),(1,'2024-10-08 01:23:13.323119',NULL,226,'2024-10-08 01:23:12.000000','?뚮?'),(1,'2024-10-08 01:23:13.440443',NULL,227,'2024-10-08 01:23:12.000000','?뚮?'),(1,'2024-10-08 01:23:13.577410',NULL,228,'2024-10-08 01:23:12.000000','?뚮?'),(1,'2024-10-08 01:23:13.687202',NULL,229,'2024-10-08 01:23:12.000000','?뚮?'),(1,'2024-10-08 01:23:13.821698',NULL,230,'2024-10-08 01:23:12.000000','?뚮?'),(1,'2024-10-08 01:23:13.936598',NULL,231,'2024-10-08 01:23:12.000000','?뚮?'),(1,'2024-10-08 01:23:14.064269',NULL,232,'2024-10-08 01:23:12.000000','?뚮?'),(1,'2024-10-08 01:23:14.179599',NULL,233,'2024-10-08 01:23:13.000000','?뚮?'),(1,'2024-10-08 01:23:14.312740',NULL,234,'2024-10-08 01:23:13.000000','?뚮?'),(1,'2024-10-08 01:23:14.485709',NULL,235,'2024-10-08 01:23:13.000000','?뚮?'),(1,'2024-10-08 01:23:14.613905',NULL,236,'2024-10-08 01:23:13.000000','?뚮?'),(1,'2024-10-08 01:23:14.762693',NULL,237,'2024-10-08 01:23:13.000000','?뚮?'),(1,'2024-10-08 01:23:14.912993',NULL,238,'2024-10-08 01:23:13.000000','?뚮?'),(1,'2024-10-08 01:23:15.061885',NULL,239,'2024-10-08 01:23:13.000000','?뚮?'),(1,'2024-10-08 01:23:15.370215',NULL,240,'2024-10-08 01:23:14.000000','?뚮?'),(1,'2024-10-08 01:23:15.497709',NULL,241,'2024-10-08 01:23:14.000000','?뚮?'),(1,'2024-10-08 01:23:15.633067',NULL,242,'2024-10-08 01:23:14.000000','?뚮?'),(1,'2024-10-08 01:23:15.775941',NULL,243,'2024-10-08 01:23:14.000000','?뚮?'),(1,'2024-10-08 01:23:15.931860',NULL,244,'2024-10-08 01:23:14.000000','?뚮?'),(1,'2024-10-08 01:23:16.105810',NULL,245,'2024-10-08 01:23:14.000000','?뚮?'),(1,'2024-10-08 01:23:16.286111',NULL,246,'2024-10-08 01:23:15.000000','?뚮?'),(1,'2024-10-08 01:23:16.473920',NULL,247,'2024-10-08 01:23:15.000000','?뚮?'),(1,'2024-10-08 01:23:16.603499',NULL,248,'2024-10-08 01:23:15.000000','?뚮?'),(1,'2024-10-08 01:23:16.758410',NULL,249,'2024-10-08 01:23:15.000000','?뚮?'),(1,'2024-10-08 01:23:16.910774',NULL,250,'2024-10-08 01:23:15.000000','?뚮?'),(1,'2024-10-08 01:23:17.035205',NULL,251,'2024-10-08 01:23:15.000000','?뚮?'),(1,'2024-10-08 01:23:17.350040',NULL,252,'2024-10-08 01:23:16.000000','?뚮?'),(1,'2024-10-08 01:23:17.485436',NULL,253,'2024-10-08 01:23:16.000000','?뚮?'),(1,'2024-10-08 01:23:17.589727',NULL,254,'2024-10-08 01:23:16.000000','?뚮?'),(1,'2024-10-08 01:23:17.928409',NULL,255,'2024-10-08 01:23:16.000000','?뚮?'),(1,'2024-10-08 01:23:18.062916',NULL,256,'2024-10-08 01:23:16.000000','?뚮?'),(1,'2024-10-08 01:23:18.183344',NULL,257,'2024-10-08 01:23:17.000000','?뚮?'),(1,'2024-10-08 01:23:18.291694',NULL,258,'2024-10-08 01:23:17.000000','?뚮?'),(1,'2024-10-08 01:23:18.407390',NULL,259,'2024-10-08 01:23:17.000000','?뚮?'),(1,'2024-10-08 01:23:18.530925',NULL,260,'2024-10-08 01:23:17.000000','?뚮?'),(1,'2024-10-08 01:23:18.644130',NULL,261,'2024-10-08 01:23:17.000000','?뚮?'),(1,'2024-10-08 01:23:18.767041',NULL,262,'2024-10-08 01:23:17.000000','?뚮?'),(1,'2024-10-08 01:23:18.886793',NULL,263,'2024-10-08 01:23:17.000000','?뚮?'),(1,'2024-10-08 01:23:18.993149',NULL,264,'2024-10-08 01:23:17.000000','?뚮?'),(1,'2024-10-08 01:23:19.116331',NULL,265,'2024-10-08 01:23:18.000000','?뚮?'),(1,'2024-10-08 01:23:19.240551',NULL,266,'2024-10-08 01:23:18.000000','?뚮?'),(1,'2024-10-08 01:23:19.332632',NULL,267,'2024-10-08 01:23:18.000000','?뚮?'),(1,'2024-10-08 01:23:19.442704',NULL,268,'2024-10-08 01:23:18.000000','?뚮?'),(1,'2024-10-08 01:23:39.137830',NULL,269,'2024-10-08 01:23:38.000000','?蹂'),(1,'2024-10-08 01:23:39.385052',NULL,270,'2024-10-08 01:23:38.000000','?蹂'),(1,'2024-10-08 01:23:39.580888',NULL,271,'2024-10-08 01:23:38.000000','?蹂'),(1,'2024-10-08 01:23:39.781993',NULL,272,'2024-10-08 01:23:38.000000','?蹂'),(1,'2024-10-08 01:23:39.977242',NULL,273,'2024-10-08 01:23:38.000000','?蹂'),(1,'2024-10-08 01:23:40.078920',NULL,274,'2024-10-08 01:23:38.000000','?蹂'),(1,'2024-10-08 01:23:40.173984',NULL,275,'2024-10-08 01:23:39.000000','?蹂'),(1,'2024-10-08 01:23:40.277511',NULL,276,'2024-10-08 01:23:39.000000','?蹂'),(1,'2024-10-08 01:23:40.393171',NULL,277,'2024-10-08 01:23:39.000000','?蹂'),(1,'2024-10-08 01:23:40.536273',NULL,278,'2024-10-08 01:23:39.000000','?蹂'),(1,'2024-10-08 01:23:40.639510',NULL,279,'2024-10-08 01:23:39.000000','?蹂'),(1,'2024-10-08 01:23:40.753047',NULL,280,'2024-10-08 01:23:39.000000','?蹂'),(1,'2024-10-08 01:23:40.859092',NULL,281,'2024-10-08 01:23:39.000000','?蹂'),(1,'2024-10-08 01:23:40.891990',NULL,282,'2024-10-08 01:23:43.000000','??),(1,'2024-10-08 01:23:40.948687',NULL,283,'2024-10-08 01:23:39.000000','?蹂'),(1,'2024-10-08 01:23:41.232692',NULL,284,'2024-10-08 01:23:40.000000','?蹂'),(1,'2024-10-08 01:23:41.393656',NULL,285,'2024-10-08 01:23:40.000000','?蹂'),(1,'2024-10-08 01:23:41.503810',NULL,286,'2024-10-08 01:23:40.000000','?蹂'),(1,'2024-10-08 01:23:41.630714',NULL,287,'2024-10-08 01:23:40.000000','?蹂'),(1,'2024-10-08 01:23:41.746795',NULL,288,'2024-10-08 01:23:40.000000','?蹂'),(1,'2024-10-08 01:23:41.882193',NULL,289,'2024-10-08 01:23:40.000000','?蹂'),(1,'2024-10-08 01:23:42.048413',NULL,290,'2024-10-08 01:23:40.000000','?蹂'),(1,'2024-10-08 01:23:42.185296',NULL,291,'2024-10-08 01:23:41.000000','?蹂'),(1,'2024-10-08 01:23:42.329002',NULL,292,'2024-10-08 01:23:41.000000','?蹂'),(1,'2024-10-08 01:23:42.455703',NULL,293,'2024-10-08 01:23:41.000000','?蹂'),(1,'2024-10-08 01:23:42.577586',NULL,294,'2024-10-08 01:23:41.000000','?蹂'),(1,'2024-10-08 01:23:42.710100',NULL,295,'2024-10-08 01:23:41.000000','?蹂'),(1,'2024-10-08 01:23:42.834935',NULL,296,'2024-10-08 01:23:41.000000','?蹂'),(1,'2024-10-08 01:23:42.973910',NULL,297,'2024-10-08 01:23:41.000000','?蹂'),(1,'2024-10-08 01:23:43.142335',NULL,298,'2024-10-08 01:23:42.000000','?蹂'),(1,'2024-10-08 01:23:43.274555',NULL,299,'2024-10-08 01:23:42.000000','?蹂'),(1,'2024-10-08 01:23:43.396631',NULL,300,'2024-10-08 01:23:42.000000','?蹂'),(1,'2024-10-08 01:23:43.458018',NULL,301,'2024-10-08 01:23:45.000000','?蹂'),(1,'2024-10-08 01:23:43.547716',NULL,302,'2024-10-08 01:23:42.000000','?蹂'),(1,'2024-10-08 01:23:43.704586',NULL,303,'2024-10-08 01:23:42.000000','?蹂'),(1,'2024-10-08 01:23:43.831200',NULL,304,'2024-10-08 01:23:46.000000','?뚮?'),(1,'2024-10-08 01:23:44.094204',NULL,305,'2024-10-08 01:23:46.000000','?뚮?'),(1,'2024-10-08 01:23:44.389640',NULL,306,'2024-10-08 01:23:46.000000','?좎텞'),(1,'2024-10-08 01:23:44.598329',NULL,307,'2024-10-08 01:23:46.000000','?앹궗'),(1,'2024-10-08 01:23:44.815015',NULL,308,'2024-10-08 01:23:47.000000','?蹂'),(1,'2024-10-08 01:23:45.202623',NULL,309,'2024-10-08 01:23:47.000000','??),(1,'2024-10-08 01:23:45.551243',NULL,310,'2024-10-08 01:23:47.000000','??),(1,'2024-10-08 01:23:46.047140',NULL,311,'2024-10-08 01:23:48.000000','?좎텞'),(1,'2024-10-08 01:23:46.362480',NULL,312,'2024-10-08 01:23:48.000000','?좎텞'),(1,'2024-10-08 01:23:46.591947',NULL,313,'2024-10-08 01:23:48.000000','?좎텞'),(1,'2024-10-08 01:23:46.871007',NULL,314,'2024-10-08 01:23:49.000000','??),(1,'2024-10-08 01:23:47.002643',NULL,315,'2024-10-08 01:23:49.000000','?좎텞'),(1,'2024-10-08 01:23:47.333898',NULL,316,'2024-10-08 01:23:49.000000','?앹궗'),(1,'2024-10-08 01:23:47.594435',NULL,317,'2024-10-08 01:23:49.000000','?앹궗'),(1,'2024-10-08 01:23:47.814028',NULL,318,'2024-10-08 01:23:50.000000','?蹂'),(1,'2024-10-08 01:23:48.318892',NULL,319,'2024-10-08 01:23:50.000000','?좎텞'),(1,'2024-10-08 01:23:48.762475',NULL,320,'2024-10-08 01:23:50.000000','??),(1,'2024-10-08 01:23:48.991276',NULL,321,'2024-10-08 01:23:51.000000','?좎텞'),(1,'2024-10-08 01:23:49.274080',NULL,322,'2024-10-08 01:23:51.000000','?뚮?'),(1,'2024-10-08 01:23:49.501910',NULL,323,'2024-10-08 01:23:51.000000','?蹂'),(1,'2024-10-08 01:23:49.864268',NULL,324,'2024-10-08 01:23:52.000000','?앹궗'),(1,'2024-10-08 01:23:50.098688',NULL,325,'2024-10-08 01:23:52.000000','?좎텞'),(1,'2024-10-08 01:23:50.394657',NULL,326,'2024-10-08 01:23:52.000000','??),(1,'2024-10-08 01:23:50.520484',NULL,327,'2024-10-08 01:23:52.000000','??),(1,'2024-10-08 01:23:50.634550',NULL,328,'2024-10-08 01:23:52.000000','??),(1,'2024-10-08 01:23:50.741850',NULL,329,'2024-10-08 01:23:52.000000','??),(1,'2024-10-08 01:23:50.901679',NULL,330,'2024-10-08 01:23:53.000000','??),(1,'2024-10-08 01:23:51.070400',NULL,331,'2024-10-08 01:23:53.000000','??),(1,'2024-10-08 01:23:51.272115',NULL,332,'2024-10-08 01:23:53.000000','?좎텞'),(1,'2024-10-08 01:23:51.396898',NULL,333,'2024-10-08 01:23:53.000000','?좎텞'),(1,'2024-10-08 01:23:51.499953',NULL,334,'2024-10-08 01:23:53.000000','?좎텞'),(1,'2024-10-08 01:23:51.710087',NULL,335,'2024-10-08 01:23:53.000000','?좎텞'),(1,'2024-10-08 01:23:51.837821',NULL,336,'2024-10-08 01:23:54.000000','?좎텞'),(1,'2024-10-08 01:23:51.960406',NULL,337,'2024-10-08 01:23:54.000000','?좎텞'),(1,'2024-10-08 01:23:52.061489',NULL,338,'2024-10-08 01:23:54.000000','?좎텞'),(1,'2024-10-08 01:23:52.271773',NULL,339,'2024-10-08 01:23:54.000000','?좎텞'),(1,'2024-10-08 01:23:52.302348',NULL,340,'2024-10-08 01:23:54.000000','?좎텞'),(1,'2024-10-08 01:23:52.504472',NULL,341,'2024-10-08 01:23:54.000000','?좎텞'),(1,'2024-10-08 01:23:52.695070',NULL,342,'2024-10-08 01:23:54.000000','?좎텞'),(1,'2024-10-08 01:23:52.730051',NULL,343,'2024-10-08 01:23:54.000000','?좎텞'),(1,'2024-10-08 01:23:52.880050',NULL,344,'2024-10-08 01:23:55.000000','?좎텞'),(1,'2024-10-08 01:23:53.009224',NULL,345,'2024-10-08 01:23:55.000000','?좎텞'),(1,'2024-10-08 01:23:53.115588',NULL,346,'2024-10-08 01:23:55.000000','?좎텞'),(1,'2024-10-08 01:23:53.341890',NULL,347,'2024-10-08 01:23:55.000000','?좎텞'),(1,'2024-10-08 01:23:53.465798',NULL,348,'2024-10-08 01:23:55.000000','?좎텞'),(1,'2024-10-08 01:23:53.588404',NULL,349,'2024-10-08 01:23:55.000000','?좎텞'),(1,'2024-10-08 01:23:53.742379',NULL,350,'2024-10-08 01:23:55.000000','?좎텞'),(1,'2024-10-08 01:23:53.876623',NULL,351,'2024-10-08 01:23:56.000000','?좎텞'),(1,'2024-10-08 01:23:54.096315',NULL,352,'2024-10-08 01:23:56.000000','?좎텞'),(1,'2024-10-08 01:23:54.213587',NULL,353,'2024-10-08 01:23:56.000000','?좎텞'),(1,'2024-10-08 01:23:54.328274',NULL,354,'2024-10-08 01:23:56.000000','?좎텞'),(1,'2024-10-08 01:23:54.654569',NULL,355,'2024-10-08 01:23:56.000000','?좎텞'),(1,'2024-10-08 01:23:54.691800',NULL,356,'2024-10-08 01:23:56.000000','?좎텞'),(1,'2024-10-08 01:23:54.801006',NULL,357,'2024-10-08 01:23:56.000000','?좎텞'),(1,'2024-10-08 01:23:54.965053',NULL,358,'2024-10-08 01:23:57.000000','?좎텞'),(1,'2024-10-08 01:23:55.282769',NULL,359,'2024-10-08 01:23:57.000000','?좎텞'),(1,'2024-10-08 01:23:55.530346',NULL,360,'2024-10-08 01:23:57.000000','?좎텞'),(1,'2024-10-08 01:23:55.662755',NULL,361,'2024-10-08 01:23:57.000000','?좎텞'),(1,'2024-10-08 01:23:57.288928',NULL,362,'2024-10-08 01:23:59.000000','?좎텞'),(1,'2024-10-08 01:23:57.534562',NULL,363,'2024-10-08 01:23:59.000000','?좎텞'),(1,'2024-10-08 01:23:57.607079',NULL,364,'2024-10-08 01:23:59.000000','?좎텞'),(1,'2024-10-08 01:23:57.864583',NULL,365,'2024-10-08 01:24:00.000000','?좎텞'),(1,'2024-10-08 01:23:57.998366',NULL,366,'2024-10-08 01:24:00.000000','?뚮?'),(1,'2024-10-08 01:23:58.109671',NULL,367,'2024-10-08 01:24:00.000000','?뚮?'),(1,'2024-10-08 01:23:58.532415',NULL,368,'2024-10-08 01:24:00.000000','?蹂'),(1,'2024-10-08 01:23:58.850123',NULL,369,'2024-10-08 01:24:01.000000','?앹궗'),(1,'2024-10-08 01:23:59.119491',NULL,370,'2024-10-08 01:24:01.000000','?앹궗'),(1,'2024-10-08 01:23:59.383821',NULL,371,'2024-10-08 01:24:01.000000','?좎텞'),(1,'2024-10-08 01:23:59.563161',NULL,372,'2024-10-08 01:24:01.000000','?좎텞'),(1,'2024-10-08 01:23:59.917709',NULL,373,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:00.101485',NULL,374,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:00.323448',NULL,375,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:00.546083',NULL,376,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:00.672138',NULL,377,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:00.936232',NULL,378,'2024-10-08 01:24:03.000000','?좎텞'),(1,'2024-10-08 01:24:01.070695',NULL,379,'2024-10-08 01:24:03.000000','?좎텞'),(1,'2024-10-08 01:24:01.192874',NULL,380,'2024-10-08 01:24:03.000000','?좎텞'),(1,'2024-10-08 01:24:02.941792',NULL,381,'2024-10-08 01:24:01.000000','?좎텞'),(1,'2024-10-08 01:24:03.092355',NULL,382,'2024-10-08 01:24:01.000000','?좎텞'),(1,'2024-10-08 01:24:03.370425',NULL,383,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:03.519525',NULL,384,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:03.670545',NULL,385,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:03.810456',NULL,386,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:03.985820',NULL,387,'2024-10-08 01:24:02.000000','?좎텞'),(1,'2024-10-08 01:24:04.165202',NULL,388,'2024-10-08 01:24:03.000000','?좎텞'),(1,'2024-10-08 01:24:04.178085',NULL,389,'2024-10-08 01:24:06.000000','?좎텞'),(1,'2024-10-08 01:24:04.531514',NULL,390,'2024-10-08 01:24:06.000000','?좎텞'),(1,'2024-10-08 01:24:04.772447',NULL,391,'2024-10-08 01:24:03.000000','?좎텞'),(1,'2024-10-08 01:24:04.786518',NULL,392,'2024-10-08 01:24:06.000000','?좎텞'),(1,'2024-10-08 01:24:05.635889',NULL,393,'2024-10-08 01:24:04.000000','?좎텞'),(1,'2024-10-08 01:24:05.798638',NULL,394,'2024-10-08 01:24:04.000000','?좎텞'),(1,'2024-10-08 01:24:06.011223',NULL,395,'2024-10-08 01:24:04.000000','?좎텞'),(1,'2024-10-08 01:24:06.176455',NULL,396,'2024-10-08 01:24:05.000000','?좎텞'),(1,'2024-10-08 01:24:06.564674',NULL,397,'2024-10-08 01:24:05.000000','?좎텞'),(1,'2024-10-08 01:24:06.713714',NULL,398,'2024-10-08 01:24:05.000000','?좎텞'),(1,'2024-10-08 01:24:06.856300',NULL,399,'2024-10-08 01:24:05.000000','?좎텞'),(1,'2024-10-08 01:24:06.993960',NULL,400,'2024-10-08 01:24:05.000000','?좎텞'),(1,'2024-10-08 01:24:07.097056',NULL,401,'2024-10-08 01:24:09.000000','?좎텞'),(1,'2024-10-08 01:24:07.157162',NULL,402,'2024-10-08 01:24:06.000000','?좎텞'),(1,'2024-10-08 01:24:07.233291',NULL,403,'2024-10-08 01:24:09.000000','?좎텞'),(1,'2024-10-08 01:24:07.294004',NULL,404,'2024-10-08 01:24:06.000000','?좎텞'),(1,'2024-10-08 01:24:07.437471',NULL,405,'2024-10-08 01:24:06.000000','?좎텞'),(1,'2024-10-08 01:24:08.313532',NULL,406,'2024-10-08 01:24:07.000000','?앹궗'),(1,'2024-10-08 01:24:08.440171',NULL,407,'2024-10-08 01:24:07.000000','?앹궗'),(1,'2024-10-08 01:24:08.587811',NULL,408,'2024-10-08 01:24:07.000000','?앹궗'),(1,'2024-10-08 01:24:08.738962',NULL,409,'2024-10-08 01:24:07.000000','?앹궗'),(1,'2024-10-08 01:24:08.895858',NULL,410,'2024-10-08 01:24:07.000000','?앹궗'),(1,'2024-10-08 01:24:09.054264',NULL,411,'2024-10-08 01:24:07.000000','?앹궗'),(1,'2024-10-08 01:24:09.194728',NULL,412,'2024-10-08 01:24:08.000000','?앹궗'),(1,'2024-10-08 01:24:09.797865',NULL,413,'2024-10-08 01:24:08.000000','?뚮?'),(1,'2024-10-08 01:24:09.942459',NULL,414,'2024-10-08 01:24:08.000000','?뚮?'),(1,'2024-10-08 01:24:10.075847',NULL,415,'2024-10-08 01:24:08.000000','?뚮?'),(1,'2024-10-08 01:24:10.227697',NULL,416,'2024-10-08 01:24:09.000000','?뚮?'),(1,'2024-10-08 01:24:10.358882',NULL,417,'2024-10-08 01:24:09.000000','?뚮?'),(1,'2024-10-08 01:24:10.743727',NULL,418,'2024-10-08 01:24:09.000000','?蹂'),(1,'2024-10-08 01:24:10.906052',NULL,419,'2024-10-08 01:24:09.000000','?蹂'),(1,'2024-10-08 01:24:11.049437',NULL,420,'2024-10-08 01:24:09.000000','?蹂'),(1,'2024-10-08 01:24:11.194415',NULL,421,'2024-10-08 01:24:10.000000','?蹂'),(1,'2024-10-08 01:24:11.359436',NULL,422,'2024-10-08 01:24:10.000000','?蹂'),(1,'2024-10-08 01:24:11.518870',NULL,423,'2024-10-08 01:24:10.000000','?蹂'),(1,'2024-10-08 01:24:11.689269',NULL,424,'2024-10-08 01:24:10.000000','?蹂'),(1,'2024-10-08 01:24:11.859979',NULL,425,'2024-10-08 01:24:10.000000','?蹂'),(1,'2024-10-08 01:24:11.872041',NULL,426,'2024-10-08 01:24:14.000000','?좎텞'),(1,'2024-10-08 01:24:12.024810',NULL,427,'2024-10-08 01:24:10.000000','?蹂'),(1,'2024-10-08 01:24:12.198506',NULL,428,'2024-10-08 01:24:11.000000','?蹂'),(1,'2024-10-08 01:24:12.242289',NULL,429,'2024-10-08 01:24:14.000000','?좎텞'),(1,'2024-10-08 01:24:12.242954',NULL,430,'2024-10-08 01:24:14.000000','?좎텞'),(1,'2024-10-08 01:24:12.369275',NULL,431,'2024-10-08 01:24:11.000000','?蹂'),(1,'2024-10-08 01:24:12.527163',NULL,432,'2024-10-08 01:24:11.000000','?蹂'),(1,'2024-10-08 01:24:12.535043',NULL,433,'2024-10-08 01:24:14.000000','?좎텞'),(1,'2024-10-08 01:24:12.677543',NULL,434,'2024-10-08 01:24:11.000000','?蹂'),(1,'2024-10-08 01:24:12.689584',NULL,435,'2024-10-08 01:24:14.000000','?좎텞'),(1,'2024-10-08 01:24:12.829764',NULL,436,'2024-10-08 01:24:11.000000','?蹂'),(1,'2024-10-08 01:24:13.083644',NULL,437,'2024-10-08 01:24:15.000000','?좎텞'),(1,'2024-10-08 01:24:13.232549',NULL,438,'2024-10-08 01:24:15.000000','?좎텞'),(1,'2024-10-08 01:24:13.374445',NULL,439,'2024-10-08 01:24:15.000000','?좎텞'),(1,'2024-10-08 01:24:13.512157',NULL,440,'2024-10-08 01:24:15.000000','?좎텞'),(1,'2024-10-08 01:24:14.140126',NULL,441,'2024-10-08 01:24:13.000000','?좎텞'),(1,'2024-10-08 01:24:14.283302',NULL,442,'2024-10-08 01:24:13.000000','?좎텞'),(1,'2024-10-08 01:24:14.432769',NULL,443,'2024-10-08 01:24:13.000000','?좎텞'),(1,'2024-10-08 01:24:14.717162',NULL,444,'2024-10-08 01:24:13.000000','?앹궗'),(1,'2024-10-08 01:24:14.980034',NULL,445,'2024-10-08 01:24:13.000000','?좎텞'),(1,'2024-10-08 01:24:15.259918',NULL,446,'2024-10-08 01:24:14.000000','?앹궗'),(1,'2024-10-08 01:24:15.483244',NULL,447,'2024-10-08 01:24:14.000000','?좎텞'),(1,'2024-10-08 01:24:15.677319',NULL,448,'2024-10-08 01:24:14.000000','?앹궗'),(1,'2024-10-08 01:24:15.888990',NULL,449,'2024-10-08 01:24:14.000000','?좎텞'),(1,'2024-10-08 01:24:16.081743',NULL,450,'2024-10-08 01:24:14.000000','?앹궗'),(1,'2024-10-08 01:24:16.271256',NULL,451,'2024-10-08 01:24:15.000000','?좎텞'),(1,'2024-10-08 01:24:16.465073',NULL,452,'2024-10-08 01:24:15.000000','?앹궗'),(1,'2024-10-08 01:24:16.666297',NULL,453,'2024-10-08 01:24:15.000000','?좎텞'),(1,'2024-10-08 01:24:16.738866',NULL,454,'2024-10-08 01:24:18.000000','?좎텞'),(1,'2024-10-08 01:24:16.870104',NULL,455,'2024-10-08 01:24:15.000000','?앹궗'),(1,'2024-10-08 01:24:16.888698',NULL,456,'2024-10-08 01:24:19.000000','?좎텞'),(1,'2024-10-08 01:24:16.931349',NULL,457,'2024-10-08 01:24:16.000000','?좎텞'),(1,'2024-10-08 01:24:17.056765',NULL,458,'2024-10-08 01:24:15.000000','?좎텞'),(1,'2024-10-08 01:24:17.190795',NULL,459,'2024-10-08 01:24:16.000000','?좎텞'),(1,'2024-10-08 01:24:17.247505',NULL,460,'2024-10-08 01:24:16.000000','?앹궗'),(1,'2024-10-08 01:24:17.289274',NULL,461,'2024-10-08 01:24:19.000000','?좎텞'),(1,'2024-10-08 01:24:17.425931',NULL,462,'2024-10-08 01:24:16.000000','?좎텞'),(1,'2024-10-08 01:24:17.452888',NULL,463,'2024-10-08 01:24:16.000000','?좎텞'),(1,'2024-10-08 01:24:17.453667',NULL,464,'2024-10-08 01:24:16.000000','?좎텞'),(1,'2024-10-08 01:24:17.611781',NULL,465,'2024-10-08 01:24:16.000000','?앹궗'),(1,'2024-10-08 01:24:17.645810',NULL,466,'2024-10-08 01:24:19.000000','?좎텞'),(1,'2024-10-08 01:24:17.683191',NULL,467,'2024-10-08 01:24:16.000000','?좎텞'),(1,'2024-10-08 01:24:17.726755',NULL,468,'2024-10-08 01:24:17.000000','?좎텞'),(1,'2024-10-08 01:24:17.812272',NULL,469,'2024-10-08 01:24:20.000000','?좎텞'),(1,'2024-10-08 01:24:17.950645',NULL,470,'2024-10-08 01:24:16.000000','?蹂'),(1,'2024-10-08 01:24:18.096316',NULL,471,'2024-10-08 01:24:17.000000','?좎텞'),(1,'2024-10-08 01:24:18.096011',NULL,472,'2024-10-08 01:24:20.000000','?좎텞'),(1,'2024-10-08 01:24:18.098854',NULL,473,'2024-10-08 01:24:17.000000','?좎텞'),(1,'2024-10-08 01:24:18.145241',NULL,474,'2024-10-08 01:24:17.000000','?蹂'),(1,'2024-10-08 01:24:18.239939',NULL,475,'2024-10-08 01:24:20.000000','?좎텞'),(1,'2024-10-08 01:24:18.362605',NULL,476,'2024-10-08 01:24:17.000000','?좎텞'),(1,'2024-10-08 01:24:18.396935',NULL,477,'2024-10-08 01:24:20.000000','?좎텞'),(1,'2024-10-08 01:24:18.437554',NULL,478,'2024-10-08 01:24:17.000000','?좎텞'),(1,'2024-10-08 01:24:18.496677',NULL,479,'2024-10-08 01:24:17.000000','?좎텞'),(1,'2024-10-08 01:24:18.640428',NULL,480,'2024-10-08 01:24:17.000000','?좎텞'),(1,'2024-10-08 01:24:18.658210',NULL,481,'2024-10-08 01:24:20.000000','?좎텞'),(1,'2024-10-08 01:24:18.680704',NULL,482,'2024-10-08 01:24:17.000000','?좎텞'),(1,'2024-10-08 01:24:18.806429',NULL,483,'2024-10-08 01:24:20.000000','?좎텞'),(1,'2024-10-08 01:24:18.833064',NULL,484,'2024-10-08 01:24:17.000000','?좎텞'),(1,'2024-10-08 01:24:18.912002',NULL,485,'2024-10-08 01:24:18.000000','?좎텞'),(1,'2024-10-08 01:24:18.946445',NULL,486,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:19.152447',NULL,487,'2024-10-08 01:24:18.000000','?좎텞'),(1,'2024-10-08 01:24:19.153347',NULL,488,'2024-10-08 01:24:18.000000','?좎텞'),(1,'2024-10-08 01:24:19.207938',NULL,489,'2024-10-08 01:24:18.000000','??),(1,'2024-10-08 01:24:19.392626',NULL,490,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:19.545581',NULL,491,'2024-10-08 01:24:18.000000','?좎텞'),(1,'2024-10-08 01:24:19.546234',NULL,492,'2024-10-08 01:24:18.000000','?좎텞'),(1,'2024-10-08 01:24:19.546777',NULL,493,'2024-10-08 01:24:18.000000','?앹궗'),(1,'2024-10-08 01:24:19.556213',NULL,494,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:19.829187',NULL,495,'2024-10-08 01:24:19.000000','?좎텞'),(1,'2024-10-08 01:24:19.868872',NULL,496,'2024-10-08 01:24:18.000000','?뚮?'),(1,'2024-10-08 01:24:20.076490',NULL,497,'2024-10-08 01:24:19.000000','?좎텞'),(1,'2024-10-08 01:24:20.338688',NULL,498,'2024-10-08 01:24:19.000000','?좎텞'),(1,'2024-10-08 01:24:20.338742',NULL,499,'2024-10-08 01:24:19.000000','?좎텞'),(1,'2024-10-08 01:24:20.437726',NULL,500,'2024-10-08 01:24:22.000000','?좎텞'),(1,'2024-10-08 01:24:20.673081',NULL,501,'2024-10-08 01:24:19.000000','?좎텞'),(1,'2024-10-08 01:24:20.672956',NULL,502,'2024-10-08 01:24:19.000000','?좎텞'),(1,'2024-10-08 01:24:20.898590',NULL,503,'2024-10-08 01:24:22.000000','?좎텞'),(1,'2024-10-08 01:24:20.918596',NULL,504,'2024-10-08 01:24:23.000000','?좎텞'),(1,'2024-10-08 01:24:20.947924',NULL,505,'2024-10-08 01:24:19.000000','??),(1,'2024-10-08 01:24:20.951920',NULL,506,'2024-10-08 01:24:20.000000','?좎텞'),(1,'2024-10-08 01:24:21.107786',NULL,507,'2024-10-08 01:24:19.000000','??),(1,'2024-10-08 01:24:21.212464',NULL,508,'2024-10-08 01:24:20.000000','?좎텞'),(1,'2024-10-08 01:24:21.212494',NULL,509,'2024-10-08 01:24:20.000000','?좎텞'),(1,'2024-10-08 01:24:21.301114',NULL,510,'2024-10-08 01:24:20.000000','?앹궗'),(1,'2024-10-08 01:24:21.481950',NULL,511,'2024-10-08 01:24:20.000000','?앹궗'),(1,'2024-10-08 01:24:21.655168','2024-10-08 01:24:20.000000',512,'2024-10-08 01:22:39.000000','?섎㈃'),(1,'2024-10-08 01:24:21.821759',NULL,513,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:21.836902',NULL,514,'2024-10-08 01:24:20.000000','?뚮?'),(1,'2024-10-08 01:24:21.856906',NULL,515,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:22.051272',NULL,516,'2024-10-08 01:24:20.000000','?뚮?'),(1,'2024-10-08 01:24:22.092223',NULL,517,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:22.353688',NULL,518,'2024-10-08 01:24:21.000000','?蹂'),(1,'2024-10-08 01:24:22.360795',NULL,519,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:22.430289',NULL,520,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:22.707335',NULL,521,'2024-10-08 01:24:22.000000','?앹궗'),(1,'2024-10-08 01:24:22.733916',NULL,522,'2024-10-08 01:24:21.000000','?앹궗'),(1,'2024-10-08 01:24:22.835905',NULL,523,'2024-10-08 01:24:25.000000','?좎텞'),(1,'2024-10-08 01:24:22.906824',NULL,524,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:22.967026',NULL,525,'2024-10-08 01:24:22.000000','?앹궗'),(1,'2024-10-08 01:24:22.969310',NULL,526,'2024-10-08 01:24:22.000000','?앹궗'),(1,'2024-10-08 01:24:23.074126',NULL,527,'2024-10-08 01:24:21.000000','?좎텞'),(1,'2024-10-08 01:24:23.290071',NULL,528,'2024-10-08 01:24:22.000000','??),(1,'2024-10-08 01:24:23.291210',NULL,529,'2024-10-08 01:24:25.000000','?좎텞'),(1,'2024-10-08 01:24:23.384292',NULL,530,'2024-10-08 01:24:22.000000','?蹂'),(1,'2024-10-08 01:24:23.657339',NULL,531,'2024-10-08 01:24:22.000000','?뚮?'),(1,'2024-10-08 01:24:23.727695',NULL,532,'2024-10-08 01:24:25.000000','?좎텞'),(1,'2024-10-08 01:24:23.854944','2024-10-08 01:24:22.000000',533,'2024-10-08 01:24:21.000000','?섎㈃'),(1,'2024-10-08 01:24:23.925831',NULL,534,'2024-10-08 01:24:23.000000','?뚮?'),(1,'2024-10-08 01:24:24.026715',NULL,535,'2024-10-08 01:24:22.000000','?뚮?'),(1,'2024-10-08 01:24:24.183786','2024-10-08 01:24:23.000000',536,'2024-10-07 15:16:40.000000','?섎㈃'),(1,'2024-10-08 01:24:24.218291',NULL,537,'2024-10-08 01:24:26.000000','?좎텞'),(1,'2024-10-08 01:24:24.218376',NULL,538,'2024-10-08 01:24:23.000000','?뚮?'),(1,'2024-10-08 01:24:24.730077',NULL,539,'2024-10-08 01:24:24.000000','??),(1,'2024-10-08 01:24:24.752790',NULL,540,'2024-10-08 01:24:26.000000','?좎텞'),(1,'2024-10-08 01:24:24.956658',NULL,541,'2024-10-08 01:24:23.000000','?좎텞'),(1,'2024-10-08 01:24:25.045574',NULL,542,'2024-10-08 01:24:24.000000','??),(1,'2024-10-08 01:24:25.140190',NULL,543,'2024-10-08 01:24:24.000000','??),(1,'2024-10-08 01:24:25.316322',NULL,544,'2024-10-08 01:24:24.000000','??),(1,'2024-10-08 01:24:25.683590','2024-10-08 01:24:24.000000',545,'2024-10-08 01:24:23.000000','?섎㈃'),(1,'2024-10-08 01:24:25.853996',NULL,546,'2024-10-08 01:24:24.000000','?뚮?'),(1,'2024-10-08 01:24:26.009822',NULL,547,'2024-10-08 01:24:24.000000','?뚮?'),(1,'2024-10-08 01:24:26.193779',NULL,548,'2024-10-08 01:24:25.000000','?뚮?'),(1,'2024-10-08 01:24:26.416016',NULL,549,'2024-10-08 01:24:28.000000','?좎텞'),(1,'2024-10-08 01:24:26.718600',NULL,550,'2024-10-08 01:24:25.000000','?좎텞'),(1,'2024-10-08 01:24:26.890190',NULL,551,'2024-10-08 01:24:25.000000','??),(1,'2024-10-08 01:24:27.061918',NULL,552,'2024-10-08 01:24:29.000000','?좎텞'),(1,'2024-10-08 01:24:27.068516',NULL,553,'2024-10-08 01:24:25.000000','??),(1,'2024-10-08 01:24:27.413321',NULL,554,'2024-10-08 01:24:26.000000','?앹궗'),(1,'2024-10-08 01:24:27.601215',NULL,555,'2024-10-08 01:24:26.000000','?뚮?'),(1,'2024-10-08 01:24:27.757768',NULL,556,'2024-10-08 01:24:27.000000','?앹궗'),(1,'2024-10-08 01:24:28.055151',NULL,557,'2024-10-08 01:24:27.000000','?앹궗'),(1,'2024-10-08 01:24:28.055718',NULL,558,'2024-10-08 01:24:27.000000','?앹궗'),(1,'2024-10-08 01:24:28.150284',NULL,559,'2024-10-08 01:24:27.000000','?좎텞'),(1,'2024-10-08 01:24:28.418015',NULL,560,'2024-10-08 01:24:27.000000','?앹궗'),(1,'2024-10-08 01:24:28.491645',NULL,561,'2024-10-08 01:24:30.000000','?좎텞'),(1,'2024-10-08 01:24:28.524139',NULL,562,'2024-10-08 01:24:27.000000','??),(1,'2024-10-08 01:24:28.863223',NULL,563,'2024-10-08 01:24:27.000000','?앹궗'),(1,'2024-10-08 01:24:28.988938',NULL,564,'2024-10-08 01:24:31.000000','?좎텞'),(1,'2024-10-08 01:24:28.989007',NULL,565,'2024-10-08 01:24:28.000000','?앹궗'),(1,'2024-10-08 01:24:29.215924','2024-10-08 01:24:28.000000',566,'2024-10-08 01:24:27.000000','?섎㈃'),(1,'2024-10-08 01:24:29.287693',NULL,567,'2024-10-08 01:24:28.000000','?앹궗'),(1,'2024-10-08 01:24:29.291046',NULL,568,'2024-10-08 01:24:28.000000','?앹궗'),(1,'2024-10-08 01:24:29.571368',NULL,569,'2024-10-08 01:24:28.000000','?앹궗'),(1,'2024-10-08 01:24:29.982688',NULL,570,'2024-10-08 01:24:29.000000','?뚮?'),(1,'2024-10-08 01:24:29.982671',NULL,571,'2024-10-08 01:24:29.000000','?뚮?'),(1,'2024-10-08 01:24:30.331520','2024-10-08 01:24:29.000000',572,'2024-10-08 01:24:23.000000','?섎㈃'),(1,'2024-10-08 01:24:31.208512',NULL,573,'2024-10-08 01:24:30.000000','??),(1,'2024-10-08 01:24:31.208473',NULL,574,'2024-10-08 01:24:30.000000','??),(1,'2024-10-08 01:24:31.872158',NULL,575,'2024-10-08 01:24:34.000000','?좎텞'),(1,'2024-10-08 01:24:32.794309',NULL,576,'2024-10-08 01:24:34.000000','?앹궗'),(1,'2024-10-08 01:24:32.999109',NULL,577,'2024-10-08 01:24:32.000000','?蹂'),(1,'2024-10-08 01:24:33.344458',NULL,578,'2024-10-08 01:24:32.000000','?蹂'),(1,'2024-10-08 01:24:33.345025',NULL,579,'2024-10-08 01:24:32.000000','?蹂'),(1,'2024-10-08 01:24:33.533915',NULL,580,'2024-10-08 01:24:35.000000','?좎텞'),(1,'2024-10-08 01:24:33.635736',NULL,581,'2024-10-08 01:24:32.000000','?蹂'),(1,'2024-10-08 01:24:33.954757',NULL,582,'2024-10-08 01:24:33.000000','?蹂'),(1,'2024-10-08 01:24:34.347669',NULL,583,'2024-10-08 01:24:33.000000','?蹂'),(1,'2024-10-08 01:24:34.348068',NULL,584,'2024-10-08 01:24:33.000000','?蹂'),(1,'2024-10-08 01:24:36.059149',NULL,585,'2024-10-08 01:24:35.000000','?뚮?'),(1,'2024-10-08 01:24:36.411943',NULL,586,'2024-10-08 01:24:35.000000','?뚮?'),(1,'2024-10-08 01:24:36.710322',NULL,587,'2024-10-08 01:24:36.000000','?앹궗'),(1,'2024-10-08 01:24:36.711261',NULL,588,'2024-10-08 01:24:36.000000','?좎텞'),(1,'2024-10-08 01:24:37.043049',NULL,589,'2024-10-08 01:24:36.000000','?좎텞'),(1,'2024-10-08 01:24:37.043961',NULL,590,'2024-10-08 01:24:36.000000','?좎텞'),(1,'2024-10-08 01:24:37.529680',NULL,591,'2024-10-08 01:24:36.000000','??),(1,'2024-10-08 01:24:37.858162',NULL,592,'2024-10-08 01:24:37.000000','?좎텞'),(1,'2024-10-08 01:24:37.858081',NULL,593,'2024-10-08 01:24:37.000000','??),(1,'2024-10-08 01:24:38.173255',NULL,594,'2024-10-08 01:24:37.000000','?좎텞'),(1,'2024-10-08 01:24:38.466307',NULL,595,'2024-10-08 01:24:37.000000','?좎텞'),(1,'2024-10-08 01:24:38.466299',NULL,596,'2024-10-08 01:24:37.000000','?좎텞'),(1,'2024-10-08 01:24:38.539959',NULL,597,'2024-10-08 01:24:37.000000','?좎텞'),(1,'2024-10-08 01:24:38.973547',NULL,598,'2024-10-08 01:24:38.000000','?좎텞'),(1,'2024-10-08 01:24:38.973465',NULL,599,'2024-10-08 01:24:38.000000','?좎텞'),(1,'2024-10-08 01:24:39.351671',NULL,600,'2024-10-08 01:24:38.000000','?좎텞'),(1,'2024-10-08 01:24:39.351686',NULL,601,'2024-10-08 01:24:38.000000','?좎텞'),(1,'2024-10-08 01:24:39.694474',NULL,602,'2024-10-08 01:24:38.000000','?좎텞'),(1,'2024-10-08 01:24:39.695066',NULL,603,'2024-10-08 01:24:38.000000','?좎텞'),(1,'2024-10-08 01:24:40.008607',NULL,604,'2024-10-08 01:24:39.000000','?좎텞'),(1,'2024-10-08 01:24:40.045509',NULL,605,'2024-10-08 01:24:39.000000','?좎텞'),(1,'2024-10-08 01:24:40.505383',NULL,606,'2024-10-08 01:24:39.000000','?좎텞'),(1,'2024-10-08 01:24:40.505500',NULL,607,'2024-10-08 01:24:39.000000','?좎텞'),(1,'2024-10-08 01:24:40.851384',NULL,608,'2024-10-08 01:24:40.000000','?좎텞'),(1,'2024-10-08 01:24:41.297166',NULL,609,'2024-10-08 01:24:40.000000','?좎텞'),(1,'2024-10-08 01:24:41.641188',NULL,610,'2024-10-08 01:24:40.000000','?좎텞'),(1,'2024-10-08 01:24:41.641642',NULL,611,'2024-10-08 01:24:40.000000','?좎텞'),(1,'2024-10-08 01:24:42.565626',NULL,612,'2024-10-08 01:24:41.000000','?좎텞'),(1,'2024-10-08 01:24:42.871136',NULL,613,'2024-10-08 01:24:42.000000','?좎텞'),(1,'2024-10-08 01:24:42.917269',NULL,614,'2024-10-08 01:24:41.000000','?뚮?'),(1,'2024-10-08 01:24:43.090467',NULL,615,'2024-10-08 01:24:41.000000','?뚮?'),(1,'2024-10-08 01:24:43.218381',NULL,616,'2024-10-08 01:24:42.000000','?좎텞'),(1,'2024-10-08 01:24:43.291779',NULL,617,'2024-10-08 01:24:42.000000','?蹂'),(1,'2024-10-08 01:24:43.450340',NULL,618,'2024-10-08 01:24:42.000000','?蹂'),(1,'2024-10-08 01:24:43.548218',NULL,619,'2024-10-08 01:24:42.000000','?좎텞'),(1,'2024-10-08 01:24:43.789181',NULL,620,'2024-10-08 01:24:42.000000','?좎텞'),(1,'2024-10-08 01:24:43.864890',NULL,621,'2024-10-08 01:24:43.000000','?좎텞'),(1,'2024-10-08 01:24:43.942196',NULL,622,'2024-10-08 01:24:43.000000','?좎텞'),(1,'2024-10-08 01:24:44.132605',NULL,623,'2024-10-08 01:24:43.000000','??),(1,'2024-10-08 01:24:44.334934',NULL,624,'2024-10-08 01:24:43.000000','??),(1,'2024-10-08 01:24:44.642172',NULL,625,'2024-10-08 01:24:43.000000','?뚮?'),(1,'2024-10-08 01:24:44.842339',NULL,626,'2024-10-08 01:24:43.000000','?뚮?'),(1,'2024-10-08 01:24:45.311585',NULL,627,'2024-10-08 01:24:44.000000','??),(1,'2024-10-08 01:24:45.508945',NULL,628,'2024-10-08 01:24:44.000000','??),(1,'2024-10-08 01:24:45.708312',NULL,629,'2024-10-08 01:24:44.000000','??),(1,'2024-10-08 01:24:45.898531',NULL,630,'2024-10-08 01:24:44.000000','?앹궗'),(1,'2024-10-08 01:24:46.085726',NULL,631,'2024-10-08 01:24:45.000000','?좎텞'),(1,'2024-10-08 01:24:46.123955','2024-10-08 01:24:45.000000',632,'2024-10-08 01:24:28.000000','?섎㈃'),(1,'2024-10-08 01:24:46.230303',NULL,633,'2024-10-08 01:24:45.000000','?蹂'),(1,'2024-10-08 01:24:46.396988',NULL,634,'2024-10-08 01:24:45.000000','?蹂'),(1,'2024-10-08 01:24:46.503208',NULL,635,'2024-10-08 01:24:45.000000','?좎텞'),(1,'2024-10-08 01:24:46.503391',NULL,636,'2024-10-08 01:24:45.000000','?좎텞'),(1,'2024-10-08 01:24:46.690029',NULL,637,'2024-10-08 01:24:45.000000','?앹궗'),(1,'2024-10-08 01:24:46.866755',NULL,638,'2024-10-08 01:24:46.000000','?좎텞'),(1,'2024-10-08 01:24:46.866788',NULL,639,'2024-10-08 01:24:46.000000','?좎텞'),(1,'2024-10-08 01:24:46.872797',NULL,640,'2024-10-08 01:24:45.000000','?앹궗'),(1,'2024-10-08 01:24:47.067226',NULL,641,'2024-10-08 01:24:45.000000','?좎텞'),(1,'2024-10-08 01:24:47.176107',NULL,642,'2024-10-08 01:24:46.000000','?좎텞'),(1,'2024-10-08 01:24:47.183324',NULL,643,'2024-10-08 01:24:46.000000','?좎텞'),(1,'2024-10-08 01:24:47.244240',NULL,644,'2024-10-08 01:24:46.000000','?좎텞'),(1,'2024-10-08 01:24:47.421503',NULL,645,'2024-10-08 01:24:46.000000','?좎텞'),(1,'2024-10-08 01:24:47.669280',NULL,646,'2024-10-08 01:24:46.000000','?좎텞'),(1,'2024-10-08 01:24:47.671624',NULL,647,'2024-10-08 01:24:46.000000','?좎텞'),(1,'2024-10-08 01:24:47.875391','2024-10-08 01:24:46.000000',648,'2024-10-08 01:24:46.000000','?섎㈃'),(1,'2024-10-08 01:24:48.026065',NULL,649,'2024-10-08 01:24:47.000000','?좎텞'),(1,'2024-10-08 01:24:48.026630',NULL,650,'2024-10-08 01:24:47.000000','?좎텞'),(1,'2024-10-08 01:24:48.091034',NULL,651,'2024-10-08 01:24:47.000000','?좎텞'),(1,'2024-10-08 01:24:48.210638','2024-10-08 01:24:47.000000',652,'2024-10-08 01:24:46.000000','?섎㈃'),(1,'2024-10-08 01:24:48.524700',NULL,653,'2024-10-08 01:24:47.000000','?좎텞'),(1,'2024-10-08 01:24:48.524958',NULL,654,'2024-10-08 01:24:47.000000','?좎텞'),(1,'2024-10-08 01:24:48.586307',NULL,655,'2024-10-08 01:24:47.000000','?뚮?'),(1,'2024-10-08 01:24:48.761818',NULL,656,'2024-10-08 01:24:47.000000','?뚮?'),(1,'2024-10-08 01:24:48.900180',NULL,657,'2024-10-08 01:24:48.000000','?좎텞'),(1,'2024-10-08 01:24:48.901442',NULL,658,'2024-10-08 01:24:48.000000','?좎텞'),(1,'2024-10-08 01:24:48.923626',NULL,659,'2024-10-08 01:24:47.000000','?뚮?'),(1,'2024-10-08 01:24:49.112266',NULL,660,'2024-10-08 01:24:48.000000','?뚮?'),(1,'2024-10-08 01:24:49.249283',NULL,661,'2024-10-08 01:24:48.000000','?좎텞'),(1,'2024-10-08 01:24:49.249105',NULL,662,'2024-10-08 01:24:48.000000','?좎텞'),(1,'2024-10-08 01:24:49.290861',NULL,663,'2024-10-08 01:24:48.000000','?뚮?'),(1,'2024-10-08 01:24:49.578992',NULL,664,'2024-10-08 01:24:48.000000','?좎텞'),(1,'2024-10-08 01:24:49.740872',NULL,665,'2024-10-08 01:24:48.000000','?蹂'),(1,'2024-10-08 01:24:49.945723',NULL,666,'2024-10-08 01:24:48.000000','?蹂'),(1,'2024-10-08 01:24:50.939870',NULL,667,'2024-10-08 01:24:50.000000','??),(1,'2024-10-08 01:24:51.366892',NULL,668,'2024-10-08 01:24:50.000000','??),(1,'2024-10-08 01:24:59.065560',NULL,669,'2024-10-08 01:24:57.000000','?좎텞'),(1,'2024-10-08 01:24:59.234221',NULL,670,'2024-10-08 01:24:58.000000','?좎텞'),(1,'2024-10-08 01:24:59.412013',NULL,671,'2024-10-08 01:24:58.000000','?좎텞'),(1,'2024-10-08 01:24:59.625153',NULL,672,'2024-10-08 01:24:58.000000','?좎텞'),(1,'2024-10-08 01:24:59.718104',NULL,673,'2024-10-08 01:24:58.000000','?좎텞'),(1,'2024-10-08 01:24:59.881407',NULL,674,'2024-10-08 01:24:58.000000','?좎텞'),(1,'2024-10-08 01:24:59.978908',NULL,675,'2024-10-08 01:24:58.000000','?좎텞'),(1,'2024-10-08 01:25:00.156607',NULL,676,'2024-10-08 01:24:59.000000','?좎텞'),(1,'2024-10-08 01:25:00.253754',NULL,677,'2024-10-08 01:24:59.000000','?좎텞'),(1,'2024-10-08 01:25:00.519577',NULL,678,'2024-10-08 01:24:59.000000','??),(1,'2024-10-08 01:25:00.714432',NULL,679,'2024-10-08 01:24:59.000000','??),(1,'2024-10-08 01:25:00.823634',NULL,680,'2024-10-08 01:24:59.000000','??),(1,'2024-10-08 01:25:00.978315',NULL,681,'2024-10-08 01:24:59.000000','??),(1,'2024-10-08 01:25:01.079111',NULL,682,'2024-10-08 01:24:59.000000','??),(1,'2024-10-08 01:25:05.084317',NULL,683,'2024-10-08 01:25:04.000000','??),(1,'2024-10-08 01:25:05.488667',NULL,684,'2024-10-08 01:25:04.000000','??),(1,'2024-10-08 01:25:05.869875',NULL,685,'2024-10-08 01:25:05.000000','?좎텞'),(1,'2024-10-08 01:25:06.247388',NULL,686,'2024-10-08 01:25:05.000000','?좎텞'),(1,'2024-10-08 01:26:16.923594',NULL,710,'2024-10-08 01:26:16.000000','?蹂'),(1,'2024-10-08 01:26:17.230419',NULL,711,'2024-10-08 01:26:16.000000','?蹂'),(1,'2024-10-08 01:26:17.377765',NULL,712,'2024-10-08 01:26:17.000000','?蹂'),(1,'2024-10-08 01:26:17.425091',NULL,713,'2024-10-08 01:26:16.000000','?뚮?'),(1,'2024-10-08 01:26:17.527400',NULL,714,'2024-10-08 01:26:17.000000','?蹂'),(1,'2024-10-08 01:26:17.671895',NULL,715,'2024-10-08 01:26:17.000000','?蹂'),(1,'2024-10-08 01:26:17.812775',NULL,716,'2024-10-08 01:26:17.000000','?蹂'),(1,'2024-10-08 01:26:18.110086',NULL,717,'2024-10-08 01:26:17.000000','?蹂'),(1,'2024-10-08 01:26:18.230442',NULL,718,'2024-10-08 01:26:17.000000','?蹂'),(1,'2024-10-08 01:26:18.260564',NULL,719,'2024-10-08 01:26:17.000000','?蹂'),(1,'2024-10-08 01:26:18.418927',NULL,720,'2024-10-08 01:26:18.000000','?蹂'),(1,'2024-10-08 01:26:18.561735',NULL,721,'2024-10-08 01:26:18.000000','?蹂'),(1,'2024-10-08 01:26:18.695351',NULL,722,'2024-10-08 01:26:18.000000','?蹂'),(1,'2024-10-08 01:26:18.844671',NULL,723,'2024-10-08 01:26:18.000000','?蹂'),(1,'2024-10-08 01:26:18.993682',NULL,724,'2024-10-08 01:26:18.000000','?蹂'),(1,'2024-10-08 01:26:19.160670',NULL,725,'2024-10-08 01:26:18.000000','?蹂'),(1,'2024-10-08 01:26:19.293790',NULL,726,'2024-10-08 01:26:19.000000','?蹂'),(1,'2024-10-08 01:26:19.453839',NULL,727,'2024-10-08 01:26:19.000000','?蹂'),(1,'2024-10-08 01:26:19.626308',NULL,728,'2024-10-08 01:26:19.000000','?蹂'),(1,'2024-10-08 01:26:19.792324',NULL,729,'2024-10-08 01:26:19.000000','?蹂'),(1,'2024-10-08 01:26:19.850189',NULL,730,'2024-10-08 01:26:22.000000','?앹궗'),(1,'2024-10-08 01:26:19.938852',NULL,731,'2024-10-08 01:26:19.000000','?蹂'),(1,'2024-10-08 01:26:20.097002',NULL,732,'2024-10-08 01:26:19.000000','?蹂'),(1,'2024-10-08 01:26:20.226632',NULL,733,'2024-10-08 01:26:19.000000','?蹂'),(1,'2024-10-08 01:26:20.372994',NULL,734,'2024-10-08 01:26:20.000000','?蹂'),(1,'2024-10-08 01:26:20.518068',NULL,735,'2024-10-08 01:26:20.000000','?蹂'),(1,'2024-10-08 01:26:20.658099',NULL,736,'2024-10-08 01:26:20.000000','?蹂'),(1,'2024-10-08 01:26:20.817791',NULL,737,'2024-10-08 01:26:23.000000','?좎텞'),(1,'2024-10-08 01:26:20.817761',NULL,738,'2024-10-08 01:26:20.000000','?蹂'),(1,'2024-10-08 01:26:20.975343',NULL,739,'2024-10-08 01:26:20.000000','?蹂'),(1,'2024-10-08 01:26:21.131942',NULL,740,'2024-10-08 01:26:20.000000','?蹂'),(1,'2024-10-08 01:26:21.250163',NULL,741,'2024-10-08 01:26:20.000000','?蹂'),(1,'2024-10-08 01:26:21.405472',NULL,742,'2024-10-08 01:26:21.000000','?蹂'),(1,'2024-10-08 01:26:21.529343',NULL,743,'2024-10-08 01:26:21.000000','?蹂'),(1,'2024-10-08 01:26:21.589428',NULL,744,'2024-10-08 01:26:23.000000','??),(1,'2024-10-08 01:26:21.654851',NULL,745,'2024-10-08 01:26:21.000000','?蹂'),(1,'2024-10-08 01:26:21.800434',NULL,746,'2024-10-08 01:26:21.000000','?蹂'),(1,'2024-10-08 01:26:21.941573',NULL,747,'2024-10-08 01:26:21.000000','?蹂'),(1,'2024-10-08 01:26:22.088837',NULL,748,'2024-10-08 01:26:21.000000','?蹂'),(1,'2024-10-08 01:26:22.226692',NULL,749,'2024-10-08 01:26:21.000000','?蹂'),(1,'2024-10-08 01:26:22.371106',NULL,750,'2024-10-08 01:26:22.000000','?蹂'),(1,'2024-10-08 01:26:22.513928',NULL,751,'2024-10-08 01:26:22.000000','?蹂'),(1,'2024-10-08 01:26:22.632549',NULL,752,'2024-10-08 01:26:22.000000','?蹂'),(1,'2024-10-08 01:26:22.807287',NULL,753,'2024-10-08 01:26:22.000000','?蹂'),(1,'2024-10-08 01:26:22.962042',NULL,754,'2024-10-08 01:26:22.000000','?蹂'),(1,'2024-10-08 01:26:23.103046',NULL,755,'2024-10-08 01:26:22.000000','?蹂'),(1,'2024-10-08 01:26:23.224426',NULL,756,'2024-10-08 01:26:22.000000','?蹂'),(1,'2024-10-08 01:26:23.285512','2024-10-08 01:26:25.000000',757,'2024-10-08 01:26:24.000000','?섎㈃'),(1,'2024-10-08 01:26:23.859422',NULL,758,'2024-10-08 01:26:26.000000','?뚮?'),(1,'2024-10-08 01:26:24.299782',NULL,759,'2024-10-08 01:26:24.000000','?蹂'),(1,'2024-10-08 01:26:24.439606',NULL,760,'2024-10-08 01:26:24.000000','?蹂'),(1,'2024-10-08 01:26:24.581291',NULL,761,'2024-10-08 01:26:24.000000','?蹂'),(1,'2024-10-08 01:26:24.718961',NULL,762,'2024-10-08 01:26:24.000000','?蹂'),(1,'2024-10-08 01:26:24.852604',NULL,763,'2024-10-08 01:26:24.000000','?蹂'),(1,'2024-10-08 01:26:24.936527',NULL,764,'2024-10-08 01:26:27.000000','?좎텞'),(1,'2024-10-08 01:26:24.980132',NULL,765,'2024-10-08 01:26:24.000000','?蹂'),(1,'2024-10-08 01:26:25.115790',NULL,766,'2024-10-08 01:26:24.000000','?蹂'),(1,'2024-10-08 01:26:25.253469',NULL,767,'2024-10-08 01:26:24.000000','?蹂'),(1,'2024-10-08 01:26:25.395793',NULL,768,'2024-10-08 01:26:25.000000','?蹂'),(1,'2024-10-08 01:26:25.543302',NULL,769,'2024-10-08 01:26:25.000000','?蹂'),(1,'2024-10-08 01:26:25.671255',NULL,770,'2024-10-08 01:26:25.000000','?蹂'),(1,'2024-10-08 01:26:25.747822',NULL,771,'2024-10-08 01:26:27.000000','??),(1,'2024-10-08 01:26:25.826774',NULL,772,'2024-10-08 01:26:25.000000','?蹂'),(1,'2024-10-08 01:26:25.986987',NULL,773,'2024-10-08 01:26:25.000000','?蹂'),(1,'2024-10-08 01:26:26.134800',NULL,774,'2024-10-08 01:26:25.000000','?蹂'),(1,'2024-10-08 01:26:26.288719',NULL,775,'2024-10-08 01:26:26.000000','?蹂'),(1,'2024-10-08 01:26:26.437292',NULL,776,'2024-10-08 01:26:26.000000','?蹂'),(1,'2024-10-08 01:26:26.610831',NULL,777,'2024-10-08 01:26:26.000000','?蹂'),(1,'2024-10-08 01:26:26.759548',NULL,778,'2024-10-08 01:26:26.000000','?蹂'),(1,'2024-10-08 01:26:26.907400',NULL,779,'2024-10-08 01:26:29.000000','??),(1,'2024-10-08 01:26:27.129529',NULL,780,'2024-10-08 01:26:26.000000','?뚮?'),(1,'2024-10-08 01:26:27.253170',NULL,781,'2024-10-08 01:26:26.000000','?뚮?'),(1,'2024-10-08 01:26:27.395078',NULL,782,'2024-10-08 01:26:27.000000','?뚮?'),(1,'2024-10-08 01:26:27.543141',NULL,783,'2024-10-08 01:26:27.000000','?뚮?'),(1,'2024-10-08 01:26:27.697234',NULL,784,'2024-10-08 01:26:27.000000','?뚮?'),(1,'2024-10-08 01:26:27.829082',NULL,785,'2024-10-08 01:26:27.000000','?뚮?'),(1,'2024-10-08 01:26:27.981276',NULL,786,'2024-10-08 01:26:27.000000','?뚮?'),(1,'2024-10-08 01:26:28.145441',NULL,787,'2024-10-08 01:26:27.000000','?뚮?'),(1,'2024-10-08 01:26:28.289734',NULL,788,'2024-10-08 01:26:28.000000','?뚮?'),(1,'2024-10-08 01:26:28.439159',NULL,789,'2024-10-08 01:26:28.000000','?뚮?'),(1,'2024-10-08 01:26:28.581378',NULL,790,'2024-10-08 01:26:28.000000','?뚮?'),(1,'2024-10-08 01:26:28.715029',NULL,791,'2024-10-08 01:26:28.000000','?뚮?'),(1,'2024-10-08 01:26:28.856395',NULL,792,'2024-10-08 01:26:28.000000','?뚮?'),(1,'2024-10-08 01:26:28.999534',NULL,793,'2024-10-08 01:26:28.000000','?뚮?'),(1,'2024-10-08 01:26:29.286833',NULL,795,'2024-10-08 01:26:29.000000','?뚮?'),(1,'2024-10-08 03:22:29.781392',NULL,796,'2024-10-08 03:22:31.000000','?앹궗'),(1,'2024-10-08 03:22:30.590855',NULL,797,'2024-10-08 03:22:32.000000','?앹궗'),(1,'2024-10-08 03:22:31.080955',NULL,798,'2024-10-08 03:22:33.000000','?앹궗'),(1,'2024-10-08 03:22:31.602563',NULL,799,'2024-10-08 03:22:33.000000','?앹궗'),(1,'2024-10-08 03:22:32.188972',NULL,800,'2024-10-08 03:22:34.000000','?앹궗'),(1,'2024-10-08 03:22:32.789080',NULL,801,'2024-10-08 03:22:34.000000','?좎텞'),(1,'2024-10-08 03:22:33.388682',NULL,802,'2024-10-08 03:22:35.000000','?좎텞'),(1,'2024-10-08 03:22:34.176811',NULL,803,'2024-10-08 03:22:36.000000','?좎텞'),(1,'2024-10-08 03:22:35.146506',NULL,804,'2024-10-08 03:22:37.000000','?좎텞'),(1,'2024-10-08 03:22:36.144502',NULL,805,'2024-10-08 03:22:38.000000','?좎텞'),(1,'2024-10-08 03:22:36.988776',NULL,806,'2024-10-08 03:22:39.000000','?뚮?'),(1,'2024-10-08 03:22:37.820634',NULL,807,'2024-10-08 03:22:39.000000','?뚮?'),(1,'2024-10-08 03:22:39.162631',NULL,808,'2024-10-08 03:22:41.000000','?蹂'),(1,'2024-10-08 03:22:39.786643',NULL,809,'2024-10-08 03:22:41.000000','?蹂'),(1,'2024-10-08 03:22:40.576466',NULL,810,'2024-10-08 03:22:42.000000','?蹂'),(1,'2024-10-08 03:22:42.186948',NULL,811,'2024-10-08 03:22:44.000000','?앹궗'),(1,'2024-10-08 03:22:43.154675',NULL,812,'2024-10-08 03:22:45.000000','?앹궗'),(1,'2024-10-08 03:22:44.090958',NULL,813,'2024-10-08 03:22:46.000000','?앹궗'),(1,'2024-10-08 03:22:55.808934',NULL,814,'2024-10-08 03:22:57.000000','?앹궗'),(1,'2024-10-08 03:22:56.381013',NULL,815,'2024-10-08 03:22:58.000000','?앹궗'),(1,'2024-10-08 03:22:57.429170',NULL,816,'2024-10-08 03:22:59.000000','?앹궗'),(1,'2024-10-08 03:23:01.277016',NULL,817,'2024-10-08 03:23:03.000000','?앹궗'),(1,'2024-10-08 03:23:02.407150',NULL,818,'2024-10-08 03:23:04.000000','?앹궗'),(1,'2024-10-08 03:23:03.489241',NULL,819,'2024-10-08 03:23:05.000000','?앹궗'),(1,'2024-10-08 03:23:04.486996',NULL,820,'2024-10-08 03:23:06.000000','?앹궗'),(1,'2024-10-08 03:23:05.727190',NULL,821,'2024-10-08 03:23:07.000000','?좎텞'),(1,'2024-10-08 03:23:11.859386',NULL,822,'2024-10-08 03:23:13.000000','?蹂'),(1,'2024-10-08 03:23:12.527377',NULL,823,'2024-10-08 03:23:14.000000','?蹂'),(1,'2024-10-08 03:23:13.068266',NULL,824,'2024-10-08 03:23:15.000000','?蹂'),(1,'2024-10-08 03:23:14.605371',NULL,825,'2024-10-08 03:23:16.000000','?蹂'),(1,'2024-10-08 03:23:20.523133',NULL,826,'2024-10-08 03:23:22.000000','?뚮?'),(1,'2024-10-08 03:23:22.101403',NULL,827,'2024-10-08 03:23:24.000000','?뚮?'),(1,'2024-10-08 03:23:23.215208',NULL,828,'2024-10-08 03:23:25.000000','?뚮?'),(1,'2024-10-08 03:23:28.803479',NULL,829,'2024-10-08 03:23:30.000000','?뚮?'),(1,'2024-10-08 03:23:29.057343',NULL,830,'2024-10-08 03:23:31.000000','?뚮?'),(1,'2024-10-08 03:23:29.539122',NULL,831,'2024-10-08 03:23:31.000000','?뚮?'),(1,'2024-10-08 03:23:31.533708',NULL,832,'2024-10-08 03:23:33.000000','?蹂'),(1,'2024-10-08 03:23:32.355598',NULL,833,'2024-10-08 03:23:34.000000','?蹂'),(1,'2024-10-08 03:23:33.455915',NULL,834,'2024-10-08 03:23:35.000000','?蹂'),(1,'2024-10-08 03:23:33.949355',NULL,835,'2024-10-08 03:23:36.000000','?蹂'),(1,'2024-10-08 03:23:34.491616',NULL,836,'2024-10-08 03:23:36.000000','?蹂'),(1,'2024-10-08 03:23:40.017977',NULL,838,'2024-10-08 03:23:42.000000','?蹂'),(1,'2024-10-08 03:23:41.911780',NULL,839,'2024-10-08 03:23:43.000000','?蹂'),(1,'2024-10-08 03:23:43.037730',NULL,840,'2024-10-08 03:23:45.000000','?蹂'),(1,'2024-10-08 03:23:53.995971',NULL,841,'2024-10-08 03:23:56.000000','??),(1,'2024-10-08 03:23:54.546052',NULL,842,'2024-10-08 03:23:56.000000','?앹궗'),(1,'2024-10-08 03:23:55.111600',NULL,843,'2024-10-08 03:23:57.000000','?뚮?'),(1,'2024-10-08 03:23:55.112247',NULL,844,'2024-10-08 03:23:57.000000','?蹂'),(1,'2024-10-08 09:02:41.969328',NULL,845,'2024-10-08 09:02:41.000000','?앹궗'),(1,'2024-10-08 09:02:42.610870',NULL,846,'2024-10-08 09:02:41.000000','?蹂'),(2,'2024-10-08 09:07:27.906653',NULL,848,'2024-10-08 09:07:28.000000','??),(1,'2024-10-08 09:37:02.326710',NULL,850,'2024-10-08 09:37:01.000000','?蹂'),(1,'2024-10-08 09:37:03.657226',NULL,852,'2024-10-08 09:37:02.000000','?좎텞'),(1,'2024-10-08 09:38:58.429252',NULL,853,'2024-10-08 09:38:57.000000','?좎텞'),(1,'2024-10-08 09:41:35.755772',NULL,855,'2024-10-08 09:41:35.000000','??),(1,'2024-10-08 09:41:44.105892',NULL,856,'2024-10-08 09:41:43.000000','?좎텞'),(1,'2024-10-08 09:41:47.757298',NULL,857,'2024-10-08 09:41:47.000000','?蹂'),(2,'2024-10-08 09:45:04.944579',NULL,858,'2024-10-08 09:45:04.000000','?좎텞'),(2,'2024-10-08 09:45:05.588013',NULL,859,'2024-10-08 09:45:04.000000','?蹂'),(1,'2024-10-08 10:05:08.895340',NULL,860,'2024-10-08 10:05:08.000000','?앹궗'),(1,'2024-10-08 10:05:10.191422',NULL,861,'2024-10-08 10:05:09.000000','?좎텞'),(1,'2024-10-08 10:05:11.801718',NULL,862,'2024-10-08 10:05:11.000000','?좎텞'),(1,'2024-10-08 10:05:13.006014',NULL,863,'2024-10-08 10:05:12.000000','??),(1,'2024-10-08 10:05:14.193456',NULL,864,'2024-10-08 10:05:13.000000','??),(1,'2024-10-08 10:05:14.504959',NULL,865,'2024-10-08 10:05:13.000000','??),(1,'2024-10-08 10:05:21.342429',NULL,866,'2024-10-08 10:05:20.000000','?蹂'),(1,'2024-10-08 10:05:22.331691',NULL,867,'2024-10-08 10:05:21.000000','?蹂'),(1,'2024-10-08 10:05:30.177934',NULL,868,'2024-10-08 10:05:29.000000','?蹂'),(1,'2024-10-08 10:06:05.770344',NULL,869,'2024-10-08 10:06:05.000000','?뚮?'),(1,'2024-10-08 10:06:07.350916',NULL,870,'2024-10-08 10:06:06.000000','?뚮?'),(1,'2024-10-08 10:06:08.664926',NULL,871,'2024-10-08 10:06:07.000000','?뚮?'),(1,'2024-10-08 10:06:09.774704',NULL,872,'2024-10-08 10:06:09.000000','?뚮?'),(1,'2024-10-08 10:06:10.823347',NULL,873,'2024-10-08 10:06:10.000000','?뚮?'),(1,'2024-10-08 10:06:16.354119',NULL,874,'2024-10-08 10:06:15.000000','?뚮?'),(1,'2024-10-08 10:06:17.473810',NULL,875,'2024-10-08 10:06:16.000000','?뚮?'),(1,'2024-10-08 10:06:18.522476',NULL,876,'2024-10-08 10:06:17.000000','?뚮?'),(1,'2024-10-08 10:06:19.611172',NULL,877,'2024-10-08 10:06:18.000000','?뚮?'),(1,'2024-10-08 10:06:20.572093',NULL,878,'2024-10-08 10:06:19.000000','?뚮?'),(1,'2024-10-08 10:06:36.446950',NULL,879,'2024-10-08 10:06:35.000000','?蹂'),(1,'2024-10-08 10:06:39.033423',NULL,880,'2024-10-08 10:06:38.000000','?뚮?'),(1,'2024-10-08 10:06:40.715692',NULL,881,'2024-10-08 10:06:40.000000','?뚮?'),(1,'2024-10-08 10:06:42.285521',NULL,882,'2024-10-08 10:06:41.000000','?뚮?'),(1,'2024-10-08 10:06:43.634183',NULL,883,'2024-10-08 10:06:42.000000','?뚮?'),(1,'2024-10-08 10:06:57.141460',NULL,884,'2024-10-08 10:06:56.000000','?좎텞'),(1,'2024-10-08 10:07:03.475310',NULL,885,'2024-10-08 10:07:02.000000','?앹궗'),(1,'2024-10-08 10:07:05.793093',NULL,886,'2024-10-08 10:07:05.000000','?앹궗'),(1,'2024-10-08 10:07:07.230056',NULL,887,'2024-10-08 10:07:06.000000','?앹궗'),(1,'2024-10-08 10:07:10.913690',NULL,888,'2024-10-08 10:07:10.000000','?앹궗'),(1,'2024-10-08 10:07:12.231180',NULL,889,'2024-10-08 10:07:11.000000','?앹궗'),(1,'2024-10-08 10:07:21.291868',NULL,890,'2024-10-08 10:07:20.000000','?앹궗'),(1,'2024-10-08 10:07:23.170042',NULL,891,'2024-10-08 10:07:22.000000','?앹궗'),(1,'2024-10-08 10:07:24.005946',NULL,892,'2024-10-08 10:07:23.000000','?앹궗'),(1,'2024-10-08 10:07:24.806228',NULL,893,'2024-10-08 10:07:24.000000','?앹궗'),(1,'2024-10-08 10:07:25.639696',NULL,894,'2024-10-08 10:07:24.000000','?蹂'),(1,'2024-10-08 10:07:30.625501',NULL,895,'2024-10-08 10:07:29.000000','?蹂'),(1,'2024-10-08 10:07:31.428078',NULL,896,'2024-10-08 10:07:30.000000','?蹂'),(1,'2024-10-08 10:07:32.093708',NULL,897,'2024-10-08 10:07:31.000000','?뚮?'),(1,'2024-10-08 10:07:33.159405',NULL,898,'2024-10-08 10:07:32.000000','?뚮?'),(1,'2024-10-08 10:07:33.976190',NULL,899,'2024-10-08 10:07:33.000000','?뚮?'),(1,'2024-10-08 10:07:34.702738',NULL,900,'2024-10-08 10:07:34.000000','??),(1,'2024-10-08 10:07:35.977407',NULL,901,'2024-10-08 10:07:35.000000','??),(1,'2024-10-08 10:07:36.944413',NULL,902,'2024-10-08 10:07:36.000000','??),(1,'2024-10-08 10:07:37.878582',NULL,903,'2024-10-08 10:07:37.000000','??),(1,'2024-10-08 10:07:52.152535',NULL,904,'2024-10-08 10:07:51.000000','?蹂'),(1,'2024-10-08 10:07:53.222780',NULL,905,'2024-10-08 10:07:52.000000','?앹궗'),(1,'2024-10-08 10:26:11.410169',NULL,906,'2024-10-08 10:26:10.000000','?뚮?'),(1,'2024-10-08 10:26:12.443190',NULL,907,'2024-10-08 10:26:11.000000','?蹂'),(1,'2024-10-08 10:35:15.180600',NULL,914,'2024-10-08 10:35:14.000000','?좎텞'),(1,'2024-10-08 10:35:15.281074',NULL,915,'2024-10-08 10:35:14.000000','?좎텞'),(1,'2024-10-08 10:35:15.361557',NULL,916,'2024-10-08 10:35:14.000000','?좎텞'),(2,'2024-10-08 10:36:22.448435',NULL,917,'2024-10-08 10:36:21.000000','?좎텞'),(2,'2024-10-08 10:36:23.633502',NULL,918,'2024-10-08 10:36:22.000000','?앹궗'),(2,'2024-10-08 10:36:23.809766',NULL,919,'2024-10-08 10:36:23.000000','?蹂'),(2,'2024-10-08 10:36:24.126523',NULL,920,'2024-10-08 10:36:23.000000','?뚮?'),(2,'2024-10-08 10:36:24.926665','2024-10-08 10:36:24.000000',921,'2024-10-08 10:36:23.000000','?섎㈃'),(2,'2024-10-08 10:36:25.429154',NULL,922,'2024-10-08 10:36:24.000000','??),(1,'2024-10-08 13:57:59.579331',NULL,923,'2024-10-08 13:57:58.000000','?蹂'),(1,'2024-10-08 13:58:00.336550',NULL,924,'2024-10-08 13:57:59.000000','?뚮?'),(1,'2024-10-08 16:31:38.216838',NULL,925,'2024-10-08 16:31:37.000000','?앹궗'),(1,'2024-10-08 16:31:41.103236',NULL,926,'2024-10-08 16:31:40.000000','?뚮?'),(1,'2024-10-08 16:31:49.158554',NULL,927,'2024-10-08 16:31:48.000000','??),(1,'2024-10-08 16:31:50.530316',NULL,928,'2024-10-08 16:31:50.000000','?蹂'),(1,'2024-10-08 16:31:52.254076',NULL,929,'2024-10-08 16:31:52.000000','?좎텞'),(1,'2024-10-08 16:39:54.228203',NULL,930,'2024-10-08 16:39:53.000000','?앹궗'),(1,'2024-10-08 16:42:40.790255',NULL,931,'2024-10-08 16:42:40.000000','?蹂'),(1,'2024-10-08 16:42:41.247541',NULL,932,'2024-10-08 16:42:40.000000','?뚮?'),(1,'2024-10-08 16:42:41.729291',NULL,933,'2024-10-08 16:42:41.000000','?좎텞'),(1,'2024-10-08 16:42:42.210597',NULL,934,'2024-10-08 16:42:41.000000','??),(1,'2024-10-08 16:42:43.988519','2024-10-08 16:42:43.000000',935,'2024-10-08 16:31:53.000000','?섎㈃'),(1,'2024-10-08 16:42:51.348225','2024-10-08 16:42:51.000000',936,'2024-10-08 16:42:47.000000','?섎㈃'),(1,'2024-10-08 16:43:35.410331','2024-10-08 16:43:35.000000',937,'2024-10-08 16:43:24.000000','?섎㈃'),(1,'2024-10-08 16:43:44.985164','2024-10-08 16:43:44.000000',938,'2024-10-08 16:43:41.000000','?섎㈃'),(1,'2024-10-08 17:02:31.074676','2024-10-08 17:02:30.000000',939,'2024-10-08 17:02:29.000000','?섎㈃'),(1,'2024-10-08 17:02:35.195869',NULL,940,'2024-10-08 17:02:34.000000','?蹂'),(1,'2024-10-08 17:02:35.696710',NULL,941,'2024-10-08 17:02:35.000000','?앹궗'),(1,'2024-10-08 17:02:36.099961',NULL,942,'2024-10-08 17:02:35.000000','?좎텞'),(1,'2024-10-08 17:02:36.564978',NULL,943,'2024-10-08 17:02:36.000000','?뚮?'),(1,'2024-10-08 17:02:37.040538',NULL,944,'2024-10-08 17:02:36.000000','??),(1,'2024-10-08 17:02:45.738257','2024-10-08 17:02:45.000000',945,'2024-10-08 17:02:38.000000','?섎㈃'),(1,'2024-10-08 17:25:31.914499',NULL,946,'2024-10-08 17:25:31.000000','?蹂'),(1,'2024-10-08 17:35:50.640386',NULL,947,'2024-10-08 17:35:49.000000','?좎텞'),(1,'2024-10-08 17:37:21.127218','2024-10-08 17:37:19.000000',948,'2024-10-08 17:37:18.000000','?섎㈃'),(1,'2024-10-08 17:37:22.512183','2024-10-08 17:37:21.000000',949,'2024-10-08 17:37:20.000000','?섎㈃'),(1,'2024-10-08 17:37:23.172466',NULL,950,'2024-10-08 17:37:21.000000','??),(1,'2024-10-08 17:37:23.862576',NULL,951,'2024-10-08 17:37:22.000000','??),(1,'2024-10-08 17:37:25.390536',NULL,952,'2024-10-08 17:37:24.000000','??),(1,'2024-10-08 17:38:02.302905','2024-10-08 17:38:00.000000',953,'2024-10-08 17:37:53.000000','?섎㈃'),(1,'2024-10-08 17:41:02.872181',NULL,954,'2024-10-08 17:41:01.000000','?좎텞'),(1,'2024-10-08 17:43:36.777486',NULL,956,'2024-10-08 17:43:35.000000','?좎텞'),(1,'2024-10-09 14:09:00.663133',NULL,957,'2024-10-09 14:08:59.000000','?앹궗'),(2,'2024-10-09 14:09:47.132215',NULL,958,'2024-10-09 14:09:46.000000','?앹궗'),(1,'2024-10-09 14:35:52.541430',NULL,959,'2024-10-09 14:35:52.000000','?蹂'),(1,'2024-10-09 14:35:52.823694',NULL,960,'2024-10-09 14:35:52.000000','?蹂'),(1,'2024-10-09 14:35:53.221536',NULL,961,'2024-10-09 14:35:53.000000','?蹂'),(1,'2024-10-09 14:35:53.545797',NULL,962,'2024-10-09 14:35:53.000000','?蹂'),(1,'2024-10-09 14:35:53.989099',NULL,963,'2024-10-09 14:35:53.000000','?蹂'),(1,'2024-10-09 15:17:45.025055',NULL,964,'2024-10-09 15:17:45.000000','?蹂'),(1,'2024-10-09 15:17:47.133327',NULL,965,'2024-10-09 15:17:47.000000','?蹂'),(1,'2024-10-09 15:17:49.689098',NULL,966,'2024-10-09 15:17:49.000000','?蹂'),(1,'2024-10-09 15:17:52.896624',NULL,967,'2024-10-09 15:17:52.000000','?蹂'),(1,'2024-10-09 15:17:54.937016',NULL,968,'2024-10-09 15:17:54.000000','?뚮?'),(1,'2024-10-09 15:17:58.038902',NULL,969,'2024-10-09 15:17:58.000000','?뚮?'),(1,'2024-10-09 15:18:01.773052','2024-10-09 15:18:01.000000',970,'2024-10-09 15:17:59.000000','?섎㈃'),(1,'2024-10-09 15:18:08.086604',NULL,971,'2024-10-09 15:18:08.000000','?앹궗'),(1,'2024-10-09 15:18:10.016203',NULL,972,'2024-10-09 15:18:10.000000','?앹궗'),(1,'2024-10-09 15:59:02.395749',NULL,973,'2024-10-09 15:59:02.000000','?앹궗'),(1,'2024-10-09 15:59:10.923463',NULL,974,'2024-10-09 15:59:10.000000','?앹궗'),(1,'2024-10-09 16:46:32.355806',NULL,975,'2024-10-09 16:46:32.000000','?앹궗'),(1,'2024-10-09 16:46:33.007014',NULL,976,'2024-10-09 16:46:32.000000','?좎텞'),(1,'2024-10-09 16:46:33.662516',NULL,977,'2024-10-09 16:46:33.000000','??),(1,'2024-10-09 16:47:24.591184',NULL,978,'2024-10-09 16:47:24.000000','?앹궗'),(1,'2024-10-09 16:47:25.574553',NULL,979,'2024-10-09 16:47:25.000000','?좎텞'),(1,'2024-10-09 16:47:26.558469',NULL,980,'2024-10-09 16:47:26.000000','?뚮?'),(1,'2024-10-09 17:15:04.903995',NULL,981,'2024-10-09 17:15:04.000000','?앹궗'),(1,'2024-10-09 17:15:06.501251',NULL,982,'2024-10-09 17:15:06.000000','?뚮?'),(1,'2024-10-09 17:15:07.400521',NULL,983,'2024-10-09 17:15:07.000000','?앹궗'),(1,'2024-10-09 17:15:08.683674','2024-10-09 17:15:08.000000',984,'2024-10-09 17:15:05.000000','?섎㈃'),(1,'2024-10-09 17:15:09.768917',NULL,985,'2024-10-09 17:15:09.000000','?앹궗'),(1,'2024-10-09 17:15:10.933599',NULL,986,'2024-10-09 17:15:10.000000','?좎텞'),(1,'2024-10-09 17:15:12.117221',NULL,987,'2024-10-09 17:15:11.000000','?좎텞'),(1,'2024-10-10 09:38:19.782185',NULL,991,'2024-10-10 09:38:19.000000','??),(1,'2024-10-10 09:38:27.380508','2024-10-10 09:38:26.000000',992,'2024-10-10 09:38:17.000000','?섎㈃'),(1,'2024-10-10 09:38:37.734614',NULL,995,'2024-10-10 09:38:37.000000','?앹궗'),(1,'2024-10-10 10:20:10.167446',NULL,996,'2024-10-10 10:20:09.000000','?뚮?'),(1,'2024-10-10 10:45:12.169155',NULL,1013,'2024-10-10 10:45:11.000000','?뚮?'),(1,'2024-10-10 10:45:50.653805',NULL,1018,'2024-10-10 10:45:50.000000','?蹂'),(1,'2024-10-10 13:58:28.614489',NULL,1038,'2024-10-10 13:58:27.000000','?蹂'),(1,'2024-10-10 13:58:30.579130',NULL,1039,'2024-10-10 13:58:29.000000','?뚮?'),(1,'2024-10-10 13:58:32.528721','2024-10-10 13:58:31.000000',1040,'2024-10-10 13:58:02.000000','?섎㈃'),(1,'2024-10-10 13:58:34.829641',NULL,1041,'2024-10-10 13:58:33.000000','?앹궗'),(1,'2024-10-10 13:58:36.573286',NULL,1042,'2024-10-10 13:58:35.000000','??),(1,'2024-10-10 14:46:49.469001',NULL,1043,'2024-10-10 14:46:49.000000','?앹궗');
    /*!40000 ALTER TABLE `record` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `report`
    --
    
    DROP TABLE IF EXISTS `report`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `report` (
      `growth_degree` int NOT NULL,
      `max_degree` int NOT NULL,
      `month` int NOT NULL,
      `child_id` bigint NOT NULL,
      `created_at` datetime(6) NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `content` varchar(255) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `report`
    --
    
    LOCK TABLES `report` WRITE;
    /*!40000 ALTER TABLE `report` DISABLE KEYS */;
    INSERT INTO `report` VALUES (7,10,4,1,'2024-10-10 10:16:51.994602',4,'?꾧린???꾨컲?곸씤 ?깆옣 ?곹깭???됯퇏 ?섏??낅땲?? 泥댁쨷? 異쒖깮 ??3.5kg?먯꽌 4媛쒖썡 ??7.8kg濡?利앷??덉뒿?덈떎. 二쇱슂 諛쒕떖 ?댁젙?쒕? ???곕Ⅴ怨??덉뒿?덈떎. 遺紐⑤떂猿섏꽌??洹쒖튃?곸씤 嫄닿컯 寃吏꾩쓣 ?좎??섏꽭??'),(8,10,4,1,'2024-10-10 15:42:43.762654',5,'?꾧린???꾨컲?곸씤 ?깆옣 ?곹깭???됯퇏 ?댁긽?낅땲?? 異쒖깮 ??泥댁쨷 3.5kg?먯꽌 4媛쒖썡源뚯? 7.8kg源뚯? 利앷??덉뒿?덈떎. 二쇱슂 諛쒕떖 ?댁젙?쒕뒗 ???꾨떖?섍퀬 ?덉쑝硫? ?ㅼ쓬 諛쒕떖 ?④퀎??湲곕??⑸땲??');
    /*!40000 ALTER TABLE `report` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `schedule`
    --
    
    DROP TABLE IF EXISTS `schedule`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `schedule` (
      `child_id` bigint NOT NULL,
      `created_at` datetime(6) NOT NULL,
      `end_date_time` datetime(6) NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `member_id` bigint NOT NULL,
      `start_date_time` datetime(6) NOT NULL,
      `description` varchar(255) NOT NULL,
      `title` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FK7v0bi6rnf2n1filekw1lr9nwu` (`child_id`),
      KEY `FKn7js9p799qcts7le20pec9bxr` (`member_id`),
      CONSTRAINT `FK7v0bi6rnf2n1filekw1lr9nwu` FOREIGN KEY (`child_id`) REFERENCES `child` (`id`),
      CONSTRAINT `FKn7js9p799qcts7le20pec9bxr` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `schedule`
    --
    
    LOCK TABLES `schedule` WRITE;
    /*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
    INSERT INTO `schedule` VALUES (1,'2024-10-07 16:52:22.039489','2024-10-07 00:00:00.000000',1,1,'2024-10-07 00:00:00.000000','?좎뒪 媛꾨떎由?~','?좎뒪媛湲?),(1,'2024-10-07 17:20:07.771904','2024-10-12 18:20:06.000000',2,1,'2024-10-12 17:20:06.000000','?좎슂?쇱뿉 ?⑹쭊?댄삎 肄붾뵫 ?뚯뒪??,'?⑹쭊?댄삎 肄붾뵫 ?뚯뒪??),(1,'2024-10-07 17:20:30.714492','2024-12-10 10:00:00.000000',3,1,'2024-12-10 09:00:00.000000','12??以묒닚???⑹쭊???낆궗 ?덉젙','?⑹쭊???낆궗'),(1,'2024-10-07 17:23:59.675385','2024-10-07 00:00:00.000000',4,1,'2024-10-07 00:00:00.000000','?뗣뀑 ???좎뒪 ?먮쾲媛꾨떎','?좎뒪 ?먮쾲媛湲?),(1,'2024-10-08 01:28:41.968093','2024-10-08 00:00:00.000000',5,1,'2024-10-08 00:00:00.000000','?뗣뀑 ???좎뒪媛꾨떎 ?껁뀋','?좎뒪媛湲?),(2,'2024-10-08 10:27:35.561843','2024-10-08 00:00:00.000000',7,1,'2024-10-08 00:00:00.000000','asddsaz','dsa'),(1,'2024-10-08 16:44:16.812055','2024-10-08 00:00:00.000000',8,1,'2024-10-08 00:00:00.000000','123','asd'),(1,'2024-10-08 16:44:24.070935','2024-10-08 00:00:00.000000',9,1,'2024-10-08 00:00:00.000000','124421214','123'),(1,'2024-10-08 20:25:43.005168','2024-10-13 19:00:00.000000',10,1,'2024-10-13 15:00:00.000000','?쒖뼇??먯꽌 移댁뭅??2李?肄붾뵫?뚯뒪???쇱옄 蹂닿쿋??..','?ㅼ슫??移댁뭅??媛寃좊떎...'),(1,'2024-10-09 14:41:08.883571','2024-10-09 15:41:00.000000',12,1,'2024-10-09 14:00:00.000000','?ㅻ챸???쒓났?섏? ?딆븯?듬땲??','?뚯븘怨?諛⑸Ц'),(1,'2024-10-09 15:16:52.366714','2024-10-11 15:00:00.000000',13,1,'2024-10-11 14:00:00.000000','?ㅽ썑 2???ы썕?뚯븘怨??덉빟','?뚯븘怨??덉빟'),(1,'2024-10-09 16:16:56.374561','2024-10-09 22:00:00.000000',14,1,'2024-10-09 19:45:00.000000','?뉎뀋','留섏뭅??紐⑥엫'),(1,'2024-10-10 14:13:44.617712','2024-10-16 10:00:00.000000',21,1,'2024-10-16 09:00:00.000000','?ㅼ쓬 二??섏슂??9?쒖뿉 蹂묒썝 諛⑸Ц','蹂묒썝 諛⑸Ц'),(1,'2024-10-10 14:19:54.891646','2024-10-11 19:00:00.000000',22,1,'2024-10-11 18:00:00.000000','?ㅽ썑 6?쒖뿉 吏?먯뭅?섏뿉 留섏뭅??紐⑥엫','留섏뭅??紐⑥엫'),(1,'2024-10-10 14:20:37.416622','2024-10-10 20:00:00.000000',23,1,'2024-10-10 19:00:00.000000','?ㅻ뒛 ?ㅽ썑 7?쒖뿉 誘몄슜??媛?嫄곗빞.','誘몄슜??諛⑸Ц'),(1,'2024-10-10 14:46:36.699610','2024-10-10 21:00:00.000000',24,1,'2024-10-10 20:00:00.000000','?ㅻ뒛 8?쒖뿉 諛?癒뱀쑝??媛?嫄곗빞','????앹궗');
    /*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `standard_growth`
    --
    
    DROP TABLE IF EXISTS `standard_growth`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `standard_growth` (
      `id` int NOT NULL AUTO_INCREMENT,
      `month` int NOT NULL,
      `standard_weight` double NOT NULL,
      `gender` varchar(255) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `standard_growth`
    --
    
    LOCK TABLES `standard_growth` WRITE;
    /*!40000 ALTER TABLE `standard_growth` DISABLE KEYS */;
    INSERT INTO `standard_growth` VALUES (1,0,3.3,'?⑥븘'),(2,0,3.2,'?ъ븘'),(3,1,4.5,'?⑥븘'),(4,1,4.2,'?ъ븘'),(5,2,5.6,'?⑥븘'),(6,2,5.1,'?ъ븘'),(7,3,6.4,'?⑥븘'),(8,3,5.8,'?ъ븘'),(9,4,7,'?⑥븘'),(10,4,6.4,'?ъ븘'),(11,5,7.5,'?⑥븘'),(12,5,6.9,'?ъ븘'),(13,6,7.9,'?⑥븘'),(14,6,7.3,'?ъ븘'),(15,7,8.3,'?⑥븘'),(16,7,7.6,'?ъ븘'),(17,8,8.6,'?⑥븘'),(18,8,7.9,'?ъ븘'),(19,9,8.9,'?⑥븘'),(20,9,8.2,'?ъ븘'),(21,10,9.2,'?⑥븘'),(22,10,8.5,'?ъ븘'),(23,11,9.4,'?⑥븘'),(24,11,8.7,'?ъ븘'),(25,12,9.6,'?⑥븘'),(26,12,8.9,'?ъ븘'),(27,13,9.9,'?⑥븘'),(28,13,9.2,'?ъ븘'),(29,14,10.1,'?⑥븘'),(30,14,9.4,'?ъ븘'),(31,15,10.3,'?⑥븘'),(32,15,9.6,'?ъ븘'),(33,16,10.5,'?⑥븘'),(34,16,9.8,'?ъ븘'),(35,17,10.7,'?⑥븘'),(36,17,10,'?ъ븘'),(37,18,10.9,'?⑥븘'),(38,18,10.2,'?ъ븘'),(39,19,11.1,'?⑥븘'),(40,19,10.4,'?ъ븘'),(41,20,11.3,'?⑥븘'),(42,20,10.6,'?ъ븘'),(43,21,11.5,'?⑥븘'),(44,21,10.9,'?ъ븘'),(45,22,11.8,'?⑥븘'),(46,22,11.1,'?ъ븘'),(47,23,12,'?⑥븘'),(48,23,11.5,'?ъ븘');
    /*!40000 ALTER TABLE `standard_growth` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `story`
    --
    
    DROP TABLE IF EXISTS `story`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `story` (
      `cover_image_index` smallint NOT NULL,
      `end_date` date NOT NULL,
      `is_deleted` bit(1) NOT NULL,
      `start_date` date NOT NULL,
      `child_id` bigint NOT NULL,
      `created_at` datetime(6) NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `title` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FK7cobkguiiukbsda2lpsstcpoj` (`child_id`),
      CONSTRAINT `FK7cobkguiiukbsda2lpsstcpoj` FOREIGN KEY (`child_id`) REFERENCES `child` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `story`
    --
    
    LOCK TABLES `story` WRITE;
    /*!40000 ALTER TABLE `story` DISABLE KEYS */;
    INSERT INTO `story` VALUES (0,'2024-10-06',_binary '','2024-10-03',1,'2024-10-07 17:13:34.041850',1,'?곕━?꾧린'),(1,'2024-10-08',_binary '\0','2024-10-07',1,'2024-10-08 11:02:25.323587',2,'?숉솕?ㅼ슫'),(3,'2024-09-08',_binary '','2024-09-01',1,'2024-10-08 11:56:18.999696',3,'?곕━?꾧린 ?쒖뼱?쒖? 100??),(4,'2024-09-28',_binary '','2024-09-24',1,'2024-10-08 14:55:22.717196',4,'?곕━ ?꾧린 ?쒖뼱?쒖? 4媛쒖썡'),(6,'2024-10-08',_binary '','2024-10-01',1,'2024-10-08 15:38:10.650528',5,'?꾧린?먭린 ?숉솕?쇨린'),(6,'2024-10-02',_binary '','2024-10-01',1,'2024-10-08 16:32:07.565609',7,'?꾧린?먭린 ?숉솕?숉솕'),(5,'2024-10-06',_binary '\0','2024-10-01',1,'2024-10-08 22:38:45.938290',8,'?꾧린?먭린 ?숉솕?쇨린'),(3,'2024-10-05',_binary '\0','2024-10-01',1,'2024-10-08 23:41:16.234091',9,'?곕━ ?꾧린 10??泥レ＜ ?숉솕'),(3,'2024-10-10',_binary '','2024-10-01',1,'2024-10-10 13:16:43.703907',11,'?곕━ ?꾩씠 理쒓퀬??),(2,'2024-10-10',_binary '\0','2024-10-01',1,'2024-10-10 13:22:03.488950',12,'?곕━ ?꾩씠 理쒓퀬!');
    /*!40000 ALTER TABLE `story` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `story_page`
    --
    
    DROP TABLE IF EXISTS `story_page`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `story_page` (
      `page_number` int NOT NULL,
      `id` bigint NOT NULL AUTO_INCREMENT,
      `story_id` bigint NOT NULL,
      `image_id` binary(16) DEFAULT NULL,
      `content` varchar(255) NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UKmd1y6c6ekeof6s6e2yjoxwdje` (`image_id`),
      KEY `FKe1r1fuad1cswv7a3sw524dpen` (`story_id`),
      CONSTRAINT `FK460x01unyow86y9p25867ad4w` FOREIGN KEY (`image_id`) REFERENCES `media` (`id`),
      CONSTRAINT `FKe1r1fuad1cswv7a3sw524dpen` FOREIGN KEY (`story_id`) REFERENCES `story` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `story_page`
    --
    
    LOCK TABLES `story_page` WRITE;
    /*!40000 ALTER TABLE `story_page` DISABLE KEYS */;
    INSERT INTO `story_page` VALUES (1,7,2,_binary '慷KM\?Jm?\?藪p?','?쏅궇 ?쏆쟻??源?ㅼ슫?대씪???꾩씠媛 ?닿퀬 ?덉뿀?댁슂. '),(2,8,2,_binary 'INu\흕껱˛na\?[','?ㅼ슫?대뒗 ??긽 轅덉냽?먯꽌 紐⑦뿕??利먭린硫??먮옄?댁슂. '),(3,9,2,_binary '!\?lL쯓&춎F\??燦','?대뒓 ?? ?ㅼ슫?대뒗 ?몄쓬???뀁뿉 媛뉙삍?댁슂. '),(4,10,2,_binary '?~쩎G?~a\?ROp','怨꾩냽 ?멸퀬 ?좊뱾吏 紐삵븯???섎Т?ㅼ씠 ?덉뿀二? '),(5,11,2,_binary '뭹C?\?뢲(\휯qUw','?ㅼ슫?대뒗 留덈쾿??移섏븘瑜?李얠븘 紐⑦뿕???좊궗?댁슂. '),(6,12,2,_binary 'g??\Kァ\液\墓?b','留덈쾿??移섏븘??紐⑤뱺 寃껋쓣 諛앷쾶 ?덇퀬, ?ㅼ슫?대뒗 ?껋쑝硫??좊뱾?덈떟?덈떎.'),(1,37,8,_binary 'o+|W쟏/\?_,\?Q','?쏅궇 ?쏆쟻??源?ㅼ슫?대씪???꾩씠媛 ?닿퀬 ?덉뿀?댁슂.'),(2,38,8,_binary 'p\??짡\?\?\?덠','?ㅼ슫?대뒗 ?좎갑 ?명삎??瑗??덇퀬 轅덈굹?쇰줈 ?좊굹??寃껋쓣 醫뗭븘?덉뼱??'),(3,39,8,_binary '??L춣I戟?0.\?\?','?대뒓 ?? 洹몃뒗 留덈쾿??諛붽뎄?덈? 諛쒓껄?섍퀬 洹??덉뿉 ?곷떦 ?ㅼ뼱媛붿뼱??'),(4,40,8,_binary 'g\???聘\??\?\럝','洹?諛붽뎄?덈뒗 ?좊퉬???멸퀎濡??듯븯??臾몄씠?덈떟?덈떎.'),(5,41,8,_binary 'c혗#H쨲\烏~???,'洹멸납?먯꽌 ?ㅼ슫?대뒗 ???λ궃媛먯쓣 諛쒓껄?섍퀬 ?섎（ 醫낆씪 利먭굅?뚰뻽?댁슂.'),(6,42,8,_binary '?y꼳@s?\??LA','洹몄쓽 ?껋쓬?뚮━??留덈쾿???멸퀎瑜?諛앷쾶 鍮꾩텛?덈떟?덈떎.'),(1,43,9,_binary '&\生덩D\脆??t\?힓','?쏅궇 ?쏆쟻??源?ㅼ슫?대씪???꾩씠媛 ?닿퀬 ?덉뿀?댁슂. '),(2,44,9,_binary '?X匍켔??P?\勵L','?ㅼ슫?대뒗 留ㅼ씪 諛??좎갑?명삎怨??④퍡 轅덈굹?쇰줈 ?ы뻾???좊궗?댁슂. '),(3,45,9,_binary '\???F??똄e챇','?대뒓 ?? 留덈쾿??諛붽뎄?덈? 諛쒓껄?섍퀬 洹??덉뿉 ???ㅼ뼱媛붿뼱??'),(4,46,9,_binary 'kA쑀\?\侏O\?u;?,'諛붽뎄???덉뿉???ㅼ슫?대뒗 ?묒? ?붿젙??留뚮굹寃??섏뿀?댁슂.'),(5,47,9,_binary '?\?:;C\琇\???.\r','?붿젙???꾩??쇰줈 ?몄긽???뚮━瑜??ｋ뒗 踰뺤쓣 諛곗썱?듬땲??'),(6,48,9,_binary '\?\肄\"\?\?\??Z?','?ㅼ슫?대뒗 ?먯젏 ???몄긽???먰뿕?섍퀬 ?띠뼱 ?덉뼱??'),(1,55,12,_binary 'DI%gMG벖,Q???,'?쏅궇 ?쏆쟻??源?ㅼ슫?대씪???꾩씠媛 ?댁븯?댁슂.'),(2,56,12,_binary '럽m?DE쑇\?\?酩','?ㅼ슫?대뒗 ?밸퀎???명삎怨??④퍡 轅덈굹?쇰줈 ?좊굹??寃껋쓣 醫뗭븘?덉뼱??'),(3,57,12,_binary '?`??L=둏Z쩳??,'?대뒓 ?? ?ㅼ슫?대뒗 留덈쾿??諛붽뎄?덈? 諛쒓껄?섍퀬 洹??덉쑝濡????ㅼ뼱媛붿뼱??'),(4,58,12,_binary '?뼾%턅?\쩡???','諛붽뎄???띿뿉???ㅼ뼇???뚮━瑜??대뒗 ?λ궃媛먮뱾??湲곕떎由ш퀬 ?덉뿀?댁슂.'),(5,59,12,_binary 'd=풗\?㈌&鄕??','?ㅼ슫?대뒗 ?λ궃媛먭낵 移쒓뎄媛 ?섏뼱 ?섎（ 醫낆씪 ?껋쑝硫???섏뼱??'),(6,60,12,_binary '?'g???e┾샇???,'洹????댄썑濡??ㅼ슫?대뒗 留ㅼ씪 ?덈줈??紐⑦뿕??轅덇씀硫??먮옄?듬땲??');
    /*!40000 ALTER TABLE `story_page` ENABLE KEYS */;
    UNLOCK TABLES;
    
    --
    -- Table structure for table `video`
    --
    
    DROP TABLE IF EXISTS `video`;
    /*!40101 SET @saved_cs_client     = @@character_set_client */;
    /*!50503 SET character_set_client = utf8mb4 */;
    CREATE TABLE `video` (
      `running_time` int NOT NULL,
      `id` binary(16) NOT NULL,
      PRIMARY KEY (`id`),
      CONSTRAINT `FKo9n8yikoq3oaatmuoqeh7cyfq` FOREIGN KEY (`id`) REFERENCES `media` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    /*!40101 SET character_set_client = @saved_cs_client */;
    
    --
    -- Dumping data for table `video`
    --
    
    LOCK TABLES `video` WRITE;
    /*!40000 ALTER TABLE `video` DISABLE KEYS */;
    /*!40000 ALTER TABLE `video` ENABLE KEYS */;
    UNLOCK TABLES;
    /*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
    
    /*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
    /*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
    /*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
    /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
    /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
    /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
    /*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
    
    -- Dump completed on 2024-10-10 16:31:05
    
    ```
    

---

# 시연 시나리오

## 시연 순서에 따른 site 화면별, 실행별(클릭 위치 등) 상세 설명

- 최종 시연 시나리오
    
    약 5-6분
    
    **3가지 기능 보여줄 것.**
    
    - 동화 생성
    - 일정 등록
    - 마일스톤 체크 및 보고서 생성
    
    ---
    
    ### 로그인
    
    (발표자)
    
    주요 기능을 보여드리기에 앞서 먼저 로그인을 진행해보겠습니다.
    
    (시연자) 로그인 진행
    
    (발표자)
    
    로그인을 하게 되면 다음과 같이 나에게 등록된 아이들이 보이게 됩니다.
    
    저희는 로그인한 사용자에게 쓰기 권한이 있는 ‘아기다운’으로 접속해보겠습니다.
    
    (시연자) ‘아기다운’으로 접속
    
    ---
    
    ### 동화 생성
    
    (발표자)
    
    첫 번째 주요 기능인 동화를 생성해보겠습니다.
    
    (시연자와 함께)
    
    하단의 동화 탭에 들어가게 되면 생성한 동화 목록들을 볼 수 있습니다.
    
    저희는 기존에 작성된 일기를 가지고 새로운 동화를 생성해보겠습니다.
    
    상단의 동화 생성 버튼을 클릭합니다.
    
    생성할 동화의 제목을 입력하고,
    
    (시연자) 제목 입력창에 ‘아기다운의 첫 번째 10월’입력
    
    동화의 시작일을 캘린더에서 보고 선택해줍니다. 
    
    저희는 10월 1일부터 10월 6일까지 작성된 일기로 생성해 보겠습니다.
    
    (시연자) 시작일 옆의 캘린더 아이콘 클릭해서 ‘10월 1일’ 날짜 선택
    
    동화의 종료일도 캘린더에서 보고 선택해줍니다.
    
    (시연자) 종료일 옆의 캘린더 아이콘 클릭해서 ‘10월 6일’ 날짜 선택
    
    그리고 동화의 커버 이미지도 선택해보겠습니다.
    
    (시연자) 표지 선택에서 오른쪽으로 슬라이드 하며 마음에 드는 이미지 하나 선택
    
    하단의 동화 생성 버튼을 클릭하여 설정한 기간에 작성된 일기를 가지고 동화를 생성해보겠습니다.
    
    (동화 생성중 약 30초) 동화 생성 과정 설명
    
    동화가 생성되는 동안 백엔드에서 동화가 생성되는 과정을 짧게 설명드리겠습니다.
    
    - 입력한 데이터로 동화 생성 요청을 보내면 DB에서 요청 기간의 일기 데이터를 꺼내 미리 작성된 프롬프트에 주입합니다. 스프링 AI를 사용해서 GPT 4o에 동화 생성을 요청하게 되는데 이 때 생성된 동화 텍스트로 달리 3라는 AI 모델에게 어울리는 이미지 생성을 요청합니다. 원래 한 장의 이미지를 생성하려면 17초 가량이 걸리는데 저희는 다량의 이미지를 빠르게 생성하기 위해 비동기적으로 요청하였습니다. 이렇게 6장의 이미지를 한 장의 이미지를 생성하는 것과 같은 시간 만에 생성이 되도록 하였습니다.
    
    ‘아기다운의 첫번째 10월’이라는 동화가 완성되었습니다. 전체 화면으로 생성된 동화를 살펴볼까요?
    
    (시연자) 생성되면 전체 화면으로 동화 보여줌 → 한 페이지씩 읽을건데 같이 넘겨주시면 됩니당
    
    ---
    
    ### 일정 등록
    
    (발표자)
    
    두 번째 주요 기능인 일정을 등록해보겠습니다.
    
    (시연자와 함께)
    
    하단의 일정 탭에 들어가게 되면 등록된 일정을 볼 수 있습니다.
    
    저희는 음성으로 새로운 일정을 등록해보겠습니다.
    
    추가 버튼을 클릭한 뒤 음성으로 추가하기 버튼을 클릭합니다.
    
    제가 마이크로 말해도 등록될까요..?
    
    “내일 10시에 아동 병원 진료”
    
    (시연자) 정지 버튼을 클릭하고 등록된 일정 확인 (12일 토요일로 이동)
    
    이렇게 제가 말한 대로 내일 12일 토요일 10시에 아동 병원 진료 일정이 등록된 것을 볼 수 있습니다.
    
    ---
    
    ### 마일스톤 체크 및 보고서 생성
    
    (발표자)
    
    세 번째 주요 기능인 마일스톤 체크 및 보고서 생성을 해보겠습니다.
    
    (시연자와 함께)
    
    - 하단의 프로필 탭에 들어가게 되면 마일스톤 체크를 할 수 있습니다.
    - 보시는 것처럼 아이의 개월 수에 따라 해당 개월 수의 또래 아이들이 평m균적으로 할 수 있는 행동 발달 특성을 4가지 영역으로 나타냈습니다.
    - 이때 우리 아이가 했던 행동이나 발달 사항을 체크해볼 수 있습니다. 저희는 두번째와 세번째 특성을 체크해 볼까요? 그리고 저장을 하면 한 칸의 게이지가 차는 모습을 볼 수 있습니다.
    - 여기서 키와 몸무게도 입력하고 저장하면 최근 저장된 키/몸무게 데이터도 확인할 수 있습니다.
    - 현재 개월 수 뿐만 아니라 이전 개월 수의 행동 특성도 볼 수 있고, 마찬가지로 저장된 키와 몸무게 데이터를 확인할 수 있습니다.
    - 그리고 아직 오지 않은 이후 개월 수의 행동 특성까지 살펴볼 수 있습니다.
    - 그럼 이제 저장된 마일스톤과 키/몸무게 데이터를 가지고 성장 분석 보고서를 생성해볼까요? AI가 저장된 데이터로 우리 아이의 성장 분석 보고서를 생성하고 있습니다.
    - 보고서가 생성되었습니다. 보고서를 보면 또래 아이의 성장 그래프와 비교해서 우리 아이의 성장이 빠른 편인지 느린 편인지 알 수 있습니다.
    - 그리고 아래의 결과지에 마일스톤 체크한 것을 바탕으로 성장 발달 진행 사항도 확인할 수 있습니다.
    
    (시연자) - 위의 순서와 동일하게 진행
    
    - 프로필 탭으로 이동해서 마일스톤 체크하기 버튼 클릭
    - 스크롤 천천히 내리면서 해당 개월수의 행동 발달 특성 보여줌
    - 두번째 세번째 항목 선택 → 상단의 저장 버튼 클릭
    - 키/몸무게 입력 버튼 클릭 → “키-65  몸무게-7.4” 입력 → 키/몸무게 저장 버튼 클릭
    - 이전 개월 수로 넘어감 1개월까지
    - 다음 개월 수로 넘어감 8개월까지
    - 다시 4개월로 돌아와서 성장 분석 보고서 생성 버튼 클릭
    - (보고서 생성 완료)
    - 스크롤 내려서 결과지 보여줌
    
    ---
    
    이렇게 3가지 주요 기능 시연을 마치겠습니다. 다시 PPT로 넘어가서 세부 기능에 대해 소개해드리겠습니다.
    
    (시연자) PPT 장표 보여주세요..
