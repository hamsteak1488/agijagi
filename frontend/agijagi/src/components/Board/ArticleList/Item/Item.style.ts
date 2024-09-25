import { css } from '@emotion/react';
import styled from '@emotion/styled';

import theme from '../../../../styles/theme';

export const ImageList = styled.div`
  display: flex;
  width: 100%;
  aspect-ratio: 1 / 1;
  overflow-x: scroll;
  scroll-snap-type: x mandatory;

  > div {
    flex-shrink: 0;
    scroll-snap-align: start;
  }
`;

export const Container = styled.div<{ transparent: boolean }>(
  (props) => css`
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    padding: 1rem;
    border-bottom: 0.0625rem solid ${theme.color.primary[100]};
    transition: all 0.2s ease;

    :last-of-type {
      border-bottom: 0;
    }

    ${!props.transparent &&
    css`
      background-color: ${theme.color.primary[50]};

      :active {
        background-color: ${theme.color.primary[100]};
      }
    `}
  `
);

export const Detail = styled.div`
  display: flex;
  justify-content: space-between;
`;
