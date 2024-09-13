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
  overflow: hidden;
  margin-top: -12px;

  svg {
    width: 600px;
    min-width: 100%;
  }
`;

export const Container = styled.header`
  position: sticky;
  top: 0;
  overflow-x: hidden;
`;
