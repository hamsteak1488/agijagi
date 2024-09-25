import React, { useState } from 'react';
import styled from '@emotion/styled';
import Typhography from '../common/Typography';
import theme from '../../styles/theme';
import Button from '../common/Button';
import { XMarkIcon } from '@heroicons/react/24/outline';
import axios from 'axios';
import ReportFilter from './ReportFilter';
import ReportIntro from './ReportIntro';
import ReportChart from './ReportChart';
import ReportSlide from './ReportSlide';
import BoyImg from '../../assets/images/boy.png';

const Title = styled.div`
  display: flex;
  margin: 20px 35px 5px 15px;
  height: 50px;
  align-items: center;
`;

const TitleText = styled.div`
  display: flex;
  margin: 0 auto;
`;

const CloseButton = styled.div`
  display: flex;
  justify-content: flex-start;
  align-items: center;
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
  align-items: center;
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

// 임의로 만든 아기 데이터
const weight = 3.02;
const gender = 'boy';
const image = BoyImg;
const growStatus = 'fast';

const MileStoneReport = () => {
  return (
    <>
      <Title>
        <CloseButton>
          <XMarkIcon />
        </CloseButton>
        <TitleText>
          <Typhography size="lg" weight="bold" color="greyScale" shade="800">
            성장 분석 보고서
          </Typhography>
        </TitleText>
      </Title>
      <Line></Line>

      <FilterContainer>
        <ReportFilter gender={gender} weight={weight} />
      </FilterContainer>

      <ReportContainer>
        <ReportIntro weight={weight} />
        <ReportSlide image={image} growthStatus={growStatus} />
        <ChartContainer>
          <ReportChart />
        </ChartContainer>
      </ReportContainer>
    </>
  );
};

export default MileStoneReport;
