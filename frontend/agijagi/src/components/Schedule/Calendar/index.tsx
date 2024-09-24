import { useState } from 'react';

import * as s from './style';

import CompactCalendar from '../../common/CompactCalendar';
import Typhography from '../../common/Typography';

interface CalendarProps {
  onClick: (date: Date) => void;
}

const Calendar = ({ onClick }: CalendarProps) => {
  const [calendar, setCalendar] = useState<Date>(new Date());

  return (
    <s.Container>
      <Typhography size="lg" color="secondary" weight="bold">
        {calendar.getFullYear()}년 {calendar.getMonth() + 1}월
      </Typhography>
      <CompactCalendar
        date={new Date()}
        onChange={(date) => setCalendar(date)}
        onClick={onClick}
      />
    </s.Container>
  );
};

export default Calendar;
