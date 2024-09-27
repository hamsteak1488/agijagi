import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const RecordButton = styled.button<{ color: string }>(
  (props) => css`
    width: 6.5rem;
    height: 6.5rem;
    border: 0;
    border-radius: 2.5rem;
    background-color: ${props.color};
    cursor: pointer;
  `
);

export const Icon = styled.img`
  width: 2rem;
  height: 2rem;
`;
