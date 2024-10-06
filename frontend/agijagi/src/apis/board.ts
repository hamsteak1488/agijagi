import { axiosInstance } from './axiosInstance';

interface WriteArticleProps {
  data: FormData;
}

export const writeArticle = ({ data }: WriteArticleProps) => {
  return axiosInstance.post(`/posts`, data, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
};
