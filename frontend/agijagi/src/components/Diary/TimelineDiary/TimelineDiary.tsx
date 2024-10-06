import { DiaryContent } from '../DiaryContent/DiaryContent';
import MediaSlider from '../MediaSlider';

import Typhography from '../../common/Typography';
import {
  Container,
  DateContainer,
  DateDiv,
  DayContainer,
} from './Timeline.styles';
import moment from 'moment';

interface TimelineDiaryProps {
  date: string;
  fileList: File[];
  DiaryText: string;
}

export const TimelineDiary = ({
  date,
  fileList,
  DiaryText,
}: TimelineDiaryProps) => {
  const formattedDate = moment(date).format('YYYY년 M월 D일');

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
            {formattedDate}
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
