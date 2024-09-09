import styeld from '@emotion/styled';

import theme from '../../../styles/theme';

import { Palette } from '../../../types/theme';

import { ButtonSize } from './Button.types';

const sizeStyle: Record<ButtonSize, string> = {
  sm: `
      padding: 0.625rem;
      font-size: 1rem;
  `,
  md: `
      padding: 0.75rem;
      font-size: 1rem;
    `,
  lg: `
      padding: 0.875rem;
      font-size: 1.25rem;
    `,
};

export const Button = styeld.button<{
  color: Palette;
  size: ButtonSize;
}>(
  (props) => `
  ${sizeStyle[props.size]}
  border: 0.0625rem solid ${theme.color[props.color][800]};
  background-color: ${theme.color[props.color][200]};
  cursor: pointer;
`
);
