<script setup lang="ts">
import type { CommentResponse } from '@/api/interface/response';
import CurvedArrow from '@/assets/Curved Arrow.svg'
import { formatDistanceToNow } from 'date-fns';
import { ko } from 'date-fns/locale';
import ProfileImg from '@/assets/Male User.svg'


const { commentChildren } = defineProps<{
  commentChildren?: CommentResponse[]
}>()
</script>

<template>
  <ul>
    <li
      v-for="(childComment, index) in commentChildren"
      :key="index"
      class="border-t py-2 flex flex-row justify-between"
    >
      <span
        class="flex flex-row items-center justify-center text-xs text-slate-400 w-20"
      >
        <img :src="CurvedArrow" alt="대댓글" />
        <span>
          <img :src="childComment.writer.profileImg || ProfileImg" />
          <span> {{ childComment.writer.nickname }}</span></span
        >
      </span>
      <span class="text-xs w-full flex flex-col gap-2">
        <span class="text-end text-slate-500">{{
            formatDistanceToNow(new Date(childComment.createdAt), {
              addSuffix: true,
              locale: ko,
            })
          }}</span>
        <p class="text-xs flex items-center">
          {{ childComment.content }}
        </p>
        <span class="text-slate-400 flex justify-end gap-1"
          ><button>수정</button> <button>삭제</button></span
        >
      </span>
    </li>
  </ul>
</template>
