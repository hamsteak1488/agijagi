import moment from 'moment';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getChild } from '../../apis/childrenApi';
import adult from '../../assets/images/adult.png';
import boy from '../../assets/images/boy.png';
import girl from '../../assets/images/girl.png';
import video from '../../assets/Test.mp4';
import { useQuery } from '@tanstack/react-query';
import { BabyProfileCard } from '../../components/BabyMain/BabyProfileCard/BabyProfileCard';
import { ScheduleCard } from '../../components/BabyMain/ScheduleCard/ScheduleCard';
import FullCalendar from '../../components/common/FullCalendar';
import Tab from '../../components/common/Tab';
import { TimelineDiary } from '../../components/Diary/TimelineDiary/TimelineDiary';
import useModal from '../../hooks/useModal';
import { BabyResponse } from '../../types/user';
import * as s from './style';
import useChildStore from '../../stores/useChlidStore';

export const BabyMain = () => {
  const [fileList, setFileList] = useState<File[]>([]);
  const [isInitialRender, setIsInitialRender] = useState<boolean>(true);
  const [tabMenu, setTabMenu] = useState<string>('1');

  const modal = useModal();
  const navigator = useNavigate();
  const { childId } = useChildStore();

  const handleTab = (menu: string) => {
    setTabMenu(menu);
  };

  const { data: child } = useQuery<BabyResponse>({
    queryKey: ['child', childId],
    queryFn: () => {
      if (childId === 0)
        return Promise.reject(new Error('유효하지 않은 childId입니다.'));
      return getChild(childId);
    },
  });

  const data = [
    {
      date: '2024-09-30',
      fileList: fileList,
      DiaryText:
        '우리 아이가 요즘 혼자서 앉아 놀기 시작했어요. ' +
        '오늘은 손에 쥔 걸 입에 넣으며 세상을 탐구하는 모습이 너무 귀여웠어요. ' +
        '천천히 경험하며 커가는 모습이 뿌듯해요 ㅎㅎ',
    },
    {
      date: '2024-10-02',
      fileList: fileList,
      DiaryText:
        '우리 아이가 요즘 혼자서 앉아 놀기 시작했어요. ' +
        '오늘은 손에 쥔 걸 입에 넣으며 세상을 탐구하는 모습이 너무 귀여웠어요. ' +
        '천천히 경험하며 커가는 모습이 뿌듯해요 ㅎㅎ',
    },
  ];

  const renderTileContent = ({ date, view }: { date: Date; view: string }) => {
    const formattedDate = moment(date).format('YYYY-MM-DD');

    const matchedDate = data.find((item) => item.date === formattedDate);
    if (view === 'month' && matchedDate && matchedDate.fileList) {
      return (
        <img
          src={URL.createObjectURL(matchedDate.fileList[0])}
          alt="day-image"
          style={{ width: '100%', height: '100%' }}
        />
      );
    }
    return null;
  };

  const handleModalDiary = (selectedDate: string) => {
    const diaryData = data.find((item) => item.date === selectedDate);

    if (diaryData) {
      modal.push({
        children: (
          <s.ModalBackground>
            <s.CloseIconBox onClick={() => modal.pop()}>
              {s.CloseIcon}
            </s.CloseIconBox>
            <TimelineDiary
              date={diaryData.date}
              fileList={diaryData.fileList}
              DiaryText={diaryData.DiaryText}
            />
          </s.ModalBackground>
        ),
        onClose: () => {},
        animation: 'center',
      });
    }
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
      <BabyProfileCard child={child} />
      <ScheduleCard />
      <s.WriteIconBox onClick={() => navigator('/family/writing')}>
        {s.WriteIcon}
      </s.WriteIconBox>
      <s.TapWrapper>
        <Tab
          selected="1"
          size="md"
          color="primary"
          onChange={(value) => {
            handleTab(value);
          }}
        >
          <Tab.Item value="1">타임라인</Tab.Item>
          <Tab.Item value="2">캘린더</Tab.Item>
        </Tab>
      </s.TapWrapper>

      {tabMenu === '1' ? (
        <s.TimelineContainer>
          <s.Circle />
          <s.PostContainer>
            {data.map((item, index) => (
              <TimelineDiary
                key={index}
                date={item.date}
                fileList={item.fileList}
                DiaryText={item.DiaryText}
              />
            ))}
          </s.PostContainer>
        </s.TimelineContainer>
      ) : (
        <s.CalendarOutterContainer>
          <s.CalendarInnerContainer>
            <FullCalendar
              locale="kr"
              tileContent={renderTileContent}
              onClickDay={(value: Date) =>
                handleModalDiary(moment(value).format('YYYY-MM-DD'))
              }
            />
          </s.CalendarInnerContainer>
        </s.CalendarOutterContainer>
      )}
    </>
  );
};
export default BabyMain;
