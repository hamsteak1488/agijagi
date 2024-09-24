import React, { useEffect, useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Typhography from '../common/Typography';
import { babyDevelopmentData } from './MileStoneMockData';
import CheckImg from '../../assets/images/check.png';
// import axios from "axios";

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
    background-image: url(${CheckImg});
    background-size: 26px;
    background-position: center;
    background-repeat: no-repeat;
    background-color: ${theme.color.tertiary[800]};
  }
`;

const StyledLabel = styled.div`
  display: flex;
  align-items: center;
  margin-top: 10px;
`;

const StyledP = styled.p`
  margin: 12px 10px;
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

interface MilestoneDetail {
  milestoneId: number;
  content: string;
  requiredAmount: number;
  currentAmount: number;
  date: null | string;
}

const DevelopmentList = ({ month }: MileStoneProps) => {
  const [developmentData, setDevelopmentData] =
    useState<any[]>([]);

  useEffect(() => {
    setDevelopmentData(babyDevelopmentData)
  })

  return (
    <>
      {developmentData.map((stage) => (
        <>
          {stage.title === '움직임 / 신체발달' && (
            <CheckContainer>
              <Typhography size="lg" weight="bold" color="tertiary" shade="900">
                움직임 / 신체발달
              </Typhography>
              {stage.MilestoneDetails.map((item: MilestoneDetail) => (
                <>
                  <StyledLabel>
                    <StyledInput type="checkbox" id={item.content} />
                    <StyledP>{item.content}</StyledP>
                  </StyledLabel>
                  <Line />
                </>
              ))}
            </CheckContainer>
          )}

          {stage.title === '언어 / 의사소통' && (
            <CheckContainer>
              <Typhography size="lg" weight="bold" color="tertiary" shade="900">
                언어 / 의사소통
              </Typhography>
              {stage.MilestoneDetails.map((item: MilestoneDetail) => (
                <>
                  <StyledLabel>
                    <StyledInput type="checkbox" id={item.content} />
                    <StyledP>{item.content}</StyledP>
                  </StyledLabel>
                  <Line />
                </>
              ))}
            </CheckContainer>
          )}

          {stage.title === '인지' && (
            <CheckContainer>
              <Typhography size="lg" weight="bold" color="tertiary" shade="900">
                인지 (학습, 사고, 문제해결)
              </Typhography>
              {stage.MilestoneDetails.map((item: MilestoneDetail) => (
                <>
                  <StyledLabel>
                    <StyledInput type="checkbox" id={item.content} />
                    <StyledP>{item.content}</StyledP>
                  </StyledLabel>
                  <Line />
                </>
              ))}
            </CheckContainer>
          )}

          {stage.title === '사회성' && (
            <CheckContainer>
              <Typhography size="lg" weight="bold" color="tertiary" shade="900">
                사회성 / 정서
              </Typhography>
              {stage.MilestoneDetails.map((item: MilestoneDetail) => (
                <>
                  <StyledLabel>
                    <StyledInput type="checkbox" id={item.content} />
                    <StyledP>{item.content}</StyledP>
                  </StyledLabel>
                  <Line />
                </>
              ))}
            </CheckContainer>
          )}
        </>
      ))}
    </>
  );
};

export default DevelopmentList;
