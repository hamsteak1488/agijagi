import styled from '@emotion/styled';

import theme from '../../../styles/theme';

import Waves from '../../../assets/images/activityRecord/waves.svg';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

export const Border = styled.div`
  height: 24px;
  background-color: ${theme.color.primary[50]};
  mask-image: url(${Waves});
`;

export const Body = styled.div`
  flex: 1 1 auto;
  padding: 0.625rem;
  box-sizing: border-box;
  background-color: ${theme.color.primary[50]};
`;
