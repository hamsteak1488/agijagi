import React, { useEffect, useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import { FaceSmileIcon, ScaleIcon } from '@heroicons/react/24/outline';
import Typhography from '../common/Typography';

const WeightContainer = styled.div`
  display: flex;
  width: 175px;
  height: 30px;
  align-items: center;
  margin-right: 10px;
  padding: 5px 10px;
  background-color: ${theme.color.primary[50]};
  border-radius: 30px;
  overflow: hidden;
  border: 1px solid ${theme.color.primary[700]};
`;

const WeightIcon = styled(ScaleIcon)`
  width: 20px;
  margin-right: 8px;
  color: ${theme.color.primary[800]};
`;

const GenderContainer = styled.div`
  display: flex;
  width: 60px;
  height: 30px;
  align-items: center;
  padding: 5px 10px;
  background-color: ${theme.color.primary[50]};
  border-radius: 30px;
  overflow: hidden;
  border: 1px solid ${theme.color.primary[700]};
`;

const FaceIcon = styled(FaceSmileIcon)`
  width: 25px;
  margin-right: 5px;
  color: ${theme.color.primary[800]};
`;

// 몸무게 범위 구하는 함수
function findWeightRange(weight: number) {
  // 소수점 둘째 자리에서 반올림
  const roundedWeight = Math.round(weight * 10) / 10;

  // 소수점 첫째 자리 계산 (ex: 7.0 or 7.1)
  const lowerBound = Math.floor(roundedWeight * 10) / 10;
  const upperBound = lowerBound + 0.1;

  return `${lowerBound.toFixed(1)} ~ ${upperBound.toFixed(1)} kg`;
}

interface FilterProps {
  gender: string;
  weight: number;
}

const ReportFilter = ({ gender, weight }: FilterProps) => {
  const [babayGender, setBabyGender] = useState<string>(gender);
  const WeightRange = findWeightRange(weight);

  useEffect(() => {
    if (gender === 'boy') {
      setBabyGender('남아');
    } else if (gender === 'girl') {
      setBabyGender('여아');
    }
  });

  return (
    <>
      <WeightContainer>
        <WeightIcon />
        <Typhography size="sm" color="greyScale" shade="800">
          출생 몸무게 {WeightRange}
        </Typhography>
      </WeightContainer>

      <GenderContainer>
        <FaceIcon />
        <Typhography size="sm" color="greyScale" shade="800">
          {babayGender}
        </Typhography>
      </GenderContainer>
    </>
  );
};

export default ReportFilter;
