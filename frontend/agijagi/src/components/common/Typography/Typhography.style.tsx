import { css } from '@emotion/react';

import styled from '@emotion/styled';

import theme from '../../../styles/theme';

import { ColorShade, FontWeight, Palette } from '../../../types/theme';

import { TyphographySize } from './Typhography.types';

const sizeStyles: Record<TyphographySize, string> = {
  '6xs': `
    size: 0.125rem;
  `,
  '5xs': `
    size: 0.25rem;`,
  '4xs': `
    size: 0.375rem;`,
  '3xs': `
    size: 0.5rem;`,
  '2xs': `
    size: 0.625rem;`,
  xs: `
    size: 0.75rem`,
  sm: `
  size: 0.875rem`,
  md: `1rem`,
  lg: `1.125rem`,
  xl: `1.250rem`,
  '2xl': `1.375rem`,
  '3xl': `1.5rem`,
  '4xl': `1.625rem`,
  '5xl': `1.75rem`,
  '6xl': `1.875rem`,
  '7xl': `2rem`,
};

const weightStyles: Record<FontWeight, string> = {
  regular: `
    font-weight : 500 `,
  bold: `
    font-weight : 700
    `,
  extraBold: `
    font-weight : 800
  `,
};

export const Typhography = styled.div<{
  color: Palette;
  size: TyphographySize;
  weight: FontWeight;
  shade: ColorShade;
}>(
  (props) => css`
    ${sizeStyles[props.size]}
    font-weight: ${props.weight};
    color: ${theme.color[props.color][props.shade]};
  `
);
