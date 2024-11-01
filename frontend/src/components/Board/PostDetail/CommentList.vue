<script setup lang="ts">
import ProfileImg from '@/assets/Male User.svg'
import CommentChildrenList from './CommentChildrenList.vue'

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
      commentList: [],
    },
  ],
}
</script>

<template>
  <ul>
    <li
      v-for="(comment, index) in postData.commentList"
      :key="index"
      class="border-t py-2 flex flex-col justify-between"
    >
      <div class="py-2 flex flex-row justify-between">
        <span
          class="flex flex-col items-center justify-center text-xs text-slate-400 w-20"
        >
          <img :src="comment.user.profileImg" />
          <span> {{ comment.user.nickname }}</span>
        </span>
        <span class="text-xs w-full flex flex-col gap-2">
          <span class="text-end text-slate-500">{{
            comment.createdAt.toDateString()
          }}</span>
          <p class="text-xs flex items-center">{{ comment.content }}</p>
        </span>
      </div>
      <span class="py-2 flex justify-between gap-2 text-xs"
        ><button class="text-purple">+ 답글 달기</button
        ><span class="text-slate-400"
          ><button>수정</button> <button>삭제</button></span
        ></span
      >
      <CommentChildrenList
        v-if="comment.children"
        :commentChildren="comment.children"
      />
    </li>
  </ul>
</template>
