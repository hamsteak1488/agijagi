import AppBar from '../../../components/common/AppBar';
import IconButton from '../../../components/common/IconButton';

import XMarkIcon from '@heroicons/react/16/solid/XMarkIcon';

import * as s from './style';

import { useNavigate } from 'react-router-dom';
import Schedule from '../../../components/Schedule';

const EditSchedulePage = () => {
  const navigate = useNavigate();

  const handleDateClick = () => {};

  return (
    <s.Container>
      <AppBar>
        <div></div>
        <IconButton onClick={() => navigate(-1)}>
          <XMarkIcon />
        </IconButton>
        <AppBar.Title>일정 수정</AppBar.Title>
      </AppBar>
      <s.Main>
        <Schedule.Calendar onClick={handleDateClick} />
      </s.Main>
    </s.Container>
  );
};

export default EditSchedulePage;
