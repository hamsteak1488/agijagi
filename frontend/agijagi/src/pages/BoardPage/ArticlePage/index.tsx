import ArticleList from '../../../components/Board/ArticleList';
import Comment from '../../../components/Board/Comment';
import CustomizedBorderContainer from '../../../components/common/CustomizedBorderContainer';

import theme from '../../../styles/theme';

import Waves from '../../../assets/images/activityRecord/waves.svg';

import * as s from './style';
import Button from '../../../components/common/Button';

interface ArticlePageProps {
  articleId: number;
}

const ArticlePage = ({ articleId }: ArticlePageProps) => {
  return (
    <s.Container>
      <s.Header>
        <Button color="secondary" size="sm">
          수정
        </Button>
        <Button color="danger" size="sm">
          삭제
        </Button>
      </s.Header>
      <ArticleList.Item
        id={articleId}
        image="asd"
        description={`${articleId + 1}번 게시글입니다 #우리아기 #용진`}
        writer={'용진맘'}
        createdAt={
          new Date(new Date().getTime() - 1000 * 3600 * Math.random() * 1000)
        }
      />
      <Comment.Write />
      <s.CommentList>
        <CustomizedBorderContainer
          backgroundColor={theme.color.primary[50]}
          border={Waves}
          borderHeight="24px"
        >
          <Comment.List>
            <Comment.List.Item
              body="안녕하세요 용진맘"
              writer="융진맘"
              createdAt={new Date()}
            />
            <Comment.List.Item
              body="안녕하세요 용진맘"
              writer="융진맘"
              createdAt={new Date()}
            />
            <Comment.List.Item
              body="안녕하세요 용진맘"
              writer="융진맘"
              createdAt={new Date()}
            />
            <Comment.List.Item
              body="안녕하세요 용진맘"
              writer="융진맘"
              createdAt={new Date()}
            />
          </Comment.List>
        </CustomizedBorderContainer>
      </s.CommentList>
    </s.Container>
  );
};

export default ArticlePage;
