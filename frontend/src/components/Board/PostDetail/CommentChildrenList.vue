<script setup lang="ts">
import type { CommentResponse } from '@/api/interface/response'
import CurvedArrow from '@/assets/Curved Arrow.svg'
import { formatDistanceToNow } from 'date-fns'
import { ko } from 'date-fns/locale'
import ProfileImg from '@/assets/Male User.svg'
import { useModifyCommentStore } from '@/stores/modifyComment'
import CommentUpdateForm from './CommentUpdateForm.vue'
import CommentDeleteBtn from './CommentDeleteBtn.vue'

const { commentChildren } = defineProps<{
  commentChildren?: CommentResponse[]
}>()

const commentToggleStore = useModifyCommentStore()
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
      <div class="text-xs w-full flex flex-col gap-2">
        <span class="text-end text-slate-500">{{
          formatDistanceToNow(new Date(childComment.createdAt), {
            addSuffix: true,
            locale: ko,
          })
        }}</span>
        <template
          v-if="commentToggleStore.isModifying.has(childComment.commentId)"
        >
          <CommentUpdateForm
            :post-id="childComment.postId"
            :comment-id="childComment.commentId"
            :content="childComment.content"
            :toggle-modify-button="commentToggleStore.toggleModifyBtn"
          />
        </template>
        <template v-else>
          <p class="text-xs flex items-center">{{ childComment.content }}</p>
        </template>

        <span class="text-slate-400 flex justify-end gap-1"
          ><button
          v-if="!commentToggleStore.isModifying.has(childComment.commentId)"
            @click="
              () => commentToggleStore.toggleModifyBtn(childComment.commentId)
            "
          >
            수정
          </button>
          <CommentDeleteBtn
            :post-id="childComment.postId"
            :comment-id="childComment.commentId"
          />
        </span>
      </div>
    </li>
  </ul>
</template>
