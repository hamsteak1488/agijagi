import React, { useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Typhography from '../common/Typography';
import { babyDevelopmentData } from './MileStoneMockData';
import { CheckIcon } from '@heroicons/react/24/outline';

const CheckContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 30px;
`;

const StyledInput = styled.input`
  appearance: none;
  width: 25px;
  height: 25px;
  border: 1.5px solid ${theme.color.tertiary[800]};
  border-radius: 50px;

  &:checked {
    border-color: transparent;
    background-image: url();
    background-color: ${theme.color.tertiary[800]};
  }
`;

const StyledLabel = styled.div`
  display: flex;
  align-items: center;
  margin-top: 10px;
  margin-left: 5px;
`;

const StyledP = styled.p`
  margin-left: 10px;
  font-size: ${theme.typography.fontSize.md};
  color: ${theme.color.greyScale[700]};
`;

const Line = styled.hr`
  border: none;
  margin: 0 40px;
  width: 90%;
  background-color: ${theme.color.tertiary[700]};
  height: 1px;
`;

interface MileStoneProps {
  month: number;
}

const DevelopmentList = ({ month }: MileStoneProps) => {
  const MonthDevelopmentData = babyDevelopmentData.filter((data) => {
    return data.month === month;
  });

  return (
    <>
      {MonthDevelopmentData.map((stage) => (
        <>
          <CheckContainer>
            <Typhography size="lg" weight="bold" color="tertiary" shade="900">
              움직임 / 신체발달
            </Typhography>
            {stage.movementPhysical.map((item, index) => (
              <>
                <StyledLabel>
                  <StyledInput type="checkbox" id={item} />
                  <StyledP>{item}</StyledP>
                </StyledLabel>
                <Line />
              </>
            ))}
          </CheckContainer>

          <CheckContainer>
            <Typhography size="lg" weight="bold" color="tertiary" shade="900">
              언어 / 의사소통
            </Typhography>
            {stage.communicationLanguage.map((item, index) => (
              <>
                <StyledLabel>
                  <StyledInput type="checkbox" id={item} />
                  <StyledP>{item}</StyledP>
                </StyledLabel>
                <Line />
              </>
            ))}
          </CheckContainer>

          <CheckContainer>
            <Typhography size="lg" weight="bold" color="tertiary" shade="900">
              인지력 (학습, 사고, 문제해결)
            </Typhography>
            {stage.cognition.map((item, index) => (
              <>
                <StyledLabel>
                  <StyledInput type="checkbox" id={item} />
                  <StyledP>{item}</StyledP>
                </StyledLabel>
                <Line />
              </>
            ))}
          </CheckContainer>

          <CheckContainer>
            <Typhography size="lg" weight="bold" color="tertiary" shade="900">
              사회성 / 정서
            </Typhography>
            {stage.socialEmotional.map((item, index) => (
              <>
                <StyledLabel>
                  <StyledInput type="checkbox" id={item} />
                  <StyledP>{item}</StyledP>
                </StyledLabel>
                <Line />
              </>
            ))}
          </CheckContainer>
        </>
      ))}
    </>
  );
};

export default DevelopmentList;
