import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../styles/theme';
import { BabyProfileCard } from '../../components/BabyMain/BabyProfileCard/BabyProfileCard';
import { BabyReportCard } from '../../components/BabyMain/BabyReportCard/BabyReportCard';
import Typhography from '../../components/common/Typography';

export const TitleContainer = styled.div`
  margin: 1rem;
`;
export const BabyProfile = () => {
  return (
    <>
      <TitleContainer>
        <Typhography size="5xl" weight="bold">
          패밀리 프로필
        </Typhography>
      </TitleContainer>
      <BabyProfileCard></BabyProfileCard>
      <BabyReportCard></BabyReportCard>
    </>
  );
};

export default BabyProfile;
