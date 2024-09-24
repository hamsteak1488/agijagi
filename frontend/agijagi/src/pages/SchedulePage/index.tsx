import { useState } from 'react';
import AppBar from '../../components/common/AppBar';
import Button from '../../components/common/Button';
import Typhography from '../../components/common/Typography';
import Schedule from '../../components/Schedule';

import useModal from '../../hooks/useModal';
import AddSchedulePage from './AddSchedulePage';

import EditSchedulePage from './EditSchedulePage';

import * as s from './style';

const SchedulePage = () => {
  const [date, setDate] = useState<Date>(new Date());

  const modal = useModal();

  const days = ['일', '월', '화', '수', '목', '금', '토', '일'];

  const handleScheduleClick = () => {
    modal.push({ children: <EditSchedulePage /> });
  };

  const handleAddClick = () => {
    modal.push({ children: <AddSchedulePage /> });
  };

  const handleDateClick = (date: Date) => {
    setDate(date);
  };

  return (
    <s.Container>
      <AppBar>
        <AppBar.Title>일정 관리</AppBar.Title>
      </AppBar>
      <s.Main>
        <div>
          <Schedule.Calendar onClick={handleDateClick} />
        </div>
        <s.ScheduleHeader>
          <Typhography size="lg" color="secondary" weight="bold">
            {date.getMonth() + 1}월 {date.getDate()}일 ({days[date.getDay()]})
            일정
          </Typhography>
          <Button size="sm" onClick={handleAddClick}>
            추가
          </Button>
        </s.ScheduleHeader>
        <s.ScheduleList>
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleScheduleClick}
          />
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleScheduleClick}
          />
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleScheduleClick}
          />
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleScheduleClick}
          />
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleScheduleClick}
          />
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleScheduleClick}
          />
        </s.ScheduleList>
      </s.Main>
    </s.Container>
  );
};

export default SchedulePage;
