import { Suspense } from 'react';

import RecordList from '../../components/Record/RecordList';
import AppBar from '../../components/common/AppBar';

import useDialog from '../../hooks/useDialog';
import useRecord from '../../hooks/useRecord';

import * as s from './style';

import RecordMenu from './RecordMenu';

import { ErrorBoundary } from 'react-error-boundary';
import ErrorBoundaryFallback from '../../components/common/ErrorBoundaryFallback';
import SuspenseFallback from '../../components/common/SuspenseFallback';

const RecordPage = () => {
  const { confirm } = useDialog();

  const record = useRecord();

  const 대변 = record.findMenuByName('대변');
  const 소변 = record.findMenuByName('소변');
  const 약 = record.findMenuByName('약');

  const handleListClick = async () => {
    if (!confirm('선택한 기록을 삭제할까요?')) {
      return;
    }
  };

  return (
    <s.Container>
      <AppBar>
        <AppBar.Title>기록</AppBar.Title>
      </AppBar>
      <s.Main>
        <s.RecordMenu>
          <ErrorBoundary
            fallbackRender={(props) => (
              <ErrorBoundaryFallback height="13rem" {...props}>
                기록 메뉴를 불러오지 못했어요.
              </ErrorBoundaryFallback>
            )}
          >
            <Suspense fallback={<SuspenseFallback height="13rem" />}>
              <RecordMenu />
            </Suspense>
          </ErrorBoundary>
        </s.RecordMenu>
        <s.RecordList>
          <RecordList>
            <RecordList.Group title="6월 29일 (수요일)">
              <RecordList.Item
                icon={대변!.icon}
                color={대변!.color}
                title="대변"
                description="많이"
                date="14:00"
                onClick={handleListClick}
              />
              <RecordList.Item
                icon={약!.icon}
                color={약!.color}
                title="약"
                description="감기약"
                date="14:00"
              />
              <RecordList.Item
                icon={소변!.icon}
                color={소변!.color}
                title="소변"
                description="많이"
                date="14:00"
              />
            </RecordList.Group>
          </RecordList>
        </s.RecordList>
      </s.Main>
    </s.Container>
  );
};

export default RecordPage;
