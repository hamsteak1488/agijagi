import { DiaryRequest, DiaryResponse } from '../types/diary';
import { BabyResponse } from '../types/user';
import { axiosInstance } from './axiosInstance';

export const getAllDiaries = async (
  childId: number
): Promise<DiaryResponse[]> => {
  const response = await axiosInstance.get(`/diaries?childId=${childId}`);
  return response.data;
};

export const addDiary = async (diary: DiaryRequest) => {
  const formData = new FormData();

  formData.append('childId', diary.childId.toString());
  formData.append('content', diary.content);
  formData.append('wroteAt', diary.date);
  diary.mediaList.forEach((file, index) => {
    formData.append(`mediaList`, file); // 파일 추가
  });

  const response = await axiosInstance.post(`/diaries`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response;
};
