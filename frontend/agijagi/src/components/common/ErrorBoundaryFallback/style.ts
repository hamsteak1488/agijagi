import styled from '@emotion/styled';

import theme from '../../../styles/theme';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  box-sizing: border-box;
  padding: 1rem;
  width: calc(100% - 1rem);
  border-radius: 0.75rem;
  background-color: ${theme.color.danger.A100};
  text-align: center;
`;
