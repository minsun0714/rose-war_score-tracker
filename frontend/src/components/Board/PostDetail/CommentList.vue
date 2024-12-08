<script setup lang="ts">
import { formatDistanceToNow } from 'date-fns';
import { ko } from 'date-fns/locale';
import CommentChildrenList from './CommentChildrenList.vue'
import CommentApiFacade from '@/api/apiFacade/CommentApiFacade';
import { useRoute } from 'vue-router';
import ProfileImg from '@/assets/Male User.svg';

const route = useRoute();
const id = Number(route.params.id)
const { data: commentList } = CommentApiFacade.useFetchCommentList(id)




</script>

<template>
  <ul>
    <li
      v-for="(comment, index) in commentList"
      :key="index"
      class="border-t py-2 flex flex-col justify-between"
    >
      <div class="py-2 flex flex-row justify-between">
        <span
          class="flex flex-col items-center justify-center text-xs text-slate-400 w-20"
        >
          <img :src="comment.writer.profileImg || ProfileImg" />
          <span> {{ comment.writer.nickname }}</span>
        </span>
        <span class="text-xs w-full flex flex-col gap-2">
          <span class="text-end text-slate-500">{{
            formatDistanceToNow(new Date(comment.createdAt), { addSuffix: true, locale: ko })
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
        v-if="comment.childrenComments"
        :commentChildren="comment.childrenComments"
      />
    </li>
  </ul>
</template>
