import { useRoute, useRouter } from 'vue-router'

export function useHandleAuth<T>() {
  const route = useRoute()
  const router = useRouter()

  const handleAuth = async (fn: () => T): Promise<void> => {
    const isAuthenticated = localStorage.getItem('token')
    if (isAuthenticated) {
      await fn()
      return
    }

    const isOk = confirm('로그인이 필요한 요청입니다. 로그인하시겠습니까?')
    if (isOk)
      router.push({ path: '/auth/login', query: { redirect: route.fullPath } })
  }

  return { handleAuth }
}
