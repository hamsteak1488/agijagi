import { useState, useEffect } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import ProfileIcon from '../common/ProfileIcon/ProfileIcon';
import Button from '../common/Button';
import MileStoneCheckList from './MileStoneCheckList';
import Textfield from '../common/Textfield';
import useModal from '../../hooks/useModal';
import ReportModal from '../Report/ReportCreateModal';
import { ValidationState } from '../common/Textfield/Textfield.types';
import { MilestoneDetail, putHeightWeight } from '../../apis/milestone';
import { useMutation, useQuery } from '@tanstack/react-query';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  max-width: 760px;
  margin: 0 auto;
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

const ButtonWrapper = styled.div<{ visible: boolean }>`
  display: flex;
  justify-content: ${(props) =>
    props.visible ? 'space-between' : 'space-around'};
  padding: 10px;

  @media (min-width: 700px) {
    width: 90%;
    margin: 0 auto;
  }
`;

const ReportButton = styled.div<{ visible: boolean }>`
  display: ${(props) => (props.visible ? 'block' : 'none')};
`;

const TextFieldContainer = styled.div<{ expanded: boolean; visible: boolean }>`
  display: flex;
  justify-content: ${(props) => (props.visible ? 'flex-end' : 'center')};
  width: 100%;
  margin: 5px auto;
  max-height: ${(props) => (props.expanded ? '500px' : '0')};
  overflow: hidden;
  transition: max-height 0.5s ease;

  @media (min-width: 700px) {
    width: 90%;
    margin-right: 20px;
  }
`;

const TextFieldWrapper = styled.div`
  display: flex;
  margin: 5px 0 10px;
  flex-direction: column;
  gap: 20px;
  margin-right: 10px;
`;

const SaveButton = styled.div<{ visible: boolean }>`
  display: flex;
  justify-content: ${(props) => (props.visible ? 'flex-end' : 'center')};
`;

const CheckContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 10px 20px;
  margin-bottom: 50px;
`;

const WarningMessage = styled.div`
  position: fixed;
  text-align: center;
  width: 230px;
  bottom: 250px;
  left: 50%;
  transform: translateX(-50%);
  background-color: ${theme.color.danger[500]};
  color: white;
  padding: 15px;
  border-radius: 10px;
  z-index: 1000;
  font-size: ${theme.typography.fontSize.md};
`;

// 현재 아이가 몇 개월인지 계산하는 함수
const calculateAgeInMonths = (birthDate: Date): number => {
  const today = new Date();
  let yearsDiff = today.getFullYear() - birthDate.getFullYear();
  let monthsDiff = today.getMonth() - birthDate.getMonth();

  if (monthsDiff < 0) {
    yearsDiff--;
    monthsDiff += 12;
  }

  return yearsDiff * 12 + monthsDiff;
};

interface MileStoneProps {
  month: number;
  name: string;
  childId: number;
  birth: Date;
  weight: number | null;
  selectedMilestones: MilestoneDetail[];
  handleSave: () => void;
  handleCheckboxChange: (item: MilestoneDetail, isChecked: boolean) => void;
}

const MileStoneCheck = ({
  month,
  name,
  childId,
  birth,
  weight,
  selectedMilestones,
  handleSave,
  handleCheckboxChange,
}: MileStoneProps) => {
  const [textFieldOpen, setTextFieldOpen] = useState<boolean>(false);
  const [currentHeight, setCurrentHeight] = useState<string>('');
  const [currentWeight, setCurrentWeight] = useState<string>('');
  const [warning, setWarning] = useState<string>('');
  const babyMonth = calculateAgeInMonths(birth);

  const modal = useModal();
  const { mutate, isPending } = useMutation({
    mutationFn: putHeightWeight,
  });

  useEffect(() => {
    setCurrentHeight('');
    setCurrentWeight('');
    setTextFieldOpen(false);
  }, [month]);

  const handleTextFieldOpen = () => {
    setTextFieldOpen(!textFieldOpen);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const handleReportModalOpen = () => {
    if (!weight) {
      setWarning('출생 몸무게를 먼저 입력해주세요');
      setTimeout(() => setWarning(''), 3000);
    } else {
      modal.push({
        children: (
          <ReportModal
            selectedMilestones={selectedMilestones}
            currentHeight={currentHeight}
            currentWeight={currentWeight}
            handleWeightHeighSave={handleWeightHeighSave}
            handleSave={handleSave}
            childId={childId}
          />
        ),
      });
    }
  };

  const handleWeightHeighSave = () => {
    // 입력이 없으면 경고 메세지
    if (!currentHeight || !currentWeight) {
      setWarning('키와 몸무게를 모두 입력해주세요');
      setTimeout(() => setWarning(''), 3000);
    }
    // 키 / 몸무게 저장 API 호출 (put)
    mutate({
      childId,
      data: {
        height: parseFloat(currentHeight),
        weight: parseFloat(currentWeight),
        month: month,
      },
    });
  };

  function validateInput(input: string): ValidationState {
    if (input.trim() === '') {
      return 'normal';
    }
    if (!isNaN(Number(input))) {
      return 'success';
    }
    return 'danger';
  }

  return (
    <Wrapper>
      <IntroBox>
        <IntroImg>
          <ProfileIcon size="md" />
        </IntroImg>
        <IntroText>
          생후 {`${month}`}개월 된 아기들 중 80% 정도가 할 수 있는 발달
          목록입니다. {`${name}`}(이)가 달성한 마일스톤을 체크해보세요.
        </IntroText>
      </IntroBox>
      <ButtonWrapper visible={babyMonth === month}>
        <ReportButton visible={babyMonth === month}>
          <Button size="sm" color="secondary" onClick={handleReportModalOpen}>
            성장 분석보고서 생성
          </Button>
        </ReportButton>

        <Button size="sm" color="primary" onClick={handleTextFieldOpen}>
          키/몸무게 입력
        </Button>
      </ButtonWrapper>

      <TextFieldContainer
        expanded={textFieldOpen}
        visible={babyMonth === month}
      >
        {textFieldOpen && (
          <TextFieldWrapper>
            <Textfield
              label="키 cm"
              size="md"
              color="tertiary"
              isColoredLabel={true}
              inputValue={currentHeight}
              setInputValue={setCurrentHeight}
              validationFunction={validateInput}
              helpText={'*숫자만 입력해주세요'}
              checkText={'올바르게 입력했습니다. 저장해주세요'}
              warningText={'숫자만 입력해주세요'}
            />
            <Textfield
              label="몸무게 kg"
              size="md"
              color="tertiary"
              isColoredLabel={true}
              inputValue={currentWeight}
              setInputValue={setCurrentWeight}
              validationFunction={validateInput}
              helpText={'*숫자만 입력해주세요'}
              checkText={'올바르게 입력했습니다. 저장해주세요'}
              warningText={'숫자만 입력해주세요'}
            />
            <SaveButton visible={babyMonth === month}>
              <Button
                size="sm"
                color="primary"
                onClick={handleWeightHeighSave}
                disabled={isPending}
              >
                키/몸무게 저장
              </Button>
            </SaveButton>
          </TextFieldWrapper>
        )}
      </TextFieldContainer>

      {warning && <WarningMessage>{warning}</WarningMessage>}

      <CheckContainer>
        <MileStoneCheckList
          month={month}
          childId={childId}
          handleCheckboxChange={handleCheckboxChange}
        />
      </CheckContainer>
    </Wrapper>
  );
};

export default MileStoneCheck;
