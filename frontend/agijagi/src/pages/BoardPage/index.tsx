import { useState } from 'react';

import * as s from './style';

import AppBar from '../../components/common/AppBar';
import Tab from '../../components/common/Tab';
import ArticleList from '../../components/Board/ArticleList';
import CustomizedBorderContainer from '../../components/common/CustomizedBorderContainer';

import useModal from '../../hooks/useModal';

import ArticlePage from './ArticlePage';

import theme from '../../styles/theme';

import Waves from '../../assets/images/activityRecord/waves.svg';

const BoardPage = () => {
  const [board, setBoard] = useState<string>('');

  const modal = useModal();

  return (
    <s.Container>
      <AppBar>
        <AppBar.Title>게시판</AppBar.Title>
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
                  backgroundColor={theme.color.primary[index % 2 ? 50 : 200]}
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
