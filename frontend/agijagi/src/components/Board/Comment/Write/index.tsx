import { useState } from 'react';

import * as s from './style';

import Button from '../../../common/Button';
import Textfield from '../../../common/Textfield';

export const Write = () => {
  const [value, setValue] = useState<string>('');
  return (
    <s.Container>
      <s.Text>
        <Textfield
          label="댓글 내용"
          fullWidth
          inputValue={value}
          setInputValue={setValue}
        />
      </s.Text>
      <Button>작성</Button>
    </s.Container>
  );
};

export default Write;
