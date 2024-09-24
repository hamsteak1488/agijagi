import { useRef, useState } from 'react';

import AppBar from '../../../components/common/AppBar';
import IconButton from '../../../components/common/IconButton';
import Schedule from '../../../components/Schedule';
import Textfield from '../../../components/common/Textfield';
import Button from '../../../components/common/Button';
import TimePicker from '../../../components/common/TimePicker';

import XMarkIcon from '@heroicons/react/16/solid/XMarkIcon';

import * as s from './style';

import { useNavigate } from 'react-router-dom';

const AddSchedulePage = () => {
  const navigate = useNavigate();

  const dateRef = useRef<Date>();

  const handleDateClick = (date: Date) => {
    dateRef.current = date;
  };

  const [value, setValue] = useState<string>('');

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
          <TimePicker />
        </s.TimerPickerWrapper>
        <s.Form>
          <Textfield
            inputValue={value}
            setInputValue={setValue}
            label="일정 이름"
            fullWidth
          />
          <Textfield
            inputValue={value}
            setInputValue={setValue}
            label="일정 내용"
            fullWidth
          />
          <s.ButtonContainer>
            <Button>추가</Button>
          </s.ButtonContainer>
        </s.Form>
      </s.Main>
    </s.Container>
  );
};

export default AddSchedulePage;
