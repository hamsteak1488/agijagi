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

  /**
   * @todo
   * data랑 userStore 비교해서 isAuthor 업데이트 하기
   */

  return (
    <s.Container>
      <ArticleHeader
        title={data.title}
        articleId={data.postId}
        isAuthor={true}
      />
      <ArticleList.Item
        id={data.postId}
        images={data.mediaUrls}
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
        </CustomizedBorderContainer>
      </s.CommentList>
    </s.Container>
  );
};

export default ArticlePageFetch;
