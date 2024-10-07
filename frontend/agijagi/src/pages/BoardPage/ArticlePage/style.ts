import styled from '@emotion/styled';

import theme from '../../../styles/theme';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100vw;
  height: 100vh;
  background-color: ${theme.color.primary[300]};
`;

export const CommentList = styled.div`
  flex: 1 1 auto;
  overflow-y: scroll;
`;
