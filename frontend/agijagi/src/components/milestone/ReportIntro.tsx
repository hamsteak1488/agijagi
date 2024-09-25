import React, { useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Typhography from '../common/Typography';
import ProfileIcon from '../common/ProfileIcon/ProfileIcon';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 auto;

  @media (min-width: 700px) {
    width: 85%;
  }
`;

const IntroBox = styled.div`
  display: grid;
  align-items: center;
  grid-template-columns: 1fr 3fr;
  gap: 5px;
  padding: 10px 5px;
`;

const IntroImg = styled.div`
  display: flex;
  justify-content: center;
`;

const IntroText = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-right: 10px;
`;

interface ReportIntroProps {
  weight: number;
}

const ReportIntro = ({weight} : ReportIntroProps) => {
  // 현재 몸무게 받아와야 함
  const currentWeight = 7.2

  return (
    <Wrapper>
      <IntroBox>
        <IntroImg>
          <ProfileIcon size="md" />
        </IntroImg>
        <IntroText>
          <Typhography>출생 몸무게 : {weight} kg</Typhography>
          <Typhography color='tertiary' shade='900'>현재 몸무게 : {currentWeight} kg</Typhography>
        </IntroText>
      </IntroBox>
    </Wrapper>
  )
};

export default ReportIntro;