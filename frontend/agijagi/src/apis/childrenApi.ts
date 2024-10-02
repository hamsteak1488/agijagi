import { BabyResponse } from '../types/user';
import { axiosInstance } from './axiosInstance';

export const getChild = async (childId: number): Promise<BabyResponse> => {
  const response = await axiosInstance.get(`/children/${childId}`);
  return response.data;
};
