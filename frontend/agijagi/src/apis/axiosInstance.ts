import axios from 'axios';

export const axiosInstance =
  process.env.NODE_ENV === 'development'
    ? axios.create({
        baseURL: 'https://api.password926.site/',
        headers: {
          'header-login-member': localStorage.getItem('memberId'),
        },
        withCredentials: true,
      })
    : axios.create({
        baseURL: 'https://api.password926.site/',
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
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);
