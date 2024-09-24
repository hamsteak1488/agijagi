import MediaSlider from '../MediaSlider';
import { DiaryContent } from '../DiaryContent/DiaryContent';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import Typhography from '../../common/Typography';
import theme from '../../../styles/theme';

interface TimelineDiaryProps {
  fileList: File[];
  DiaryText: string;
}

export const DateContainer = styled.div(
  () => css`
    display: flex;
    flex-direction: row;
    gap: 0.25rem;
  `
);
export const Container = styled.div(
  () => css`
    display: flex;
    flex-direction: column;
    align-items: center;
  `
);
export const DayContainer = styled.div(
  () => css`
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  `
);

export const DateDiv = styled.div(
  () => css`
    display: flex;
    flex-direction: row;
    align-items: center;
    cursor: pointer;
  `
);

export const TimelineDiary = ({ fileList, DiaryText }: TimelineDiaryProps) => {
  return (
    <Container>
      <DayContainer>
        <DateContainer>
          <Typhography size="xl" weight="bold">
            Day +
          </Typhography>
          <Typhography size="xl" weight="bold" color="primary">
            397
          </Typhography>
        </DateContainer>
        <DateDiv>
          <Typhography size="md" color="black">
            2024년 7월 1일
          </Typhography>
        </DateDiv>
      </DayContainer>
      <MediaSlider
        fileList={fileList}
        isWriteMode={false}
        handleDelete={() => {}}
        handleUpload={() => {}}
        isInitialRender={false}
      ></MediaSlider>
      <DiaryContent DiaryText={DiaryText}></DiaryContent>
    </Container>
  );
};
