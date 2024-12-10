<script setup lang="ts">
import CommentApiFacade from '@/api/apiFacade/CommentApiFacade'

defineProps<{
  postId: number
  commentId: number
}>()

const { mutate } = CommentApiFacade.useDeleteComment()

const handleDeleteComment = (postId: number, commentId: number) => {
  const isConfirmed = confirm('정말로 삭제하시겠습니까?')
  if (isConfirmed) {
    mutate({ postId, commentId })
  }
}
</script>

<template>
  <button @click="() => handleDeleteComment(postId, commentId)">삭제</button>
</template>
