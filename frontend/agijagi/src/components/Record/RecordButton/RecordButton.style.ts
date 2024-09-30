import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

import theme from '../../../styles/theme';

const twinkle = keyframes`
  from {
    filter: brightness(95%);
  }

  to {
    filter: brightness(110%);
  }
`;

export const RecordButton = styled.button<{ color: string; active: boolean }>(
  (props) => css`
    width: 6.5rem;
    height: 6.5rem;
    border: 0;
    border-radius: 2.5rem;
    background-color: ${props.color};
    cursor: pointer;

    ${props.active &&
    css`
      animation: ${twinkle} 1.5s alternate infinite;
    `}
  `
);

export const Icon = styled.img`
  width: 2rem;
  height: 2rem;
`;
