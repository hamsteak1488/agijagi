import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../styles/theme';

export const Container = styled.div<{}>(
  (props) => css`
    background-color: ${theme.color.primary[100]};
    height: 100%;
  `
);
