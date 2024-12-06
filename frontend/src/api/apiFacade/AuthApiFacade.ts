import { useMutation, useQuery } from '@tanstack/vue-query'
import AuthService from '../services/AuthService'
import { useRouter } from 'vue-router'
import { queryClient } from '@/main'

class AuthApiFacade {
  // 유저, 인증 관련 메서드
  static useFetchUserInfo() {
    return useQuery({
      queryKey: ['user'],
      queryFn: () => AuthService.fetchUserInfo(),
      enabled: () => !!localStorage.getItem('token'),
    })
  }

  static useSignUp() {
    const router = useRouter()
    return useMutation({
      mutationFn: ({
        userId,
        password,
        passwordConfirm,
        name,
        nickname,
      }: SignUpRequest) =>
        AuthService.signUp({
          userId,
          password,
          passwordConfirm,
          name,
          nickname,
        }),
      onSuccess: async response => {
        const accessToken = response.accessToken
        localStorage.setItem('token', accessToken)
        const userInfo = await queryClient.fetchQuery({
          queryKey: ['user'],
          queryFn: () => AuthService.fetchUserInfo(),
        })
        queryClient.setQueryData(['user'], userInfo)
        router.push('/')
      },
    })
  }

  static useLogin() {
    const router = useRouter()
    return useMutation({
      mutationFn: ({ userId, password }: LoginRequest) =>
        AuthService.login({ userId, password }),
      onSuccess: async response => {
        const accessToken = response.accessToken
        localStorage.setItem('token', accessToken)
        const userInfo = await queryClient.fetchQuery({
          queryKey: ['user'],
          queryFn: () => AuthService.fetchUserInfo(),
        })
        queryClient.setQueryData(['user'], userInfo)
        router.push('/')
      },
    })
  }

  static useUpdateUser(userId: string, updatedUserInfo: UpdateUserInfoRequest) {
    return useMutation({
      mutationFn: () => AuthService.updateUserInfo(userId, updatedUserInfo),
    })
  }

  static useDeleteUser(userId: string) {
    return useMutation({ mutationFn: () => AuthService.deleteUserInfo(userId) })
  }
}

export default AuthApiFacade
