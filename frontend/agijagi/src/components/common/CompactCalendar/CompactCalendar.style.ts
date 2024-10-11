import styled from '@emotion/styled';

export const Container = styled.div`
  overflow: hidden;
  padding: 0.5rem;
  border-radius: 0.5rem;
  background-color: #fff;
`;

export const Swipe = styled.div`
  display: flex;
  flex-direction: row;

  > * {
    flex-shrink: 0;
  }
`;
