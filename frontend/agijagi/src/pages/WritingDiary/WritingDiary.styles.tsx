import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../styles/theme';
import Button from '../../components/common/Button';

export const Container = styled.div<{}>(
  (props) => css`
    background-color: ${theme.color.primary[100]};
    width: 100%;
    height: 100%;
    overflow-y: auto;
  `
);

export const DiaryTextArea = styled.textarea(
  () => css`
    width: 80%;
    height: auto;
    min-height: 220px;
    overflow-y: auto;
    border-radius: 0.5rem;
    background-color: ${theme.color.primary[50]};
    border: none;
    box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px,
      rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;
    margin: 1rem;
    padding: 1rem;
  `
);
