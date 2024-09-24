import { ReactElement } from 'react';

import * as s from './IconButton.style';

interface IconButtonProps {
  children: ReactElement;
}

const IconButton = ({ children }: IconButtonProps) => {
  return <s.Container>{children}</s.Container>;
};

export default IconButton;
