import { css } from '@emotion/react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';

import Typhography from '../../components/common/Typography';
import { MyFamily } from '../../components/Main/MyFamily/MyFamily';
import { MyProfile } from '../../components/Main/MyProfile/MyProfile';

export const TitleContainer = styled.div`
  margin: 2rem 1rem 1rem 1rem;
`;

export const Container = styled.div`
  width: 100%;
  height: 100%;
  background-color: ${theme.color.primary[50]};
  display: flex;
  flex-direction: column;
`;

export const Main = () => {
  return (
    <Container>
      <TitleContainer>
        <Typhography size="6xl" weight="bold">
          내 패밀리
        </Typhography>
      </TitleContainer>
      <MyFamily />

      <TitleContainer>
        <Typhography size="6xl" weight="bold">
          내 프로필
        </Typhography>
      </TitleContainer>
      <MyProfile />
    </Container>
  );
};
