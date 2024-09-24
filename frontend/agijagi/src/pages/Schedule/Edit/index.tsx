import AppBar from '../../../components/common/AppBar';

import XMarkIcon from '@heroicons/react/16/solid/XMarkIcon';

import * as s from './style';
import IconButton from '../../../components/common/IconButton';

const Edit = () => {
  return (
    <s.Container>
      <AppBar>
        <IconButton>
          <XMarkIcon />
        </IconButton>
        <AppBar.Title>일정 수정</AppBar.Title>
      </AppBar>
    </s.Container>
  );
};

export default Edit;
