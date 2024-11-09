import { useMutation, useQuery } from '@tanstack/vue-query'
import AuthService from '../services/AuthService'
import { useRouter } from 'vue-router'

class AuthApiFacade {
  // 유저, 인증 관련 메서드
  static useFetchUserInfo(userId: string) {
    return useQuery({
      queryKey: ['user', userId],
      queryFn: () => AuthService.fetchUserInfo(userId),
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
      onSuccess: response => {
        const accessToken = response.accessToken
        localStorage.setItem('token', accessToken)
        router.push('/')
      },
    })
  }

  static useLogin(loginInfo: LoginRequest) {
    return useMutation({ mutationFn: () => AuthService.login(loginInfo) })
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
