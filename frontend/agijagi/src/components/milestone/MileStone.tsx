import React, { useState } from 'react';
import styled from '@emotion/styled';
import Typhography from '../common/Typography';
import theme from '../../styles/theme';
import Button from '../common/Button';
import { XMarkIcon } from '@heroicons/react/24/outline';
import MileStoneFilter from './MileStoneFilter';
import MileStoneCheck from './MileStoneCheck';
import axios from 'axios';

const Title = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 20px 10px 5px 20px;
  height: 50px;
  align-items: center;
`;

const CloseButton = styled.div`
  display: flex;
  align-items: center;
  margin-right: 20px;
  width: 25px;
  height: 25px;
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
  margin: 10px 20px;
  height: 30px;
`;

const MileStoneContainer = styled.div`
  background-color: ${theme.color.tertiary[50]};
  height: 84vh;
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

// 임시 아이 이름 -> 추후 데이터로 받아야함
const name = '다운';
// 임시 출생 날짜 -> 추후 데이터로 받아야함
const birth = '2024-06-23';

const birthDate = new Date(birth);
let month = calculateAgeInMonths(birthDate);

if (month % 2) {
  month -= 1;
}

interface MilestoneDetail {
  milestoneId: number;
  content: string;
  requiredAmount: number;
  currentAmount: number;
  date: null | string;
}

const MilestoneCheck = () => {
  const [months, setMonths] = useState<number>(month);
  const [selectedMilestones, setSelectedMilestones] = useState<any[]>([]);
  const childId = 1; // 예시로 지정한 childId

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
        'https://api.password926.site/milestones',
        data
      );
      console.log('데이터 저장:', response.data);
    } catch (error) {
      console.error('Error saving data:', error);
    }
  };

  const handlePrev = () => {
    setMonths((prevMonth) => {
      if (prevMonth <= 2) {
        return prevMonth;
      }
      return prevMonth - 2;
    });
  };

  const handleNext = () => {
    setMonths((prevMonth) => {
      if (prevMonth >= 36) {
        return prevMonth;
      }
      return prevMonth + 2;
    });
  };

  return (
    <>
      <Title>
        <CloseButton>
          <XMarkIcon />
        </CloseButton>
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
        <MileStoneCheck
          month={months}
          name={name}
          handleCheckboxChange={handleCheckboxChange}
        />
      </MileStoneContainer>
    </>
  );
};

export default MilestoneCheck;
