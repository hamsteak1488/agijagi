import type { Record, RecordRequest } from '../types/record';

import { axiosInstance } from './axiosInstance';

export const getLatestRecords = () => {
  return axiosInstance.get<Record[]>('/children/records/latest?childId=1');
};

export const addRecord = (data: RecordRequest) => {
  return axiosInstance.post('/children/records', data);
};
