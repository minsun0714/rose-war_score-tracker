<script setup lang="ts">
import { useRouter } from 'vue-router';
import CommentForm from './CommentForm.vue'
import CommentList from './CommentList.vue'
import PostContent from './PostContent.vue'

const router = useRouter()
</script>

<template>
  <div>
    <div class="flex pb-8 text-sm">
      <button v-text="'< Back'" @click="router.push('/board/list')"></button>
    </div>
    <PostContent />
    <CommentForm />
    <CommentList />
  </div>
</template>
