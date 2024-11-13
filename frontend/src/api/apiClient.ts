import { LocalStorageTokenUtil } from '@/lib/utils';
import axios from 'axios';

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
});

// 요청 Interceptor
api.interceptors.request.use(
    (config) => {
        const token = LocalStorageTokenUtil.getToken();
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 응답 Interceptor
api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {
            // 인증 오류 시 처리
            console.error('Unauthorized, redirecting to login...');
        }
        return Promise.reject(error);
    }
);

export default api;
