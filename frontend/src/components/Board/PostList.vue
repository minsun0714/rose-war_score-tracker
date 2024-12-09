<script setup lang="ts">
import PostCard from '../common/PostCard.vue'
import { useRouter } from 'vue-router'
import { useInfiniteScroll } from '@/lib/hooks/useInfiniteScroll'
import SearchInput from './SearchInput.vue'

const router = useRouter()

const { mergedCardData, hasNextPage } = useInfiniteScroll()

const navigateToPost = (postId: string) => postId && router.push(`/board/${postId}`)
</script>

<template>
  <div>
    <SearchInput />

    <ul class="p-4 flex flex-col gap-y-4">
      <li
        v-for="card in mergedCardData"
        :key="card.postId"
        @click="navigateToPost(card.postId)"
      >
        <PostCard :card="card" />
      </li>
      <div v-if="hasNextPage" class="text-center">
        <div
          class="spinner-border animate-spin inline-block w-8 h-8 border-4 border-solid rounded-full border-t-transparent border-gray-600"
          role="status"
        >
          <span class="sr-only">로딩 중...</span>
        </div>
      </div>
    </ul>
  </div>
</template>
