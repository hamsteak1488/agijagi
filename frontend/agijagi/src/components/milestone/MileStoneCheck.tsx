import React, { useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Typhography from '../common/Typography';
import ProfileIcon from '../common/ProfileIcon/ProfileIcon';
import Button from '../common/Button';
import { babyDevelopmentData } from './MileStoneMockData';
import DevelopmentList from './DevelopmentList';
import Textfield from '../common/Textfield';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 auto;
  padding-bottom: 50px;

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
  justify-content: space-between;
  padding: 10px;

  @media (min-width: 700px) {
    width: 95%;
    margin: 0 auto;
  }
`;

const TextFieldContainer = styled.div<{ expanded: boolean }>`
  display: flex;
  justify-content: flex-end;
  width: 100%;
  margin: 5px auto;
  max-height: ${(props) => (props.expanded ? '500px' : '0')};
  overflow: hidden;
  transition: max-height 0.5s ease;
`;

const TextFieldWrapper = styled.div`
  display: flex;
  margin: 5px 0 10px;
  flex-direction: column;
  gap: 5px;
  margin-right: 10px;

  @media (max-width: 360px) {
    flex-direction: column;
    align-items: flex-end;
    gap: 5px;
    margin-right: 10px;
  }
`;

const CheckContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 10px 20px;
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
  name: string;
  handleCheckboxChange: (item: MilestoneDetail, isChecked: boolean) => void;
}

interface DevelopmentStage {
  month: number;
  movementPhysical: string[];
  communicationLanguage: string[];
  cognition: string[];
  socialEmotional: string[];
}

const MileStoneCheck = ({ month, name, handleCheckboxChange }: MileStoneProps) => {
  const [textFieldOpen, setTextFieldOpen] = useState<boolean>(false);
  const [height, setHeight] = useState<string>('');
  const [weight, setWeight] = useState<string>('');
  const [headLength, setHeadLength] = useState<number>(0);

  const handleTextFieldOpen = () => {
    setTextFieldOpen(!textFieldOpen);
  };

  return (
    <Wrapper>
      <IntroBox>
        <IntroImg>
          <ProfileIcon size="md" />
        </IntroImg>
        <IntroText>
          생후 {`${month}`}개월 된 아기들 중 80% 정도가 할 수 있는 발달 목록입니다.{' '}
          {`${name}`}(이)가 달성한 마일스톤을 체크해보세요.
        </IntroText>
      </IntroBox>
      <ButtonWrapper>
        <Button size="sm" color="secondary">
          성장 분석보고서 생성
        </Button>
        <Button size="sm" color="primary" onClick={handleTextFieldOpen}>
          키/몸무게 입력
        </Button>
      </ButtonWrapper>

      <TextFieldContainer expanded={textFieldOpen}>
        {textFieldOpen && (
          <TextFieldWrapper>
            <Textfield
              label="키 cm"
              size="md"
              color="tertiary"
              isColoredLabel={true}
              inputValue={height}
              setInputValue={setHeight}
            />
            <Textfield
              label="몸무게 kg"
              size="md"
              color="tertiary"
              isColoredLabel={true}
              inputValue={weight}
              setInputValue={setWeight}
            />
          </TextFieldWrapper>
        )}
      </TextFieldContainer>

      <CheckContainer>
        <DevelopmentList month={month} handleCheckboxChange={handleCheckboxChange}/>
      </CheckContainer>
    </Wrapper>
  );
};

export default MileStoneCheck;
