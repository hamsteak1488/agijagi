FROM eclipse-temurin:17
LABEL authors="password926"

WORKDIR /app

COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY gradlew.bat /app/gradlew.bat
COPY settings.gradle /app/settings.gradle
COPY build.gradle /app/build.gradle

RUN chmod +x ./gradlew
RUN ./gradlew dependencies

COPY src /app/src
RUN ./gradlew clean build
RUN cp build/libs/agijagi-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]