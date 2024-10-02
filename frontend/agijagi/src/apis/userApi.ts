import { UserData, MemberResponse } from '../types/user';
import { axiosInstance } from './axiosInstance';
import { BabyResponse } from '../types/user';

export const signUp = (user: UserData) => {
  return axiosInstance.post('/members', user);
};

export const getAllChildren = async (): Promise<BabyResponse[]> => {
  const response = await axiosInstance.get('/children');
  return response.data;
};

export const getUserInfo = async (
  memberId: number
): Promise<MemberResponse> => {
  const response = await axiosInstance.get(`/members/${memberId}`);
  return response.data;
};
