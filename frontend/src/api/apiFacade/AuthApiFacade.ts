import { useMutation, useQuery } from '@tanstack/vue-query'
import AuthService from '../services/AuthService'
import { useRouter } from 'vue-router'
import { queryClient } from '@/main'
import { LocalStorageTokenUtil } from '@/lib/utils'

class AuthApiFacade {
  // 유저, 인증 관련 메서드
  static useFetchUserInfo() {
    return useQuery({
      queryKey: ['user'],
      queryFn: () => AuthService.fetchUserInfo(),
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
        LocalStorageTokenUtil.setToken(accessToken)
        queryClient.refetchQueries({queryKey: ["user"]})
        router.push('/')
      },
    })
  }

  static useLogin() {
    const router = useRouter()
    return useMutation({
      mutationFn: ({ userId, password }: LoginRequest) =>
        AuthService.login({ userId, password }),
      onSuccess: response => {
        const accessToken = response.accessToken
        LocalStorageTokenUtil.setToken(accessToken)
        router.push('/')
      },
    })
  }

  static useUpdateUser(updatedUserInfo: UpdateUserInfoRequest) {
    return useMutation({
      mutationFn: () => AuthService.updateUserInfo(updatedUserInfo),
    })
  }

  static useDeleteUser() {
    return useMutation({ mutationFn: () => AuthService.deleteUserInfo() })
  }
}

export default AuthApiFacade
