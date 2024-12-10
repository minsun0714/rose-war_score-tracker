<script setup lang="ts">
import PostApiFacade from '@/api/apiFacade/PostApiFacade'
defineProps<{
  postId: number
}>()
const { mutate: deletePost } = PostApiFacade.useDeletePost()

const handleDelete = (postId: number) => {
  const isConfirmed = confirm('정말로 삭제하시겠습니까?')
  if (isConfirmed) {
    deletePost({ postId })
  }
}
</script>

<template>
  <button @click="() => handleDelete(postId)">삭제</button>
</template>
