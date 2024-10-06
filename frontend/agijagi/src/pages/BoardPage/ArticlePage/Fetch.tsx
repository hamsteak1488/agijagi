import ArticleList from '../../../components/Board/ArticleList';
import Comment from '../../../components/Board/Comment';
import CustomizedBorderContainer from '../../../components/common/CustomizedBorderContainer';
import ArticleHeader from '../../../components/Board/ArticleHeader';

import theme from '../../../styles/theme';
import * as s from './style';

import Waves from '../../../assets/images/record/waves.svg';

import useGetArticle from '../../../hooks/api/useGetArticle';

interface ArticlePageProps {
  articleId: number;
}

const ArticlePageFetch = ({ articleId }: ArticlePageProps) => {
  const { data } = useGetArticle(articleId);

  return (
    <s.Container>
      <ArticleHeader
        title={data.title}
        articleId={data.postId}
        isAuthor={true}
      />
      <ArticleList.Item
        id={data.postId}
        image="asd"
        description={data.content}
        writer={data.writerNickname}
        createdAt={data.createdAt}
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

export default ArticlePageFetch;
