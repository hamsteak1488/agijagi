import { ReactNode, useState } from 'react';
import styled from '@emotion/styled';
import theme from '../../styles/theme';
import Typhography from '../common/Typography';
import { useNavigate } from 'react-router-dom';
import { CalendarIcon } from '@heroicons/react/24/outline';
import ReportCover from '../../assets/images/report/reportcover.png';
import { useQuery } from '@tanstack/react-query';
import { getReportList } from '../../apis/report';

const ReportListWrapper = styled.div`
  display: grid;
  grid-template-columns: 1fr;
  gap: 10px;
  max-width: 700px;
  height: 100px;
  margin: 0 auto;

  /* 화면 너비가 700px 이상일 때 */
  @media (min-width: 700px) {
    grid-template-columns: 1fr 1fr; /* 두 개의 열로 변경 */
    height: 300px;
  }
  @media (max-width: 600px) {
    flex-direction: column;
    text-align: center;
  }
`;

const ReportContainer = styled.div`
  display: flex;
  justify-content: center;
  height: 100px;
  gap: 20px;
  padding: 15px;
  align-items: center;
  animation: smoothAppear 0.6s ease-in-out;

  @keyframes smoothAppear {
    from {
      opacity: 0;
      transform: translateY(-5%);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
`;

const ImageContainer = styled.div`
  display: flex;
  position: relative;
  justify-content: center;
`;

const BookImage = styled.img`
  min-width: 65px;
  height: 95px;
  border-radius: 10px;
  box-shadow: 5px 8px 20px rgba(0, 0, 0, 0.2);
`;

const DaysText = styled.div`
  display: flex;
  justify-content: center;
  position: absolute;
  text-align: center;
  top: 25%;
  width: 100%;
  font-size: ${theme.typography.fontSize.sm};
  font-weight: ${theme.typography.fontWeight.bold};
  color: ${theme.color.primary[800]};
`;

const LabelContainer = styled.div`
  flex-direction: column;
  justify-content: center;
  text-align: left;
`;

const TitleLabel = styled.div`
  font-size: ${theme.typography.fontSize.md};
  font-weight: ${theme.typography.fontWeight.regular};
  color: ${theme.color.greyScale[800]};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const DateLabel = styled.div`
  display: flex;
  margin-top: 20px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const DateContainer = styled.div`
  display: flex;
  justify-content: center;
  width: 170px;
  height: 25px;
  align-items: center;
  padding: 5px 10px;
  background-color: ${theme.color.primary[50]};
  border-radius: 30px;
  overflow: hidden;
  border: 1px solid ${theme.color.primary[700]};
`;

const DateIcon = styled(CalendarIcon)`
  width: 20px;
  margin-right: 5px;
  color: ${theme.color.primary[800]};
`;

interface ReportListProps {
  name: string | undefined;
  birth: string | undefined;
  year: number;
  childId: number;
}

const ReportList = ({ name, birth, year, childId }: ReportListProps) => {
  const navigate = useNavigate();

  const { data, error, isLoading } = useQuery({
    queryKey: ['reportlist', childId],
    queryFn: () => getReportList(childId),
  });

  if (error) {
    return <>데이터를 불러오지 못했습니다.</>;
  }
  if (isLoading) {
    return <>로딩중</>;
  }

  const onReportSelect = (id: number) => {
    navigate('/milestone-report', { state: { reportId: id } });
  };

  const calculateDays = (date: string) => {
    const createDate = new Date(date);
    const birthDate = new Date(birth? birth: '');

    // 두 날짜 간의 차이를 밀리초로 계산
    const timeDifference: number = createDate.getTime() - birthDate.getTime();

    // 밀리초를 일 단위로 변환 (1일 = 24시간 * 60분 * 60초 * 1000밀리초)
    const daysDifference: number = Math.floor(
      timeDifference / (1000 * 60 * 60 * 24)
    );

    return daysDifference;
  };

  const filteredReport = data?.data.filter((report) => {
    const date = new Date(report.createAt);
    const createdDate = date.getFullYear();
    return year === createdDate;
  });

  return (
    <ReportListWrapper>
      {filteredReport?.length === 0 ? (
        <p>생성된 분석 보고서가 없습니다.</p>
      ) : (
        filteredReport?.map((report) => (
          <ReportContainer
            key={report.id}
            onClick={() => onReportSelect(report.id)}
          >
            <ImageContainer>
              <BookImage src={ReportCover} alt="report img" />
              <DaysText>
                {' '}
                {calculateDays(report.createAt)} <br /> days
              </DaysText>
            </ImageContainer>

            <LabelContainer>
              <TitleLabel>
                {name} 성장 분석 보고서
              </TitleLabel>
              <DateLabel>
                <DateContainer>
                  <DateIcon />
                  <Typhography size="sm" color="greyScale" shade="800">
                    생성일 : {report.createAt.slice(0, 10)}
                  </Typhography>
                </DateContainer>
              </DateLabel>
            </LabelContainer>
          </ReportContainer>
        ))
      )}
    </ReportListWrapper>
  );
};

export default ReportList;
