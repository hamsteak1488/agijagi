import * as s from './style';

import AppBar from '../../components/common/AppBar';
import Button from '../../components/common/Button';
import RecentArticleList from '../../components/Board/RecentArticleList';

import useModal from '../../hooks/useModal';

import WritePage from './WritePage';
import { Suspense } from 'react';
import SuspenseFallback from '../../components/common/SuspenseFallback';
import { ErrorBoundary } from 'react-error-boundary';
import ErrorBoundaryFallback from '../../components/common/ErrorBoundaryFallback';

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
          <ErrorBoundary
            fallbackRender={(props) => (
              <ErrorBoundaryFallback height="20rem" padding="1rem" {...props}>
                게시글을 불러오지 못했어요.
              </ErrorBoundaryFallback>
            )}
          >
            <Suspense
              fallback={<SuspenseFallback height="20rem" padding="1rem" />}
            >
              <RecentArticleList />
            </Suspense>
          </ErrorBoundary>
        </s.ArticleList>
      </main>
    </div>
  );
};

export default BoardPage;
