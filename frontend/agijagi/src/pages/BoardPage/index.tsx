import { useState } from 'react';

import * as s from './style';
import theme from '../../styles/theme';

import AppBar from '../../components/common/AppBar';
import Tab from '../../components/common/Tab';
import ArticleList from '../../components/Board/ArticleList';
import CustomizedBorderContainer from '../../components/common/CustomizedBorderContainer';
import Button from '../../components/common/Button';

import useModal from '../../hooks/useModal';

import ArticlePage from './ArticlePage';

import Waves from '../../assets/images/record/waves.svg';

import WritePage from './WritePage';

const BoardPage = () => {
  const [board, setBoard] = useState<string>('');

  const modal = useModal();

  const handleWriteClick = () => {
    modal.push({ children: <WritePage /> });
  };

  return (
    <s.Container>
      <AppBar>
        <div></div>
        <AppBar.Title>게시판</AppBar.Title>
        <Button color="success" size="sm" onClick={handleWriteClick}>
          글쓰기
        </Button>
      </AppBar>
      <s.Main>
        <Tab
          selected="b1"
          size="md"
          onChange={(value) => {
            setBoard(value);
          }}
        >
          <Tab.Item value="b1">1번 게시판</Tab.Item>
          <Tab.Item value="b2">2번 게시판</Tab.Item>
          <Tab.Item value="b3">3번 게시판</Tab.Item>
        </Tab>
        <s.ArticleList>
          <ArticleList key={board}>
            {new Array(20).fill(0).map((value, index) => (
              <s.ArticleListItem key={index}>
                <CustomizedBorderContainer
                  backgroundColor={theme.color.primary[index % 2 ? 100 : 300]}
                  border={Waves}
                  borderHeight="24px"
                >
                  <ArticleList.Item
                    id={index}
                    image="asd"
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
      </s.Main>
    </s.Container>
  );
};

export default BoardPage;
