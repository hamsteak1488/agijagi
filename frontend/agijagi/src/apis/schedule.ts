import { ScheduleData } from '../types/schedule';

import { axiosInstance } from './axiosInstance';

interface AddScheduleProps {
  childId: number;
  data: ScheduleData;
}

export const addSchedule = ({ childId, data }: AddScheduleProps) => {
  return axiosInstance.post(`/children/${childId}/schedules`, data);
};
