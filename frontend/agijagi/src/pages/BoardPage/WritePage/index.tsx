import * as s from './style';

import { useNavigate } from 'react-router-dom';

import AppBar from '../../../components/common/AppBar';
import IconButton from '../../../components/common/IconButton';
import Button from '../../../components/common/Button';
import ImageUploader from '../../../components/common/ImageUploader';

import XMarkIcon from '@heroicons/react/16/solid/XMarkIcon';

const WritePage = () => {
  const navigate = useNavigate();

  return (
    <s.Container>
      <AppBar>
        <IconButton onClick={() => navigate(-1)}>
          <XMarkIcon />
        </IconButton>
        <AppBar.Title>글쓰기</AppBar.Title>
        <Button color="success" size="sm">
          작성
        </Button>
      </AppBar>
      <s.Main>
        <label htmlFor="title">제목</label>
        <s.Title type="text" id="title" />
        <label htmlFor="body">내용</label>
        <s.Body id="body" />
        <ImageUploader maxFileCount={5} />
      </s.Main>
    </s.Container>
  );
};

export default WritePage;
