import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import adult from '../../assets/images/adult.png';
import boy from '../../assets/images/boy.png';
import girl from '../../assets/images/girl.png';
import video from '../../assets/Test.mp4';
import { TimelineDiary } from '../../components/Diary/TimelineDiary/TimelineDiary';
import Button from '../../components/common/Button';
import Tab from '../../components/common/Tab';
import useModal from '../../hooks/useModal';
import * as s from './style';

const DiaryText =
  '우리 아이가 요즘 혼자서 앉아 놀기 시작했어요. ' +
  '오늘은 손에 쥔 걸 입에 넣으며 세상을 탐구하는 모습이 너무 귀여웠어요. ' +
  '천천히 경험하며 커가는 모습이 뿌듯해요 ㅎㅎ';

export const BabyMain = () => {
  const [fileList, setFileList] = useState<File[]>([]);
  const [isInitialRender, setIsInitialRender] = useState<boolean>(true);

  const modal = useModal();
  const navigator = useNavigate();

  const handleModalDiary = () => {
    modal.push({
      children: (
        <s.ModalBackground>
          <s.CloseIconBox onClick={() => modal.pop()}>
            {s.CloseIcon}
          </s.CloseIconBox>
          <TimelineDiary fileList={fileList} DiaryText={DiaryText} />
        </s.ModalBackground>
      ),
      onClose: () => {},
      animation: 'center',
    });
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
      <s.WriteIconBox onClick={() => navigator('/baby/writing')}>
        {s.WriteIcon}
      </s.WriteIconBox>
      <Tab selected="1" size="md" color="primary">
        <Tab.Item value="1">타임라인</Tab.Item>
        <Tab.Item value="2">캘린더</Tab.Item>
      </Tab>
      <Button onClick={handleModalDiary}>모달형 일기</Button>
      <s.TimelineContainer>
        <s.Circle />
        <s.PostContainer>
          <TimelineDiary fileList={fileList} DiaryText={DiaryText} />
          <TimelineDiary fileList={fileList} DiaryText={DiaryText} />
        </s.PostContainer>
      </s.TimelineContainer>
    </>
  );
};
export default BabyMain;
