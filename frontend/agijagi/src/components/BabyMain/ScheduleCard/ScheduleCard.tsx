import styled from '@emotion/styled';
import moment from 'moment';
import 'moment/locale/ko';
import defaultImg from '../../../assets/images/adult.png';
import theme from '../../../styles/theme';
import Button from '../../common/Button';
import Typhography from '../../common/Typography';

export const Container = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 1rem;
  margin-bottom: 1rem;
`;

export const GridCard = styled.div`
  display: grid;
  grid-template-columns: 1fr 3fr;
  width: 90%;
  height: 100%;
  min-height: 165px;
  max-width: 500px;
  max-height: 200px;
  border-radius: 25px;
  background-color: ${theme.color.tertiary[100]};
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
`;

export const PhotoSection = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  grid-column: 1/2;
  padding-left: 1rem;
`;

export const ContentSection = styled.div`
  grid-column: 2/3;
  padding-left: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
`;

export const DateContainer = styled.div`
  display: flex;
  justify-content: space-around;
  gap: 0.5rem;
  margin-top: 1rem;
`;

export const SchedualContainer = styled.div`
  width: 90%;
  margin-top: 0.5rem;
  text-align: center;
`;

export const ButtonContainer = styled.div`
  width: 80%;
  margin-top: 0.5rem;
`;

export const Photo = styled.img`
  width: 100%;
`;

export const ScheduleCard = () => {
  const formattedDate = moment().locale('ko').format('MM월 DD일 (ddd)');

  return (
    <Container>
      <GridCard>
        <PhotoSection>
          <Photo src={defaultImg} />
        </PhotoSection>
        <ContentSection>
          <DateContainer>
            <Typhography size="sm" weight="bold">
              오늘의 일정
            </Typhography>
            <Typhography size="sm" color="primary" weight="bold">
              {formattedDate}
            </Typhography>
          </DateContainer>
          <SchedualContainer>
            <Typhography size="sm" weight="bold">
              오후 6시
            </Typhography>
            <Typhography size="md" color="secondary" shade="A700">
              재훈의원 가기
            </Typhography>
            <Typhography size="xs" color="greyScale" shade="500">
              외 다른 일정 4개
            </Typhography>
          </SchedualContainer>

          <ButtonContainer>
            <Button size="sm" fullWidth={true}>
              일정 확인하기
            </Button>
          </ButtonContainer>
        </ContentSection>
      </GridCard>
    </Container>
  );
};
