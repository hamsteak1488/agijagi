import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { TimelineDiary } from '../../components/Diary/TimelineDiary/TimelineDiary';
import adult from '../../assets/images/adult.png';
import boy from '../../assets/images/boy.png';
import girl from '../../assets/images/girl.png';
import video from '../../assets/Test.mp4';

import styled from '@emotion/styled';
import { css } from '@emotion/react';
import theme from '../../styles/theme';
import Tab from '../../components/common/Tab';
import Button from '../../components/common/Button';
import useModal from '../../hooks/useModal';
import { DelIcon } from '../../components/Diary/MediaSlider/MediaSlider.styles';

const TimelineContainer = styled.div(
  () => css`
    position: relative;
    padding: 0.25rem 1rem 0.25rem 1.25rem;
    margin-left: 0.75rem;
    margin-right: 1rem;
    margin-top: 2rem;
    border-left: 4px dashed ${theme.color.tertiary[700]};
  `
);

const Circle = styled.div(
  () => css`
    position: absolute;
    width: 1rem;
    height: 1rem;
    background-color: ${theme.color.tertiary[700]};
    border-radius: 100rem;
    left: -10px;
    top: -10px;
  `
);

const PostContainer = styled.div(
  () => css`
    display: flex;
    flex-direction: column;
    gap: 2rem;
  `
);

const ModalBackground = styled.div(
  () => css`
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    background-color: ${theme.color.primary[50]};
    margin: 1rem;
    padding: 2.5rem 2rem 2rem 2rem;
    border-radius: 0.5rem;
  `
);

const WriteIconBox = styled.div(
  () => css`
    position: absolute;
    width: 24px;
    height: 24px;
    padding: 0.75rem;
    right: 8px;
    bottom: 24px;
    background-color: ${theme.color.primary[400]};
    color: #fff;
    border-radius: 50rem;
    z-index: 30;
  `
);
const CloseIconBox = styled.div(
  () => css`
    position: absolute;
    width: 30px;
    height: 30px;
    right: 8px;
    top: 8px;
  `
);

const CloseIcon = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 24 24"
    strokeWidth={1.5}
    stroke="currentColor"
    className="size-6"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      d="M6 18 18 6M6 6l12 12"
    />
  </svg>
);

const WriteIcon = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 24 24"
    strokeWidth={2}
    stroke="currentColor"
    className="size-6"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
    />
  </svg>
);

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
        <ModalBackground>
          <CloseIconBox onClick={() => modal.pop()}>{CloseIcon}</CloseIconBox>
          <TimelineDiary fileList={fileList} DiaryText={DiaryText} />
        </ModalBackground>
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
      <WriteIconBox onClick={() => navigator('/baby/writing')}>
        {WriteIcon}
      </WriteIconBox>
      <Tab selected="1" size="md" color="primary">
        <Tab.Item value="1">타임라인</Tab.Item>
        <Tab.Item value="2">캘린더</Tab.Item>
      </Tab>
      <Button onClick={handleModalDiary}>모달형 일기</Button>
      <TimelineContainer>
        <Circle />
        <PostContainer>
          <TimelineDiary fileList={fileList} DiaryText={DiaryText} />
          <TimelineDiary fileList={fileList} DiaryText={DiaryText} />
        </PostContainer>
      </TimelineContainer>
    </>
  );
};
export default BabyMain;
