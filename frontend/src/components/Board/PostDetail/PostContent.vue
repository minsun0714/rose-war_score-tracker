<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import PostApiFacade from '@/api/apiFacade/PostApiFacade'
import { format } from 'date-fns'
import ProfileImg from '@/assets/Male User.svg'
import PostLikeBtn from './PostLikeBtn.vue'
import PostDeleteBtn from '../PostDeleteBtn.vue'
import AuthApiFacade from '@/api/apiFacade/AuthApiFacade'

const { data: userInfo } = AuthApiFacade.useFetchUserInfo()
const route = useRoute()
const id = Number(route.params.id)
const { data: postData } = PostApiFacade.useFetchPost(Number(id))

const router = useRouter()
</script>

<template>
  <h1 class="border-b font-bold">{{ postData?.title }}</h1>
  <div
    class="flex justify-end text-gray-500 text-sm gap-1 pt-3"
    v-if="userInfo?.userId === postData?.writer.userId"
  >
    <button @click="router.push(`/board/update/${postData?.postId}`)">수정</button>
    <PostDeleteBtn :post-id="postData?.postId" />
  </div>
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
