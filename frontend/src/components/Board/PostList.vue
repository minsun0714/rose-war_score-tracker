<script setup lang="ts">
import PostCard from '../common/PostCard.vue'
import Search from '../../assets/Search.svg'
import { useRouter } from 'vue-router'
import PostApiFacade from '@/api/apiFacade/PostApiFacade';

const router = useRouter()

const { data: cardData } = PostApiFacade.useFetchPostList()

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
        v-for="card in cardData"
        :key="card.postId"
        @click="router.push(`/board/${card.postId}`)"
      >
        <PostCard :card="card" />
      </li>
    </ul>
  </div>
</template>
