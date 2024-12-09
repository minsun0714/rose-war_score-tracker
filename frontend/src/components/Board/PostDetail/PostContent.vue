<script setup lang="ts">
import { useRoute } from 'vue-router'
import PostApiFacade from '@/api/apiFacade/PostApiFacade'
import { format } from 'date-fns'
import ProfileImg from '@/assets/Male User.svg'
import PostLikeBtn from './PostLikeBtn.vue'

const route = useRoute()
const id = Number(route.params.id)
const { data: postData } = PostApiFacade.useFetchPost(Number(id))
</script>

<template>
  <h1 class="border-b font-bold">{{ postData?.title }}</h1>
  <div class="flex justify-between items-center text-xs text-slate-400 py-4">
    <span class="flex flex-row items-center">
      <img :src="postData?.writer.profileImg || ProfileImg" alt="" />
      <p>{{ postData?.writer.nickname }}</p>
      <pre> · </pre>
      <p>
        {{
          postData?.createdAt &&
          format(new Date(postData?.createdAt), 'yyyy-MM-dd')
        }}
      </p>
    </span>
    <PostLikeBtn
      v-if="postData?.postId"
      :like-count="postData?.likeCount"
      :post-id="postData?.postId"
    />
  </div>
  <p class="pb-16 text-sm">{{ postData?.content }}</p>
</template>
