import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../styles/theme';
import Button from '../../components/common/Button';

export const Container = styled.div<{}>(
  (props) => css`
    background-color: ${theme.color.primary[100]};
    height: 100%;
  `
);
