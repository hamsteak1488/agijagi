import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const AppBar = styled.div<{ color: string; height: string }>(
  (props) => css`
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;
    z-index: 10;
    height: ${props.height};
    padding: 0 0.75rem;
    background-color: ${props.color};
  `
);

export const Cloud = styled.div`
  transform: translateY(-12px);

  svg {
    width: 600px;
    height: 50%;
    min-width: 100%;
  }
`;

export const Container = styled.div`
  position: sticky;
  top: 0;
  overflow-x: hidden;
`;
