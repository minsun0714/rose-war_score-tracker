<script setup lang="ts">
import PostCard from '../common/PostCard.vue'
import Search from '../../assets/Search.svg'
import { useRouter } from 'vue-router'
import { onMounted, ref, watchEffect } from 'vue'
import PostApiFacade from '@/api/apiFacade/PostApiFacade'
const router = useRouter()

const { data: cardData, fetchNextPage, hasNextPage } = PostApiFacade.useFetchPostList()

const loading = ref(false)
const showLoading = ref(false) // 로딩 표시 상태

const mergedCardData = ref([] as Array<PostResponse>)

watchEffect(() => {
  if (cardData?.value?.pages) {
    if (cardData?.value.pages[0]?.currentPage === 0) {
      mergedCardData.value = []
    }
    mergedCardData.value = cardData.value?.pages.flatMap(page => page.content)
  }
})

// 더 많은 포스트를 가져오는 함수
const loadMorePosts = async () => {
  if (!cardData.value?.pages[0].isLast && !loading.value) {
    loading.value = true
    showLoading.value = true // 로딩 시작 시 표시

    // 1초 후에 로딩 메시지를 숨김
    setTimeout(() => {
      showLoading.value = false // 1초 후 로딩 텍스트 숨김
    }, 20000)

    await fetchNextPage()
    loading.value = false
    console.log('Next page fetched')
  }
}

// 스크롤 이벤트 핸들러
const onScroll = () => {
  const scrollY = window.scrollY
  const windowHeight = window.innerHeight
  const documentHeight = document.documentElement.scrollHeight

  // 페이지 하단에 가까워지면 다음 페이지 로드
  if (scrollY + windowHeight >= documentHeight) {
    loadMorePosts()
  }
}

onMounted(() => {
  window.addEventListener('scroll', onScroll)
})
</script>

<template>
  <div>
    <div class="p-4 flex flex-row">
      <img :src="Search" alt="" />
      <input
        type="text"
        placeholder="검색어를 입력해주세요."
        class="border-b w-full p-2"
      />
    </div>


    <ul class="p-4 flex flex-col gap-y-4">
      <li
        v-for="card in mergedCardData"
        :key="card.postId"
        @click="router.push(`/board/${card.postId}`)"
      >
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
