import { onMounted, onUnmounted, ref, watchEffect } from 'vue'
import PostApiFacade from '@/api/apiFacade/PostApiFacade'

export const useInfiniteScroll = () => {
  const { data: cardData, fetchNextPage, hasNextPage } = PostApiFacade.useFetchPostList()

  const mergedCardData = ref<Array<PostResponse>>([])

  watchEffect(() => {
    const pages = cardData?.value?.pages
    if (pages) {
      mergedCardData.value = pages[0]?.currentPage === 0
        ? pages.flatMap(page => page.content)
        : []
    }
  })

  const onScroll = async () => {
    const { scrollY, innerHeight } = window
    const documentHeight = document.documentElement.scrollHeight

    if (scrollY + innerHeight >= documentHeight) {
      await fetchNextPage()
    }
  }

  onMounted(() => window.addEventListener('scroll', onScroll))
  onUnmounted(() => window.removeEventListener('scroll', onScroll))

  return {hasNextPage, mergedCardData}
}
