import { useEffect, useState } from 'react';
import axios from 'axios';
import AppBar from '../../components/common/AppBar';
import Button from '../../components/common/Button';
import Typhography from '../../components/common/Typhography';
import MediaInput from '../../components/Diary/MediaInput/MediaInput';
import MediaSlider from '../../components/Diary/MediaSlider/MediaSlider';
import { Container, DiaryTextArea } from './WritingDiary.styles';
import boy from '../../assets/images/boy.png';
import girl from '../../assets/images/girl.png';
import adult from '../../assets/images/adult.png';
import video from '../../assets/Test.mp4';

export const WritingDiary = () => {
  const [fileList, setFileList] = useState<File[]>([]);

  const handleWheel = (e) => {
    e.preventDefault(); // 기본 세로 스크롤 방지
    const container = scrollContainerRef.current;
    container.scrollLeft += e.deltaY; // 세로 스크롤(deltaY)을 가로 스크롤로 변환
  };

  useEffect(() => {
    const convertToFile = async (imageUrl: string, fileName: string) => {
      const response = await fetch(imageUrl);
      const blob = await response.blob();
      const file = new File([blob], fileName, { type: blob.type });
      return file;
    };

    const loadImages = async () => {
      const boyFile = await convertToFile(boy, 'boy.png');
      const girlFile = await convertToFile(girl, 'girl.png');
      const adultFile = await convertToFile(adult, 'adult.png');
      const videoFile = await convertToFile(video, 'Test.mp4');
      setFileList([boyFile, girlFile, adultFile, videoFile]);
    };

    loadImages();
  }, []);

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
        <MediaSlider fileList={fileList} isWriteMode={true}></MediaSlider>
        <DiaryTextArea placeholder="오늘의 이야기를 써주세요!" />
      </Container>
    </>
  );
};

export default WritingDiary;
