<script setup lang="ts">
import CommentForm from './CommentForm.vue'
import CommentList from './CommentList.vue'
import PostContent from './PostContent.vue'
</script>

<template>
  <div class="p-4">
    <PostContent />
    <CommentForm />
    <CommentList />
  </div>
</template>
