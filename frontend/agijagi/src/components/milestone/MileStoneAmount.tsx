import { useEffect, useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';

const GaugeContainer = styled.div`
  margin-left: 40px;
  margin-bottom: 10px;
  width: 100px;
  height: 20px;
  background-color: ${theme.color.tertiary[50]};
  border-radius: 10px;
  overflow: hidden;
  border: 1px solid ${theme.color.primary[500]};
`;

const GaugeFill = styled.div<{ fillPercentage: number }>`
  height: 100%;
  border-radius: 8px;
  background-color: ${theme.color.primary[500]};
  width: ${({ fillPercentage }) => `${fillPercentage}%`};
  transition: width 0.8s ease-in-out;
`;

interface GaugeProps {
  requiredAmount: number;
  currentAmount: number;
}

const MileStoneAmount = ({ requiredAmount, currentAmount }: GaugeProps) => {
  const [fillPercentage, setFillPercentage] = useState<number>(0);

  useEffect(() => {
    const calculatedPercentage = Math.min(
      100,
      (currentAmount / requiredAmount) * 100
    );
    setTimeout(() => {
      setFillPercentage(calculatedPercentage);
    }, 100); // 100ms 후 애니메이션 시작
  }, [currentAmount, requiredAmount]);

  return (
    <GaugeContainer>
      <GaugeFill fillPercentage={fillPercentage} />
    </GaugeContainer>
  );
};

export default MileStoneAmount;
