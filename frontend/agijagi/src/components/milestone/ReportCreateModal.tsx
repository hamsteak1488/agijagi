import { useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Button from '../common/Button';
import Typhography from '../common/Typography';
import { useNavigate } from 'react-router-dom';
import { XMarkIcon } from '@heroicons/react/24/outline';
import Loading from './ReportLoading';
import axios from 'axios';

const ModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 300px;
  height: 200px;
  border-radius: 10px;
  background-color: ${theme.color.tertiary[50]};
`;

const HeaderWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 95%;
`;

const CloseButton = styled(XMarkIcon)`
  width: 25px;
  height: 25px;
  cursor: pointer;
  color: ${theme.color.greyScale[800]};
`;

const TextWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  margin: 10px auto;
  text-align: center;
`;

const ButtonWrapper = styled.div`
  display: flex;
  width: 65%;
  margin: 15px auto;
  justify-content: space-between;
`;

interface MilestoneDetail {
  milestoneId: number;
  content: string;
  requiredAmount: number;
  currentAmount: number;
  date: null | string;
}

interface ModalProps {
  selectedMilestones: MilestoneDetail[];
  currentWeight: string;
  currentHeight: string;
  handleWeightHeighSave: () => void;
  handleSave: () => void;
  childId: number;
}

const ReportModal = ({
  selectedMilestones,
  currentHeight,
  currentWeight,
  handleWeightHeighSave,
  handleSave,
  childId,
}: ModalProps) => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  const createReport = async () => {
    // 선택된 마일스톤이 있거나 키몸무게를 입력했다면 저장부터 하기
    if (selectedMilestones.length) {
      handleSave();
    }
    if (currentHeight && currentWeight) {
      handleWeightHeighSave();
    }
    // 보고서 생성 API 호출 (get)
    try {
      const response = await axios.get(
        `https://api.password926.site/children/${childId}/milestone-analysis`
      );
      console.log('보고서 데이터 :', response.data);
      navigate('/milestone-report', { state: { reportData: response.data } });
    } catch (error) {
      console.error('보고서 데이터 받기 실패:', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <ModalContainer>
      <HeaderWrapper>
        <CloseButton onClick={handleBack} />
      </HeaderWrapper>

      {isLoading ? (
        <Loading />
      ) : (
        <>
          <TextWrapper>
            {selectedMilestones.length || (currentHeight && currentWeight) ? (
              <Typhography size="lg" color="greyScale" shade="800">
                마일스톤을 저장하고 <br /> 분석 보고서를 생성하시겠습니까?
              </Typhography>
            ) : (
              <Typhography size="lg" color="greyScale" shade="800">
                성장 분석 보고서를 <br /> 생성하시겠습니까?
              </Typhography>
            )}
          </TextWrapper>

          <ButtonWrapper>
            {selectedMilestones.length || (currentHeight && currentWeight) ? (
              <Button size="md" color="secondary" onClick={createReport}>
                저장 후 생성
              </Button>
            ) : (
              <Button size="md" color="secondary" onClick={createReport}>
                생성
              </Button>
            )}

            <Button size="md" color="greyScale" onClick={handleBack}>
              취소
            </Button>
          </ButtonWrapper>
        </>
      )}
    </ModalContainer>
  );
};

export default ReportModal;
