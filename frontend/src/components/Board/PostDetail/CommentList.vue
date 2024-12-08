<script setup lang="ts">
import { formatDistanceToNow } from 'date-fns'
import { ko } from 'date-fns/locale'
import CommentChildrenList from './CommentChildrenList.vue'
import CommentApiFacade from '@/api/apiFacade/CommentApiFacade'
import { useRoute } from 'vue-router'
import ProfileImg from '@/assets/Male User.svg'
import ChildCommentForm from './ChildCommentForm.vue'
import CommentDeleteBtn from './CommentDeleteBtn.vue'
import AuthApiFacade from '@/api/apiFacade/AuthApiFacade'
import CommentModifyForm from './CommentUpdateForm.vue'
import { useModifyCommentStore } from '@/stores/modifyComment'
import { useCreateChildCommentStore } from '@/stores/createChildComment'

const { data: userInfo } = AuthApiFacade.useFetchUserInfo()

const route = useRoute()
const postId = Number(route.params.id)
const { data: commentList } = CommentApiFacade.useFetchCommentList(postId)

const commentToggleStore = useModifyCommentStore()
const createChildCommentStore = useCreateChildCommentStore()
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
            formatDistanceToNow(new Date(comment.createdAt), {
              addSuffix: true,
              locale: ko,
            })
          }}</span>
          <template
            v-if="commentToggleStore.isModifying.has(comment.commentId)"
          >
            <CommentModifyForm
              :post-id="comment.postId"
              :comment-id="comment.commentId"
              :content="comment.content"
              :toggle-modify-button="commentToggleStore.toggleModifyBtn"
            />
          </template>
          <template v-else>
            <p class="text-xs flex items-center">{{ comment.content }}</p>
          </template>
        </span>
      </div>
      <div class="py-2 flex justify-between gap-2 text-xs">
        <button
          class="text-purple"
          @click="createChildCommentStore.toggleChildComment(comment.commentId)"
        >
          {{
            createChildCommentStore.isChildCommentOpen.has(comment.commentId)
              ? '- 답글 닫기'
              : '+ 답글 달기'
          }}
        </button>
        <span
          class="text-slate-400 flex gap-x-2"
          v-if="comment.writer.userId === userInfo.userId"
        >
          <button
          v-if="!commentToggleStore.isModifying.has(comment.commentId)"
            @click="commentToggleStore.toggleModifyBtn(comment.commentId)"
          >
            수정
          </button>
          <CommentDeleteBtn
            :postId="comment.postId"
            :commentId="comment.commentId"
          />
        </span>
      </div>
      <div
        v-if="createChildCommentStore.isChildCommentOpen.has(comment.commentId)"
      >
        <ChildCommentForm :postId="postId" :parentId="comment.commentId" />
      </div>
      <CommentChildrenList
        v-if="comment.childrenComments"
        :commentChildren="comment.childrenComments"
      />
    </li>
  </ul>
</template>
