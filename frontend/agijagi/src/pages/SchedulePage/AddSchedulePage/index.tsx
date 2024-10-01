import { FormEvent, useRef, useState } from 'react';

import AppBar from '../../../components/common/AppBar';
import IconButton from '../../../components/common/IconButton';
import Schedule from '../../../components/Schedule';
import Textfield from '../../../components/common/Textfield';
import Button from '../../../components/common/Button';
import TimePicker from '../../../components/common/TimePicker';

import XMarkIcon from '@heroicons/react/16/solid/XMarkIcon';

import * as s from './style';

import { useNavigate } from 'react-router-dom';

import useAddSchedule from '../../../hooks/api/useAddSchedule';

import useChildStore from '../../../stores/useChlidStore';

import dayjs from 'dayjs';
import toISOString from '../../../utils/toISOString';

const AddSchedulePage = () => {
  const navigate = useNavigate();

  const { childId } = useChildStore();

  const dateRef = useRef<Date>(new Date());
  const timeRef = useRef<{ start: string; end: string }>({
    start: '00:00',
    end: '00:00',
  });

  const [title, setTitle] = useState<string>('');
  const [description, setDescription] = useState<string>('');

  const { mutate } = useAddSchedule();

  const isDisabled = title === '' || description === '';

  const handleDateClick = (date: Date) => {
    dateRef.current = date;
  };

  const handleTimeChange = (start: string, end: string) => {
    timeRef.current = { start, end };
  };

  const handleSubmit = (e: FormEvent) => {
    const date = dayjs(dateRef.current).format('YYYY-MM-DD');

    const start = `${date} ${timeRef.current.start}`,
      end = `${date} ${timeRef.current.end}`;

    mutate({
      childId,
      data: {
        title,
        description,
        startDateTime: toISOString(start),
        endDateTime: toISOString(end),
      },
    });

    e.preventDefault();
  };

  return (
    <s.Container>
      <AppBar>
        <div></div>
        <IconButton onClick={() => navigate(-1)}>
          <XMarkIcon />
        </IconButton>
        <AppBar.Title>일정 추가</AppBar.Title>
      </AppBar>
      <s.Main>
        <Schedule.Calendar onClick={handleDateClick} />
        <s.TimerPickerWrapper>
          <TimePicker
            start={timeRef.current.start}
            end={timeRef.current.end}
            onChange={handleTimeChange}
          />
        </s.TimerPickerWrapper>
        <s.Form onSubmit={handleSubmit}>
          <Textfield
            inputValue={title}
            setInputValue={setTitle}
            label="일정 이름"
            name="name"
            fullWidth
          />
          <Textfield
            inputValue={description}
            setInputValue={setDescription}
            label="일정 내용"
            name="description"
            fullWidth
          />
          <s.ButtonContainer>
            <Button disabled={isDisabled}>추가</Button>
          </s.ButtonContainer>
        </s.Form>
      </s.Main>
    </s.Container>
  );
};

export default AddSchedulePage;
