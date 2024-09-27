import type { Record } from '../types/record';

import { axiosInstance } from './axiosInstance';

export const getLatestRecords = () => {
  return axiosInstance.get<Record>('/children/records/latest');
};
