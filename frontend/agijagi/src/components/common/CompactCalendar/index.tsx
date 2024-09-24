import dayjs from 'dayjs';

import { useEffect, useRef } from 'react';

import useSwipe from '../../../hooks/useSwpie';

import * as s from './CompactCalendar.style';

import Week from './Week';

interface CompactCalendarProps {
  date: Date;
  onChange: (date: Date) => void;
  onClick: (date: Date) => void;
}

const CompactCalendar = ({ date, onChange, onClick }: CompactCalendarProps) => {
  const swipeRef = useRef<HTMLDivElement>(null);

  const swipe = useSwipe<HTMLDivElement>(swipeRef);

  const week = dayjs(date).add(swipe.index, 'week');
  const lastWeek = dayjs(week).add(-1, 'week');
  const nextWeek = dayjs(week).add(1, 'week');

  useEffect(() => {
    onChange(dayjs(date).add(swipe.index, 'week').startOf('week').toDate());
  }, [swipe.index]);

  return (
    <s.Container>
      <s.Swipe ref={swipeRef}>
        <Week date={lastWeek.toDate()} onClick={onClick} />
        <Week date={week.toDate()} onClick={onClick} />
        <Week date={nextWeek.toDate()} onClick={onClick} />
      </s.Swipe>
    </s.Container>
  );
};

export default CompactCalendar;
