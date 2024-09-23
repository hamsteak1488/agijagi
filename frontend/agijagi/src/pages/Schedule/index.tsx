import { useState } from 'react';
import AppBar from '../../components/common/AppBar';
import Button from '../../components/common/Button';
import CompactCalendar from '../../components/common/CompactCalendar';
import Typhography from '../../components/common/Typography';
import Schedule from '../../components/Schedule';

import useModal from '../../hooks/useModal';

import Edit from './Edit';

import * as s from './style';

const SchedulePage = () => {
  const [calendar, setCalendar] = useState<Date>(new Date());
  const [date, setDate] = useState<Date>(new Date());

  const modal = useModal();

  const days = ['일', '월', '화', '수', '목', '금', '토', '일'];

  const handleClick = () => {
    modal.push({ children: <Edit /> });
  };

  const handleCalendarChange = (date: Date) => {
    setCalendar(date);
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
        <s.Calendar>
          <Typhography size="lg" color="secondary" weight="bold">
            {calendar.getFullYear()}년 {calendar.getMonth() + 1}월
          </Typhography>
          <CompactCalendar
            date={new Date()}
            onChange={handleCalendarChange}
            onClick={handleDateClick}
          />
        </s.Calendar>
        <s.ScheduleHeader>
          <Typhography size="lg" color="secondary" weight="bold">
            {date.getMonth() + 1}월 {date.getDate()}일 ({days[date.getDay()]})
            일정
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
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleClick}
          />
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleClick}
          />
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleClick}
          />
          <Schedule.Card
            title="병원 가기"
            description="용진의원"
            time="08:00 ~ 08:30"
            onClick={handleClick}
          />
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
