import moment from 'moment';
import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { addDiary } from '../../apis/diaryApi';
import adult from '../../assets/images/adult.png';
import boy from '../../assets/images/boy.png';
import girl from '../../assets/images/girl.png';
import video from '../../assets/Test.mp4';
import AppBar from '../../components/common/AppBar';
import Button from '../../components/common/Button';
import FullCalendar from '../../components/common/FullCalendar';
import Typhography from '../../components/common/Typography';
import MediaSlider from '../../components/Diary/MediaSlider';
import useModal from '../../hooks/useModal';
import useChildStore from '../../stores/useChlidStore';
import {
  BottomArrow,
  Container,
  ContentContainer,
  DateContainer,
  DayContainer,
  DiaryTextArea,
  Icon,
  ModalBackground,
  SelectBoxDiv,
} from './WritingDiary.styles';

export const WritingDiary = () => {
  const [fileList, setFileList] = useState<File[]>([]);
  const [isInitialRender, setIsInitialRender] = useState<boolean>(true);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());
  const textAreaRef = useRef<HTMLTextAreaElement | null>(null);

  const navigator = useNavigate();
  const modal = useModal();

  const { childId } = useChildStore();

  const handleDate = (value: Date) => {
    setSelectedDate(value);
    modal.pop();
  };

  const selectDate = () => {
    modal.push({
      children: (
        <ModalBackground>
          <FullCalendar
            onClickDay={handleDate}
            maxDate={moment().toDate()}
          ></FullCalendar>
        </ModalBackground>
      ),
    });
  };

  //todo
  const handleSubmit = () => {
    const diary = {
      childId: childId,
      content: textAreaRef.current ? textAreaRef.current.value : '',
      mediaList: fileList,
    };
    console.log(diary);
    addDiary(diary)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIsInitialRender(false);
    if (e.target.files) {
      const selectedFiles = Array.from(e.target.files);
      setFileList((prevFiles) => [...prevFiles, ...selectedFiles]);
    }
  };

  const handleDelete = (index: number) => {
    const updatedFileList = fileList.filter((_, i) => i !== index);
    setFileList(updatedFileList);
  };

  return (
    <>
      <Container>
        <AppBar>
          <Button color="tertiary" size="md" onClick={() => navigator(-1)}>
            <Typhography>닫기</Typhography>
          </Button>
          <AppBar.Title>일기 쓰기</AppBar.Title>
          <Button color="secondary" size="md" onClick={handleSubmit}>
            <Typhography>쓰기</Typhography>
          </Button>
        </AppBar>
        <DayContainer>
          <DateContainer>
            <Typhography size="3xl" weight="bold">
              Day +
            </Typhography>
            <Typhography size="3xl" weight="bold" color="primary">
              397
            </Typhography>
          </DateContainer>
          <SelectBoxDiv onClick={selectDate}>
            <Typhography size="md" color="black">
              {moment(selectedDate).format('YYYY년 MM월 DD일')}
            </Typhography>
            <Icon>{BottomArrow}</Icon>
          </SelectBoxDiv>
        </DayContainer>
        <ContentContainer>
          <MediaSlider
            fileList={fileList}
            isWriteMode={true}
            handleUpload={handleUpload}
            handleDelete={handleDelete}
            isInitialRender={isInitialRender}
          ></MediaSlider>
          <DiaryTextArea
            ref={textAreaRef}
            placeholder="오늘의 이야기를 써주세요!"
          />
        </ContentContainer>
      </Container>
    </>
  );
};

export default WritingDiary;
