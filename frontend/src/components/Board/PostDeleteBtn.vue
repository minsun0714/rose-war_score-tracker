<script setup lang="ts">
import PostApiFacade from '@/api/apiFacade/PostApiFacade'
defineProps<{
  postId: number
}>()
const { mutate: deletePost } = PostApiFacade.useDeletePost()
</script>

<template>
  <button @click="() => deletePost({ postId })">삭제</button>
</template>
