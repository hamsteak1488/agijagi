import styled from '@emotion/styled';

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

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 1rem;
  transition: all 0.2s ease;

  :last-of-type {
    border-bottom: 0;
  }
`;

export const Detail = styled.div`
  display: flex;
  justify-content: space-between;
`;
