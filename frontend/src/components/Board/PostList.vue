<script setup lang="ts">
import PostCard from '../common/PostCard.vue'

const cardData = [
  {
    writer: 'minsun',
    title: '이민선이라고 합니다. 잘 부탁드립니다.',
    createdAt: new Date(),
    likeCount: 0,
    commentCount: 0,
  },
  {
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
    <ul class="p-4 flex flex-col gap-y-4">
      <li v-for="(card, index) in cardData" :key="index">
        <PostCard :card="card" />
      </li>
    </ul>
  </div>
</template>
