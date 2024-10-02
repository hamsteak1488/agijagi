import React, { useCallback, useState } from 'react';
import styled from '@emotion/styled';
import Typhography from '../../components/common/Typography';
import theme from '../../styles/theme';
import Button from '../../components/common/Button';
import { useNavigate } from 'react-router-dom';
import { XMarkIcon } from '@heroicons/react/24/outline';
import MileStoneFilter from '../../components/milestone/MileStoneFilter';
import MileStoneCheck from '../../components/milestone/MileStoneCheck';
import axios from 'axios';
import { MilestoneDetail, patchMilestone } from '../../apis/milestone';
import useChildStore from '../../stores/useChlidStore';
import { useMutation } from '@tanstack/react-query';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
`;

const Title = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 5px 15px 5px 20px;
  height: 50px;
  align-items: center;
`;

const CloseButton = styled(XMarkIcon)`
  display: flex;
  align-items: center;
  margin-right: 20px;
  width: 25px;
  height: 25px;
  cursor: pointer;
  color: ${theme.color.greyScale[800]};
`;

const Line = styled.hr`
  border: none;
  background-color: ${theme.color.greyScale[400]};
  height: 1px;
  margin: 0;
`;

const FilterContainer = styled.div`
  display: flex;
  justify-content: center;
  margin: 10px 20px;
  height: 30px;
`;

const MileStoneContainer = styled.div`
  flex: 1 1 auto;
  background-color: ${theme.color.tertiary[50]};
  overflow-y: auto;
  padding: 10px;
`;

// 태어난지 몇개월 인지 계산하는 함수
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

// 임시 아이 -> 추후 데이터로 받아야함
const name = '다운';
const birth = '2024-06-23';
const weight = 3.1;

const birthDate = new Date(birth);
const month = calculateAgeInMonths(birthDate);

const MilestoneCheck = () => {
  const [months, setMonths] = useState<number>(month);
  const [selectedMilestones, setSelectedMilestones] = useState<
    MilestoneDetail[]
  >([]);
  const navigate = useNavigate();
  const { childId } = useChildStore();

  const { mutate, isPending } = useMutation({
    mutationFn: patchMilestone,
  });

  const handleBack = () => {
    navigate(-1);
  };

  // 체크박스 선택 처리
  const handleCheckboxChange = (item: MilestoneDetail, isChecked: boolean) => {
    if (isChecked) {
      setSelectedMilestones((prev) => [...prev, item]);
    } else {
      setSelectedMilestones((prev) =>
        prev.filter((milestone) => milestone.id !== item.id)
      );
    }
  };

  const handleSave = async () => {
    const milestoneDatas = selectedMilestones.map((item) => ({
      id: item.id,
      currentAmount: item.currentAmount + 1,
    }));

    // 마일스톤 수정 api 호출 (patch)
    mutate({
      childId,
      data: {
        childId: childId,
        milestones: milestoneDatas,
      },
    });
  };

  const handlePrev = () => {
    setMonths((prevMonth) => {
      if (prevMonth <= 1) {
        return prevMonth;
      }
      return prevMonth - 1;
    });
  };

  const handleNext = () => {
    setMonths((prevMonth) => {
      if (prevMonth >= 24) {
        return prevMonth;
      }
      return prevMonth + 1;
    });
  };

  return (
    <Wrapper>
      <Title>
        <CloseButton onClick={handleBack} />
        <Typhography size="lg" weight="bold" color="greyScale" shade="800">
          발달 체크
        </Typhography>
        <Button
          size="sm"
          color="primary"
          onClick={handleSave}
          disabled={isPending}
        >
          저장
        </Button>
      </Title>
      <Line></Line>
      <FilterContainer>
        <MileStoneFilter
          month={months}
          handlePrev={handlePrev}
          handleNext={handleNext}
        />
      </FilterContainer>
      <MileStoneContainer>
        <MileStoneCheck
          month={months}
          name={name}
          childId={childId}
          birth={birthDate}
          weight={weight}
          selectedMilestones={selectedMilestones}
          handleSave={handleSave}
          handleCheckboxChange={handleCheckboxChange}
        />
      </MileStoneContainer>
    </Wrapper>
  );
};

export default MilestoneCheck;
