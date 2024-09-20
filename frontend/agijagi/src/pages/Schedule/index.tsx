import AppBar from '../../components/common/AppBar';

import Button from '../../components/common/Button';

import Typhography from '../../components/common/Typography';

import Schedule from '../../components/Schedule';

import useModal from '../../hooks/useModal';

import Edit from './Edit';

import * as s from './style';

const SchedulePage = () => {
  const modal = useModal();

  const handleClick = () => {
    modal.push({ children: <Edit /> });
  };

  return (
    <s.Container>
      <AppBar>
        <AppBar.Title>일정 관리</AppBar.Title>
      </AppBar>
      <s.Main>
        <s.ScheduleHeader>
          <Typhography size="lg" color="secondary" weight="bold">
            9월 4일 (수) 일정
          </Typhography>
          <Button size="sm">추가</Button>
        </s.ScheduleHeader>
        <s.ScheduleList>
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleClick}
          />
        </s.ScheduleList>
      </s.Main>
    </s.Container>
  );
};

export default SchedulePage;
