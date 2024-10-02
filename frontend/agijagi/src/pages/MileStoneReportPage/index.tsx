import React, { useState } from 'react';
import styled from '@emotion/styled';
import Typhography from '../../components/common/Typography';
import theme from '../../styles/theme';
import { useLocation, useNavigate } from 'react-router-dom';
import Button from '../../components/common/Button';
import { XMarkIcon } from '@heroicons/react/24/outline';
import axios from 'axios';
import ReportTarget from '../../components/Report/ReportTarget';
import ReportIntro from '../../components/Report/ReportIntro';
import ReportChart from '../../components/Report/ReportChart';
import ReportSlide from '../../components/Report/ReportSlide';
import ReportListModal from '../../components/Report/ReportListModal';
import useModal from '../../hooks/useModal';
import BoyImg from '../../assets/images/boy.png';
import { useMutation, useQuery } from '@tanstack/react-query';
import { deleteReport, getReport } from '../../apis/report';
import useChildStore from '../../stores/useChlidStore';
import { getChildInfo } from '../../apis/milestone';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
`;

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
  overflow-y: auto;
  height: 100%;
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
const weight = 3.5;
const birth = '2024-07-02';
const gender = 'boy';
const image = BoyImg;
const days = calculateDays(birth);

const Report = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const reportId = parseInt(location.state.reportId) || 0;
  const { childId } = useChildStore();

  const childQuery = useQuery({
    queryKey: ['child', childId],
    queryFn: () => getChildInfo(childId),
  });

  const reportQuery = useQuery({
    queryKey: ['report', reportId],
    queryFn: () => getReport(childId, reportId),
  });

  const { mutate, isPending } = useMutation({
    mutationFn: deleteReport,
    onSuccess: () => navigate('/report'),
  });

  const childData = childQuery.data?.data;
  const reportData = reportQuery.data?.data;
  console.log(childData)

  if (childQuery.error) {
    return <>아이 데이터를 불러오지 못했습니다.</>;
  }
  if (childQuery.isLoading) {
    return <>로딩중</>;
  }

  if (reportQuery.error) {
    return <>보고서 데이터를 불러오지 못했습니다.</>;
  }
  if (reportQuery.isLoading) {
    return <>로딩중</>;
  }



  // const createDate = new Date(data ? data.data.createAt : '');
  // const currentWeight = data ? data.data.graphData[-1].weight : 0;

  const handleReportList = () => {
    navigate('/report');
  };

  const handleDelete = () => {
    mutate({ childId, reportId });
  };

  return (
    <Wrapper>
      <Title>
        <Button size="sm" color="secondary" onClick={handleReportList}>
          목록
        </Button>
        <TitleText>
          <Typhography size="lg" weight="bold" color="greyScale" shade="800">
            성장 분석 보고서
          </Typhography>
        </TitleText>
        <Button
          size="sm"
          color="danger"
          onClick={handleDelete}
          disabled={isPending}
        >
          삭제
        </Button>
      </Title>
      <Line></Line>

      <FilterContainer>
        <ReportTarget
          gender={childData?.gender}
          createDate={reportData?.createdAt.slice(0, 10)}
        />
      </FilterContainer>

      <ReportContainer>
        <ReportIntro weight={weight} currentWeight={reportData?.currWeight} />

        <ChartContainer>
          <ReportChart data={reportData?.graphData} />
        </ChartContainer>

        <ReportSlide image={image} growthStatus={reportData?.growthDegree} />

        <ResultContainer>
          <Typhography size="md" color="primary" shade="800">
            {name}(이)의 몸무게가 출생 후 {days}일 동안{' '}
            {reportData ? reportData.currWeight - weight : 0} kg 증가했습니다.
          </Typhography>
          <Result>
            <Typhography size="md" color="greyScale" shade="800">
              {reportData?.content}
            </Typhography>
          </Result>
        </ResultContainer>
      </ReportContainer>
    </Wrapper>
  );
};

export default Report;
