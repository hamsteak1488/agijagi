import { useState } from 'react';

import * as s from './style';

import Button from '../../../common/Button';
import Textfield from '../../../common/Textfield';

export const Write = () => {
  const [text, setText] = useState<string>('');
  return (
    <s.Container>
      <s.Text>
        <Textfield
          label="댓글 내용"
          fullWidth
          inputValue={text}
          setInputValue={setText}
        />
      </s.Text>
      <Button disabled={text === ''}>작성</Button>
    </s.Container>
  );
};

export default Write;
