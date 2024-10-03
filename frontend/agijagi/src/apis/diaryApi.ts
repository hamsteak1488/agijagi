import { DiaryRequest, DiaryResponse } from '../types/diary';
import { BabyResponse } from '../types/user';
import { axiosInstance } from './axiosInstance';

export const getAllDiaries = async (
  childId: number
): Promise<DiaryResponse[]> => {
  const response = await axiosInstance.get(`/diaries?childId=${childId}`);
  console.log(response);
  return response.data;
};

export const addDiary = async (diary: DiaryRequest): Promise<number> => {
  const formData = new FormData();

  formData.append('childId', diary.childId.toString());
  formData.append('content', diary.content);
  diary.mediaList.forEach((file, index) => {
    formData.append(`mediaList`, file); // 파일 추가
  });

  const response = await axiosInstance.post(`/diaries`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response.status;
};
