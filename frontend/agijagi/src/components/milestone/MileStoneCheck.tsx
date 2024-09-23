import React, { useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Typhography from '../common/Typography';
import ProfileIcon from '../common/ProfileIcon/ProfileIcon';
import Button from '../common/Button';
import { babyDevelopmentData } from './MileStoneMockData';
import { CheckIcon } from '@heroicons/react/24/outline';
import DevelopmentList from './DevelopmentList';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 auto;

  @media (min-width: 700px) {
    width: 80%;
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
  justify-content: center;
  margin-right: 10px;
  font-size: ${theme.typography.fontSize.sm};
`;

const ButtonWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 10px;

  @media (min-width: 700px) {
    width: 95%;
    margin: 0 auto;
  }
`;

const CheckContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 10px 20px;
`;

interface MileStoneProps {
  month: number;
  name: string;
}

interface DevelopmentStage {
  month: number;
  movementPhysical: string[];
  communicationLanguage: string[];
  cognition: string[];
  socialEmotional: string[];
}

const MileStoneCheck = ({ month, name }: MileStoneProps) => {
  const [developmentData, setDevelopmentData] =
    useState<DevelopmentStage[]>(babyDevelopmentData);

  const MonthDevelopmentData = babyDevelopmentData.filter((data) => {
    return data.month === month;
  });

  return (
    <Wrapper>
      <IntroBox>
        <IntroImg>
          <ProfileIcon size="md" />
        </IntroImg>
        <IntroText>
          생후 {`${month}`}개월 아기들 중 75% 정도가 할 수 있는 것들입니다.{' '}
          {`${name}`}(이)가 달성한 마일스톤을 체크해보세요.
        </IntroText>
      </IntroBox>
      <ButtonWrapper>
        <Button size="sm" color="secondary">
          성장 분석보고서 생성
        </Button>
        <Button size="sm" color="primary">
          키/몸무게 입력
        </Button>
      </ButtonWrapper>

      <CheckContainer>
        <DevelopmentList month={month}/>
      </CheckContainer>
    </Wrapper>
  );
};

export default MileStoneCheck;
