import React, { useState } from 'react';
import styled from '@emotion/styled';
import Typhography from '../../components/common/Typography';
import theme from '../../styles/theme';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/common/Button';
import { XMarkIcon } from '@heroicons/react/24/outline';
import axios from 'axios';
import ReportFilter from '../../components/milestone/ReportFilter';
import ReportIntro from '../../components/milestone/ReportIntro';
import ReportChart from '../../components/milestone/ReportChart';
import ReportSlide from '../../components/milestone/ReportSlide';
import ReportListModal from '../../components/milestone/ReportListModal';
import useModal from '../../hooks/useModal';
import BoyImg from '../../assets/images/boy.png';

const Title = styled.div`
  display: flex;
  margin: 5px 15px;
  height: 50px;
  align-items: center;
`;

const TitleText = styled.div`
  display: flex;
  margin: 0 auto;
`;

const CloseButton = styled(XMarkIcon)`
  display: flex;
  align-items: center;
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
  margin: 10px 15px;
  height: 35px;
`;

const ReportContainer = styled.div`
  background-color: ${theme.color.tertiary[50]};
  height: 84vh;
  overflow-y: auto;
  padding: 10px;
`;

const ChartContainer = styled.div`
  width: 100%;
  max-width: 700px;
  margin: 10px auto;
  height: 280px;
`;

const ResultContainer = styled.div`
  width: 90%;
  max-width: 700px;
  margin: 20px auto 80px;
`;

const Result = styled.div`
  margin-top: 10px;
`;

function calculateDays(birthDateString: string) {
  const birthDate = new Date(birthDateString);
  const currentDate = new Date();

  // 두 날짜 간의 차이를 밀리초로 계산
  const timeDifference: number = currentDate.getTime() - birthDate.getTime();

  // 밀리초를 일 단위로 변환 (1일 = 24시간 * 60분 * 60초 * 1000밀리초)
  const daysDifference: number = Math.floor(
    timeDifference / (1000 * 60 * 60 * 24)
  );

  return daysDifference;
}

// 임의로 만든 아기 데이터
const name = '다운';
const birth = '2024-07-02';
const weight = 3.02;
const currentWeight = 7.2;
const gender = 'boy';
const image = BoyImg;
const increaseWeight = currentWeight - weight;
const days = calculateDays(birth);

const Report = () => {
  const createDate = '2024-09-27';
  const growthStatus = 6;
  const result =
    '다운이와 비슷한 출생 몸무게를 가진 아이들의 성장 그래프와 비교했을 때 다운이는 성장이 빠른 편이라고 할 수 있습니다. 마일스톤 결과를 봐도 성장 발달에 문제가 없는 것으로 보입니다. 다운이는 잘 자라고 있습니다!';

  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  const handleDelete = () => {

  };

  const modal = useModal();
  const handleReportListModal = () => {
    modal.push({
      children: (
        <ReportListModal
        />
      ),
    });
  }

  return (
    <>
      <Title>
        <Button size='sm' color='secondary' onClick={handleBack}>목록</Button>
        <TitleText>
          <Typhography size="lg" weight="bold" color="greyScale" shade="800">
            성장 분석 보고서
          </Typhography>
        </TitleText>
        <Button size='sm' color='danger' onClick={handleDelete}>삭제</Button>
      </Title>
      <Line></Line>

      <FilterContainer>
        <ReportFilter gender={gender} createDate={createDate} />
      </FilterContainer>

      <ReportContainer>
        <ReportIntro weight={weight} currentWeight={currentWeight} />

        <ChartContainer>
          <ReportChart />
        </ChartContainer>

        <ReportSlide image={image} growthStatus={growthStatus} />

        <ResultContainer>
          <Typhography size="md" color="primary" shade="800">
            {name}(이)의 몸무게가 출생 후 {days}일 동안 {increaseWeight} kg
            증가했습니다.
          </Typhography>
          <Result>
            <Typhography size="md" color="greyScale" shade="800">
              {result}
            </Typhography>
          </Result>
        </ResultContainer>
      </ReportContainer>
    </>
  );
};

export default Report;
