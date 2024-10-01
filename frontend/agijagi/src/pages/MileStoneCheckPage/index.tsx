import React, { useCallback, useState } from 'react';
import styled from '@emotion/styled';
import Typhography from '../../components/common/Typography';
import theme from '../../styles/theme';
import Button from '../../components/common/Button';
import { useNavigate } from 'react-router-dom';
import { XMarkIcon } from '@heroicons/react/24/outline';
import MileStoneFilter from '../../components/milestone/MileStoneFilter';
import MileStoneIntro from '../../components/milestone/MileStoneCheck';
import axios from 'axios';

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
const childId = 1;
const name = '다운';
const birth = '2024-06-23';
const weight = 3.1;

const birthDate = new Date(birth);
const month = calculateAgeInMonths(birthDate);

interface MilestoneDetail {
  milestoneId: number;
  content: string;
  requiredAmount: number;
  currentAmount: number;
  date: null | string;
}

const MilestoneCheck = () => {
  const [months, setMonths] = useState<number>(month);
  const [selectedMilestones, setSelectedMilestones] = useState<
    MilestoneDetail[]
  >([]);
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  // 체크박스 선택 처리
  const handleCheckboxChange = (item: MilestoneDetail, isChecked: boolean) => {
    if (isChecked) {
      setSelectedMilestones((prev) => [...prev, item]);
    } else {
      setSelectedMilestones((prev) =>
        prev.filter((milestone) => milestone.milestoneId !== item.milestoneId)
      );
    }
  };

  // 마일스톤 저장
  const handleSave = async () => {
    const milestones = selectedMilestones.map((item) => ({
      id: item.milestoneId,
      currentAmount: item.currentAmount + 1,
    }));

    const data = {
      childId: childId,
      milestones: milestones,
    };

    try {
      const response = await axios.patch(
        `https://api.password926.site/children/${childId}/milestone`,
        data
      );
      console.log('데이터 저장:', response.data);
    } catch (error) {
      console.error('Error saving data:', error);
    }
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
    <>
      <Title>
        <CloseButton onClick={handleBack} />
        <Typhography size="lg" weight="bold" color="greyScale" shade="800">
          발달 체크
        </Typhography>
        <Button size="sm" color="primary" onClick={handleSave}>
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
        <MileStoneIntro
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
    </>
  );
};

export default MilestoneCheck;
