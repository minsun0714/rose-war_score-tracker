<script setup lang="ts">
import PostCard from '../common/PostCard.vue'
import Search from '../../assets/Search.svg'
import { useRouter } from 'vue-router'
import { onMounted, onUnmounted, ref, watchEffect } from 'vue'
import PostApiFacade from '@/api/apiFacade/PostApiFacade'

const router = useRouter()
const { data: cardData, fetchNextPage, hasNextPage } = PostApiFacade.useFetchPostList()

const loading = ref(false)
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

// 포스트 로딩 함수
const loadMorePosts = async () => {
  if (hasNextPage && !loading.value) {
    loading.value = true
    showLoading.value = true
    setTimeout(() => showLoading.value = false, 20000)

    await fetchNextPage()
    loading.value = false
  }
}

// 페이지 하단 스크롤 이벤트 핸들러
const onScroll = () => {
  const { scrollY, innerHeight } = window
  const documentHeight = document.documentElement.scrollHeight

  if (scrollY + innerHeight >= documentHeight) {
    loadMorePosts()
  }
}

onMounted(() => window.addEventListener('scroll', onScroll))
onUnmounted(() => window.removeEventListener('scroll', onScroll))

const navigateToPost = (postId: string) => router.push(`/board/${postId}`)
</script>

<template>
  <div>
    <div class="p-4 flex flex-row">
      <img :src="Search" alt="검색" />
      <input type="text" placeholder="검색어를 입력해주세요." class="border-b w-full p-2" />
    </div>

    <ul class="p-4 flex flex-col gap-y-4">
      <li v-for="card in mergedCardData" :key="card.postId" @click="navigateToPost(card.postId)">
        <PostCard :card="card" />
      </li>
      <div v-if="showLoading && hasNextPage" class="text-center">
        <div class="spinner-border animate-spin inline-block w-8 h-8 border-4 border-solid rounded-full border-t-transparent border-gray-600" role="status">
          <span class="sr-only">로딩 중...</span>
        </div>
      </div>
    </ul>
  </div>
</template>
