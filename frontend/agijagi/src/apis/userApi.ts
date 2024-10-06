import { UserData } from '../types/user';
import { axiosInstance } from './axiosInstance';

export const signUp = (user: UserData) => {
  return axiosInstance.post('/members', user);
};
