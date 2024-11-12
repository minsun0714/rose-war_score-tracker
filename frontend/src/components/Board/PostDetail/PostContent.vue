<script setup lang="ts">
import { useRoute } from 'vue-router'
import PostApiFacade from '@/api/apiFacade/PostApiFacade';
import LikeIcon from '@/assets/Like.svg'

const route = useRoute()
const id = Number(route.params.id)
const {data: postData} = PostApiFacade.useFetchPost(Number(id));

console.log(postData)

</script>

<template>
  <h1 class="border-b font-bold">{{ postData?.title }}</h1>
  <div class="flex justify-between items-center text-xs text-slate-400">
    <button class="flex flex-row items-center gap-1 text-xs">
      <img :src="LikeIcon" alt="게시물 추천 수" /> {{ postData?.likeCount }}
    </button>
    <p>{{ postData?.createdAt }}</p>
  </div>
  <p class="py-4 text-sm">{{ postData?.content }}</p>
</template>
