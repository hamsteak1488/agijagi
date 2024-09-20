import styled from '@emotion/styled';
import theme from '../../../styles/theme';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  padding: 0.75rem;
  border-radius: 0.75rem;
  background-color: ${theme.color.primary[700]};
  transition: all 0.2s ease;
  user-select: none;
  cursor: pointer;

  :active {
    transform: translateY(0.25rem);
    filter: brightness(90%);
  }
`;
