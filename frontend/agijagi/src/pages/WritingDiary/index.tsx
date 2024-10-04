import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';
import moment from 'moment';
import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getChild } from '../../apis/childApi';
import { addDiary, editDiary, getAllDiaries } from '../../apis/diaryApi';
import AppBar from '../../components/common/AppBar';
import Button from '../../components/common/Button';
import FullCalendar from '../../components/common/FullCalendar';
import Typhography from '../../components/common/Typography';
import MediaSlider from '../../components/Diary/MediaSlider';
import useModal from '../../hooks/useModal';
import useChildStore from '../../stores/useChlidStore';
import { DiaryResponse } from '../../types/diary';
import { BabyResponse } from '../../types/user';
import { extractIdFromUrl } from '../../utils/getIdFromUrl';
import videoIcon from '../../assets/images/diary/videoIcon.jpeg';
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
  const [urlType, setUrlType] = useState<string[]>([]);
  const [urlList, setUrlList] = useState<string[]>([]);
  const [removeMediaIdList, setRemoveMediaIdList] = useState<string[]>([]);
  const [isInitialRender, setIsInitialRender] = useState<boolean>(true);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());
  const [selectedDiary, setSelectedDiary] = useState<DiaryResponse | null>(
    null
  );
  const textAreaRef = useRef<HTMLTextAreaElement | null>(null);

  const navigator = useNavigate();
  const modal = useModal();
  const queryClient = useQueryClient();

  const { childId } = useChildStore();

  const day = moment(selectedDate);

  const { data: diaries = [] } = useQuery<DiaryResponse[]>({
    queryKey: ['diaries', childId],
    queryFn: () => {
      return getAllDiaries(childId);
    },
  });

  const { data: child } = useQuery<BabyResponse>({
    queryKey: ['child', childId],
    queryFn: () => {
      if (childId === 0)
        return Promise.reject(new Error('유효하지 않은 childId입니다.'));
      return getChild(childId);
    },
  });

  useEffect(() => {
    setSelectedDate(new Date());
    const formattedDate = moment(selectedDate).format('YYYY-MM-DD');
    const selectedDiary =
      diaries.find(
        (item) => moment(item.wroteAt).format('YYYY-MM-DD') === formattedDate
      ) || null;

    setSelectedDiary(selectedDiary);
    setUrlList(selectedDiary ? selectedDiary.mediaUrls : []);
    setUrlList(selectedDiary ? selectedDiary.mediaUrls : []);
  }, []);

  useEffect(() => {
    const formattedDate = moment(selectedDate).format('YYYY-MM-DD');
    const diary =
      diaries.find(
        (item) => moment(item.wroteAt).format('YYYY-MM-DD') === formattedDate
      ) || null;

    setSelectedDiary(diary);
    setUrlType(diary ? diary.mediaTypes : []);
    setUrlList(diary ? diary.mediaUrls : []);

    if (textAreaRef.current && !diary) {
      textAreaRef.current.value = '';
    }

    if (textAreaRef.current && diary) {
      textAreaRef.current.value = diary?.content ? diary.content : '';
    }
  }, [selectedDate, diaries]);

  const differenceIndays = day.diff(
    moment(child?.birthday, 'YYYY-MM-DD'),
    'days'
  );

  const handleDate = (value: Date) => {
    setSelectedDate(value);
    const formattedDate = moment(selectedDate).format('YYYY-MM-DD');
    const selectedDiary =
      diaries.find(
        (item) => moment(item.wroteAt).format('YYYY-MM-DD') === formattedDate
      ) || null;

    setSelectedDiary(selectedDiary);
    modal.pop();
  };

  const selectDate = () => {
    modal.push({
      children: (
        <ModalBackground>
          <FullCalendar
            onClickDay={handleDate}
            maxDate={moment().toDate()}
            tileContent={({ date, view }: { date: Date; view: string }) => {
              const formattedDate = moment(date).format('YYYY-MM-DD');
              const matchedDate = diaries.find(
                (item) =>
                  moment(item.wroteAt).format('YYYY-MM-DD') === formattedDate
              );
              if (
                view === 'month' &&
                matchedDate &&
                matchedDate.mediaUrls.length > 0
              ) {
                return (
                  <img
                    src={
                      matchedDate.mediaTypes[0] === 'image'
                        ? matchedDate.mediaUrls[0]
                        : videoIcon
                    }
                    alt="day-image"
                    style={{ width: '100%', height: '100%' }}
                  />
                );
              }
              return null;
            }}
          ></FullCalendar>
        </ModalBackground>
      ),
    });
  };

  const handleEdit = () => {
    if (selectedDiary) {
      const request = {
        storyId: selectedDiary.id,
        content: textAreaRef.current ? textAreaRef.current.value : '',
        removeMediaIdList: removeMediaIdList,
        newMediaList: fileList,
      };

      editDiary(request)
        .then((response) => {
          navigator('/family');
        })
        .catch((error) => {
          if (axios.isAxiosError(error)) {
            alert(error.response?.data.message);
          }
        });
    }
  };

  const handleSubmit = () => {
    const diary = {
      childId: childId,
      content: textAreaRef.current ? textAreaRef.current.value : '',
      date: moment(selectedDate.toISOString()).format('YYYY-MM-DD'),
      mediaList: fileList,
    };
    addDiary(diary)
      .then((response) => {
        navigator('/family');
      })
      .catch((error) => {
        if (axios.isAxiosError(error)) {
          alert(error.response?.data.message);
        }
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

  const handleUrlDelete = (index: number) => {
    const updatedUrlList = urlList?.filter((_, i) => i !== index);
    const updatedUrlTypes = urlType?.filter((_, i) => i !== index);
    setRemoveMediaIdList((prevList) => {
      const newId = extractIdFromUrl(urlList[index]);
      return newId ? [...prevList, newId] : prevList;
    });
    setUrlList(updatedUrlList ? updatedUrlList : []);
    setUrlType(updatedUrlTypes ? updatedUrlTypes : []);
  };

  return (
    <>
      <Container>
        <AppBar>
          <Button
            color="tertiary"
            size="md"
            onClick={() => {
              navigator(-1);
            }}
          >
            <Typhography>닫기</Typhography>
          </Button>
          <AppBar.Title>
            {selectedDiary ? '일기 수정' : '일기 쓰기'}
          </AppBar.Title>
          <Button
            color="secondary"
            size="md"
            onClick={selectedDiary ? handleEdit : handleSubmit}
          >
            <Typhography>{selectedDiary ? '수정' : '쓰기'}</Typhography>
          </Button>
        </AppBar>
        <DayContainer>
          <DateContainer>
            <Typhography size="3xl" weight="bold">
              {differenceIndays > 0 ? 'Day +' : 'Day -'}
            </Typhography>
            <Typhography size="3xl" weight="bold" color="primary">
              {differenceIndays ? Math.abs(differenceIndays) : '0'}
            </Typhography>
          </DateContainer>
          <SelectBoxDiv onClick={selectDate}>
            <Typhography size="md" color="black">
              {selectedDate
                ? moment(selectedDate).format('YYYY년 MM월 DD일')
                : '2000년 01월 1일'}
            </Typhography>
            <Icon>{BottomArrow}</Icon>
          </SelectBoxDiv>
        </DayContainer>
        <ContentContainer>
          <MediaSlider
            fileList={fileList}
            urlType={urlType}
            urlList={urlList}
            isWriteMode={true}
            handleUpload={handleUpload}
            handleDelete={handleDelete}
            handleUrlDelete={handleUrlDelete}
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
