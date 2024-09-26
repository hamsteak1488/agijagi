import { DiaryContent } from '../DiaryContent/DiaryContent';
import MediaSlider from '../MediaSlider';

import Typhography from '../../common/Typography';
import {
  Container,
  DateContainer,
  DateDiv,
  DayContainer,
} from './Timeline.styles';

interface TimelineDiaryProps {
  fileList: File[];
  DiaryText: string;
}

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
