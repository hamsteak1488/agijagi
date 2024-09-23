import { useEffect, useRef, useState } from 'react';
import adult from '../../assets/images/adult.png';
import boy from '../../assets/images/boy.png';
import girl from '../../assets/images/girl.png';
import video from '../../assets/Test.mp4';
import AppBar from '../../components/common/AppBar';
import Button from '../../components/common/Button';
import Typhography from '../../components/common/Typhography';
import MediaSlider from '../../components/Diary/MediaSlider/MediaSlider';
import {
  BottomArrow,
  Container,
  ContentContainer,
  DayContainer,
  DiaryTextArea,
  Icon,
  SelectBoxDiv,
} from './WritingDiary.styles';

export const WritingDiary = () => {
  const [fileList, setFileList] = useState<File[]>([]);
  const [isInitialRender, setIsInitialRender] = useState<boolean>(true);
  const textAreaRef = useRef<HTMLTextAreaElement | null>(null);

  //todo
  const handleSubmit = () => {
    console.log(textAreaRef.current?.value);
    console.log(fileList);
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
    setIsInitialRender(true);
  }, []);

  return (
    <>
      <Container>
        <AppBar>
          <Button color="tertiary" size="md">
            <Typhography>닫기</Typhography>
          </Button>
          <AppBar.Title>일기 쓰기</AppBar.Title>
          <Button color="secondary" size="md" onClick={handleSubmit}>
            <Typhography>쓰기</Typhography>
          </Button>
        </AppBar>
        <div>
          <DayContainer>
            <Typhography size="3xl" weight="bold">
              +24 days
            </Typhography>
            <SelectBoxDiv>
              <Typhography size="md" color="black">
                2024년 7월 1일
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
        </div>
      </Container>
    </>
  );
};

export default WritingDiary;
