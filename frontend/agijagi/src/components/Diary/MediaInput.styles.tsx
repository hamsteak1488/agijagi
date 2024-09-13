import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../styles/theme';

export const Container = styled.div<{}>((props) => css``);

export const InputBox = styled.div<{}>(
  (props) => css`
    width: 250px;
    height: 250px;
    background-color: ${theme.color.greyScale[500]};
  `
);
