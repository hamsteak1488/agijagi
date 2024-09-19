import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Item = styled.div`
  display: flex;
  align-items: center;
  gap: 0.625rem;
  padding: 0.5rem;
  border-radius: 0.5rem;
  background-color: inherit;
  transition: all 0.2s ease;
  user-select: none;

  :active {
    background-color: rgba(0, 0, 0, 0.066);
  }
`;

export const Icon = styled.div<{ bacgkroundColor: string }>(
  (props) => css`
    width: 2.25rem;
    height: 2.25rem;
    border-radius: 50%;
    background-color: ${props.bacgkroundColor};
  `
);

export const Text = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1 1 auto;
`;
