import { useState } from 'react';

import * as s from './style';

import ClockIcon from '@heroicons/react/24/solid/ClockIcon';

import Typhography from '../Typography';
import TimePickerModal from './Modal';

import useModal from '../../../hooks/useModal';

const TimePicker = () => {
  const modal = useModal();

  const [start, setStart] = useState<string>('00:00');
  const [end, setEnd] = useState<string>('00:00');

  const handleTimeSelect = (start: string, end: string) => {
    setStart(start);
    setEnd(end);
  };

  const handleClick = () => {
    modal.push({
      children: (
        <TimePickerModal start={start} end={end} onSelect={handleTimeSelect} />
      ),
    });
  };

  return (
    <s.Container onClick={handleClick}>
      <s.Icon>
        <ClockIcon />
      </s.Icon>
      <Typhography size="sm" weight="bold">
        {start} ~ {end}
      </Typhography>
    </s.Container>
  );
};

export default TimePicker;
