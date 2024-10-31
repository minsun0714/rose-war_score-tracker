<script setup lang="ts">
import LikeIcon from '@/assets/Like.svg'
import ProfileImg from '@/assets/Male User.svg'
import CurvedArrow from '@/assets/Curved Arrow.svg'

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
            '대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.',
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
  <div class="p-4">
    <h1 class="border-b font-bold">{{ postData.title }}</h1>
    <div class="flex justify-between items-center text-xs text-slate-400">
      <button class="flex flex-row items-center gap-1 text-xs">
        <img :src="LikeIcon" alt="게시물 추천 수" /> {{ postData.likeCount }}
      </button>
      <p>{{ postData.createdAt.toDateString() }}</p>
    </div>
    <p class="py-4 text-sm">{{ postData.content }}</p>

    <h2>댓글 총 {{ postData.commentCount }}개</h2>
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
            <span class="flex justify-end gap-2 text-slate-400"
              ><button>수정</button> <button>삭제</button></span
            >
          </span>
        </div>
        <ul>
          <li
            v-for="(childComment, index) in comment.children"
            :key="index"
            class="border-t py-2 flex flex-row justify-between"
          >
            <span
              class="flex flex-row items-center justify-center text-xs text-slate-400 w-20"
            >
              <img :src="CurvedArrow" alt="대댓글" />
              <span>
                <img :src="childComment.user.profileImg" />
                <span> {{ childComment.user.nickname }}</span></span
              >
            </span>
            <span class="text-xs w-full flex flex-col gap-2">
              <span class="text-end text-slate-500">{{
                childComment.createdAt.toDateString()
              }}</span>
              <p class="text-xs flex items-center">
                {{ childComment.content }}
              </p>
              <span class="flex justify-end gap-2 text-slate-400"
                ><button>수정</button> <button>삭제</button></span
              >
            </span>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>
