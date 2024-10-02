import axios from 'axios';

export const axiosInstance = axios.create({
  baseURL: 'https://api.password926.site/',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

axiosInstance.interceptors.request.use(
  async (config) => {
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response?.status === 401) {
      console.log('로그인이 필요합니다.');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);
