import { ScheduleData, ScheduleResponse } from '../types/schedule';

import { axiosInstance } from './axiosInstance';

interface AddScheduleProps {
  childId: number;
  data: ScheduleData;
}

interface DeleteScheduleProps {
  childId: number;
  scheduleId: number;
}

export const addSchedule = ({ childId, data }: AddScheduleProps) => {
  return axiosInstance.post(`/children/${childId}/schedules`, data);
};

export const getSchedules = (childId: number, start: string, end: string) => {
  return axiosInstance.get<ScheduleResponse[]>(
    `/children/${childId}/schedules?startDate=${start}&endDate=${end}`
  );
};

export const deleteSchedule = ({
  childId,
  scheduleId,
}: DeleteScheduleProps) => {
  return axiosInstance.delete(`/children/${childId}/schedules/${scheduleId}`);
};
