<script setup lang="ts">
import CommentApiFacade from '@/api/apiFacade/CommentApiFacade'

defineProps<{
  postId: number
  commentId: number
}>()

const { mutate } = CommentApiFacade.useDeleteComment()
</script>

<template>
  <button @click="() => mutate({ postId, commentId })">삭제</button>
</template>
