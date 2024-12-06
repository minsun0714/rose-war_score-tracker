import axios, {
  type InternalAxiosRequestConfig,
  type AxiosResponse,
} from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  withCredentials: true, // 쿠키 전송 허용
})

// 토큰 갱신용 Axios 인스턴스
const refreshApi = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  withCredentials: true, // 쿠키 전송 허용
})

// 상태 관리
let isRefreshing = false
let refreshSubscribers: ((token: string) => void)[] = []

// 새로운 Access Token 발급 후 대기 중인 요청 처리
const onRefreshed = (token: string) => {
  refreshSubscribers.forEach(callback => callback(token))
  refreshSubscribers = []
}

// 요청 인터셉터
api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('token')
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => Promise.reject(error),
)

// 응답 인터셉터
api.interceptors.response.use(
  (response: AxiosResponse) => response,
  async error => {
    const originalRequest = error.config

    // Access Token 만료 시 처리
    if (error.response?.status === 401 && !originalRequest._retry) {
      if (isRefreshing) {
        return new Promise(resolve => {
          refreshSubscribers.push(token => {
            if (originalRequest.headers) {
              originalRequest.headers.Authorization = `Bearer ${token}`
            }
            resolve(api(originalRequest))
          })
        })
      }

      originalRequest._retry = true
      isRefreshing = true

      try {
        // Refresh Token으로 Access Token 재발급
        const response = await refreshApi.post<{
          accessToken: string
        }>('/api/auth/refresh')

        const { accessToken } = response.data

        // 새 토큰 저장
        localStorage.setItem('token', accessToken)
        isRefreshing = false

        // 대기 중인 요청 처리
        onRefreshed(accessToken)

        // 원래 요청 재시도
        if (originalRequest.headers) {
          originalRequest.headers.Authorization = `Bearer ${accessToken}`
        }
        return api(originalRequest)
      } catch (refreshError) {
        isRefreshing = false
        refreshSubscribers = []
        console.error('Failed to refresh token:', refreshError)

        // Refresh Token 재발급 실패 시 로그아웃 처리
        localStorage.removeItem('token')
        window.location.href = '/login'
        return Promise.reject(refreshError)
      }
    }

    return Promise.reject(error)
  },
)

export default api
