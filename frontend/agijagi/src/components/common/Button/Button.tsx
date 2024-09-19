import { ReactNode } from 'react';

import * as s from './Button.style';

import { ButtonStyles } from './Button.types';

interface ButtonProps
  extends ButtonStyles,
    Omit<React.ComponentProps<'button'>, 'color'> {
  children: ReactNode;
  fullWidth?: boolean;
}

const Button = ({
  children,
  fullWidth = false,
  color,
  ...rest
}: ButtonProps) => {
  return (
    <s.Button fullWidth={fullWidth} backgroundColor={color} {...rest}>
      {children}
    </s.Button>
  );
};

export default Button;
