import type {
  RecordData,
  RecordRequest,
  RecordResponse,
} from '../types/record';

import { axiosInstance } from './axiosInstance';

export const getLatestRecords = () => {
  return axiosInstance.get<RecordData[]>('/children/records/latest?childId=1');
};

export const addRecord = (data: RecordRequest) => {
  return axiosInstance.post('/children/records', data);
};

export const getRecords = (childId: number, start: string, end: string) => {
  return axiosInstance.get<RecordResponse[]>(
    `/children/records?childId=${childId}&startDate=${start}&endDate=${end}`
  );
};

export const deleteRecord = (recordId: number) => {
  return axiosInstance.delete(`/children/records/${recordId}`);
};
