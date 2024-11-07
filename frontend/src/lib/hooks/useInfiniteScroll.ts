import { onMounted, onUnmounted, ref, watchEffect } from 'vue'
import PostApiFacade from '@/api/apiFacade/PostApiFacade'

export const useInfiniteScroll = () => {
  const { data: cardData, fetchNextPage, hasNextPage } = PostApiFacade.useFetchPostList()

  const showLoading = ref(false)
  const mergedCardData = ref<Array<PostResponse>>([])

  watchEffect(() => {
    const pages = cardData?.value?.pages
    if (pages) {
      mergedCardData.value = pages[0]?.currentPage === 0
        ? pages.flatMap(page => page.content)
        : []
    }
  })

  const loadMorePosts = async () => {
    if (hasNextPage) {
      showLoading.value = true
      setTimeout(() => showLoading.value = false, 3000)

      await fetchNextPage()
    }
  }

  const onScroll = () => {
    const { scrollY, innerHeight } = window
    const documentHeight = document.documentElement.scrollHeight

    if (scrollY + innerHeight >= documentHeight) {
      loadMorePosts()
    }
  }

  onMounted(() => window.addEventListener('scroll', onScroll))
  onUnmounted(() => window.removeEventListener('scroll', onScroll))

  return {hasNextPage, mergedCardData}
}
