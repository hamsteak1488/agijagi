import { ReactNode } from 'react';

import * as s from './Button.style';

import { ButtonStyles } from './Button.types';

interface ButtonProps extends ButtonStyles {
  children: ReactNode;
}

const Button = ({ children, ...rest }: ButtonProps) => {
  return <s.Button {...rest}>{children}</s.Button>;
};

export default Button;
