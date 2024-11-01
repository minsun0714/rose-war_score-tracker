<script setup lang="ts">
import LikeIcon from '@/assets/Like.svg'
import ProfileImg from '@/assets/Male User.svg'

const postData = {
  writer: 'minsun',
  title: '이민선이라고 합니다. 잘 부탁드립니다.',
  content:
    '이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.',
  createdAt: new Date(),
  likeCount: 0,
  commentCount: 2,
  commentList: [
    {
      user: { nickname: 'minsun', id: 'minsun', profileImg: ProfileImg },
      content:
        '댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.',
      createdAt: new Date(),
      likeCount: 0,
      children: [
        {
          user: { nickname: 'minsun', id: 'minsun', profileImg: ProfileImg },
          content: '대댓글입니다.',
          createdAt: new Date(),
          likeCount: 0,
        },
        {
          user: { nickname: 'minsun', id: 'minsun', profileImg: ProfileImg },
          content:
            '대댓글입니다대댓글입니다대댓글입니다대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.',
          createdAt: new Date(),
          likeCount: 0,
        },
      ],
    },
    {
      user: { nickname: 'minsun', id: 'minsun', profileImg: ProfileImg },
      content: '댓글입니다.',
      createdAt: new Date(),
      likeCount: 0,
    },
  ],
}
</script>

<template>
  <h1 class="border-b font-bold">{{ postData.title }}</h1>
  <div class="flex justify-between items-center text-xs text-slate-400">
    <button class="flex flex-row items-center gap-1 text-xs">
      <img :src="LikeIcon" alt="게시물 추천 수" /> {{ postData.likeCount }}
    </button>
    <p>{{ postData.createdAt.toDateString() }}</p>
  </div>
  <p class="py-4 text-sm">{{ postData.content }}</p>
</template>
