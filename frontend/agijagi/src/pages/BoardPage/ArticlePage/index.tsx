import ArticleList from '../../../components/Board/ArticleList';
import Comment from '../../../components/Board/Comment';
import CustomizedBorderContainer from '../../../components/common/CustomizedBorderContainer';
import ArticleHeader from '../../../components/Board/ArticleHeader';

import theme from '../../../styles/theme';
import * as s from './style';

import Waves from '../../../assets/images/record/waves.svg';

interface ArticlePageProps {
  articleId: number;
}

const ArticlePage = ({ articleId }: ArticlePageProps) => {
  return (
    <s.Container>
      <ArticleHeader title="용진맘의 일기" articleId={1} isAuthor={true} />
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
          backgroundColor={theme.color.primary[100]}
          border={Waves}
          borderHeight="24px"
        >
          <Comment.List commentCount={0}>
            <Comment.Emtpy />
          </Comment.List>
          {/* <Comment.List commentCount={10}>
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
          </Comment.List> */}
        </CustomizedBorderContainer>
      </s.CommentList>
    </s.Container>
  );
};

export default ArticlePage;
