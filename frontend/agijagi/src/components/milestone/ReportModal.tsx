import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Button from '../common/Button';
import Typhography from '../common/Typography';
import { useNavigate } from 'react-router-dom';
import { XMarkIcon } from '@heroicons/react/24/outline';

const ModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 300px;
  height: 200px;
  border-radius: 10px;
  background-color: ${theme.color.tertiary[50]};

  @media (min-width: 700px) {
    width: 360px;
    margin: 0 auto;
  }
`;

const HeaderWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  padding: 0 10px;
  width: 90%;
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
  width: 90%;
  margin: 5px auto;
  text-align: center;
`;

const ButtonWrapper = styled.div`
  display: flex;
  width: 60%;
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
}

const ReportModal = ({ selectedMilestones }: ModalProps) => {
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  const createReport = () => {
    // 보고서 생성 API 호출
  };

  return (
    <ModalContainer>
      <HeaderWrapper>
        <CloseButton onClick={handleBack} />
      </HeaderWrapper>

      <TextWrapper>
        {selectedMilestones.length === 0 ? (
          <Typhography size="lg" color="greyScale" shade="800">
            성장 분석 보고서를 <br /> 생성하시겠습니까?
          </Typhography>
        ) : (
          <Typhography size="lg" color="greyScale" shade="800">
            마일스톤을 저장하고 <br /> 분석 보고서를 생성하시겠습니까?
          </Typhography>
        )}
      </TextWrapper>

      <ButtonWrapper>
        
        <Button size="md" color="secondary" onClick={createReport}>
          확인
        </Button>
        <Button size="md" color="greyScale" onClick={handleBack}>
          취소
        </Button>
      </ButtonWrapper>
    </ModalContainer>
  );
};

export default ReportModal;
