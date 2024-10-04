import theme from '../../styles/theme';
import * as s from './style';

import ArticleList from '../../components/Board/ArticleList';
import AppBar from '../../components/common/AppBar';
import Button from '../../components/common/Button';
import CustomizedBorderContainer from '../../components/common/CustomizedBorderContainer';

import useModal from '../../hooks/useModal';

import ArticlePage from './ArticlePage';

import Waves from '../../assets/images/record/waves.svg';

import WritePage from './WritePage';

const BoardPage = () => {
  const modal = useModal();

  const handleWriteClick = () => {
    modal.push({ children: <WritePage /> });
  };

  return (
    <div>
      <s.AppBar>
        <AppBar>
          <div></div>
          <AppBar.Title>게시판</AppBar.Title>
          <Button color="success" size="sm" onClick={handleWriteClick}>
            글쓰기
          </Button>
        </AppBar>
      </s.AppBar>
      <main>
        <s.ArticleList>
          <ArticleList>
            {new Array(20).fill(0).map((_, index) => (
              <s.ArticleListItem key={index}>
                <CustomizedBorderContainer
                  backgroundColor={theme.color.primary[index % 2 ? 100 : 300]}
                  border={Waves}
                  borderHeight="24px"
                >
                  <ArticleList.Item
                    id={index}
                    image="asd"
                    title="용진맘의 일기"
                    description={`${index + 1}번 게시글입니다 #우리아기 #용진`}
                    writer={'용진맘'}
                    createdAt={
                      new Date(
                        new Date().getTime() -
                          1000 * 3600 * Math.random() * 1000
                      )
                    }
                    onClick={() =>
                      modal.push({
                        children: <ArticlePage articleId={index} />,
                      })
                    }
                  />
                </CustomizedBorderContainer>
              </s.ArticleListItem>
            ))}
          </ArticleList>
        </s.ArticleList>
      </main>
    </div>
  );
};

export default BoardPage;
