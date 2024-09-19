import AppBar from '../../components/common/AppBar';
import Button from '../../components/common/Button';
import Typhography from '../../components/common/Typhography';
import MediaInput from '../../components/Diary/MediaInput/MediaInput';
import { Container } from './WritingDiary.styles';

export const WritingDiary = () => {
  return (
    <>
      <Container>
        <AppBar>
          <Button color="tertiary" size="md">
            <Typhography>닫기</Typhography>
          </Button>
          <AppBar.Title>일기 쓰기</AppBar.Title>
          <Button color="secondary" size="md">
            <Typhography>쓰기</Typhography>
          </Button>
        </AppBar>

        <MediaInput></MediaInput>
      </Container>
    </>
  );
};

export default WritingDiary;
