import axios from 'axios';

export const axiosInstance = axios.create({
  baseURL: 'https://api.password926.site/',
  headers: {
    'header-login-member': 1,
  },
});
