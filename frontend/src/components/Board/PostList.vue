<script setup lang="ts">
import PostCard from '../common/PostCard.vue'
import Search from '../../assets/Search.svg'
import { useRouter } from 'vue-router'

const router = useRouter()

const cardData = [
  {
    id: 0,
    writer: 'minsun',
    title: '이민선이라고 합니다. 잘 부탁드립니다.',
    createdAt: new Date(),
    likeCount: 0,
    commentCount: 0,
  },
  {
    id: 1,
    writer: 'minsun',
    title: '또 다른 게시물입니다. 잘 부탁드립니다.',
    createdAt: new Date(),
    likeCount: 5,
    commentCount: 2,
  },
]
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
        :key="card.id"
        @click="router.push(`/board/${card.id}`)"
      >
        <PostCard :card="card" />
      </li>
    </ul>
  </div>
</template>
