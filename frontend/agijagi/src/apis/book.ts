import { axiosInstance } from './axiosInstance';

interface StoryBook {
  childId: number;
  title: string;
  startDate: string;
  endDate: string;
  coverImage: string;
}

interface PostStoryBookProps {
  data: StoryBook;
}

export interface StoryBookDetail {
  id: number;
  childId: number;
  title: string;
  startDate: string;
  endDate: string;
  createdAt: string;
  coverImageUrl: string;
}

export interface StoryBookPage {
  img: string | undefined;
  id: number;
  storyId: number;
  pageNumber: number;
  content: string;
}

interface StoryBookProps {
  storyId: number;
}

export const postStoryBook = ({ data }: PostStoryBookProps) => {
  return axiosInstance.post('/stories', data);
};

export const getStoryBookList = (childId: number) => {
  return axiosInstance.get<StoryBookDetail[]>(`stories?childId=${childId}`);
};

export const getStoryBook = (storyId: number) => {
  return axiosInstance.get<StoryBookDetail>(`stories/${storyId}`);
};

export const getStoryBookPages = (storyId: number) => {
  return axiosInstance.get<StoryBookPage[]>(`stories/${storyId}/pages`);
};

export const deleteStoryBook = ({ storyId }: StoryBookProps) => {
  return axiosInstance.delete(`stories/${storyId}`);
};
