import React, { useEffect, useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Typhography from '../common/Typography';
import { babyDevelopmentData } from './MileStoneMockData';
import CheckImg from '../../assets/images/check.png';
import MileStoneAmount from './MileStoneAmount';

const CheckContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 30px;
`;

const StyledLabel = styled.div`
  display: flex;
  align-items: center;
  margin-top: 10px;
`;

const StyledInput = styled.input`
  appearance: none;
  width: 25px;
  height: 25px;
  border: 1.5px solid ${theme.color.tertiary[800]};
  border-radius: 50%;

  &:checked {
    border-color: transparent;
    background-image: url(${CheckImg});
    background-size: 26px;
    background-position: center;
    background-repeat: no-repeat;
    background-color: ${theme.color.tertiary[800]};
  }
`;

const StyledP = styled.p`
  margin: 10px 10px 12px;
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

interface MilestoneDetail {
  milestoneId: number;
  content: string;
  requiredAmount: number;
  currentAmount: number;
  date: null | string;
}

interface MileStoneProps {
  month: number;
  handleCheckboxChange: (item: MilestoneDetail, isChecked: boolean) => void;
}

const DevelopmentList = (({ month, handleCheckboxChange }: MileStoneProps) => {
  const [developmentData, setDevelopmentData] = useState<any[]>([]);

  useEffect(() => {
    setDevelopmentData(babyDevelopmentData);
  }, []);

  return (
    <>
      {developmentData.map((stage) => (
        <React.Fragment key={stage.title}>
          {stage.MilestoneDetails && (
            <CheckContainer>
              <Typhography size="lg" weight="bold" color="tertiary" shade="900">
                {stage.title}
              </Typhography>
              {stage.MilestoneDetails.map((item: MilestoneDetail) => (
                <React.Fragment key={item.milestoneId}>
                  <StyledLabel>
                    <StyledInput
                      type="checkbox"
                      id={item.content}
                      onChange={(e) => handleCheckboxChange(item, e.target.checked)}
                    />
                    <StyledP>{item.content}</StyledP>
                  </StyledLabel>
                  {item.currentAmount !== 0 && (
                    <MileStoneAmount
                      currentAmount={item.currentAmount}
                      requiredAmount={item.requiredAmount}
                    />
                  )}
                  <Line />
                </React.Fragment>
              ))}
            </CheckContainer>
          )}
        </React.Fragment>
      ))}
    </>
  );
});

export default DevelopmentList;
