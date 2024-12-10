<script setup lang="ts">
import PostLikeApiFacade from '@/api/apiFacade/PostLikeApiFacade'
import { useHandleAuth } from '@/lib/hooks/useHandleAuth'
import { IoIosHeartEmpty, IoMdHeart } from 'vue3-icons/io'

const { postId } = defineProps<{
  likeCount: number
  postId: number
}>()

const { data: isLiked, refetch } = PostLikeApiFacade.useFetchLikeStatus(postId)

const { mutateAsync: toggleLike } = PostLikeApiFacade.useTogglePostLike()

const { handleAuth } = useHandleAuth()
</script>

<template>
  <button
    v-if="postId"
    class="flex flex-row items-center gap-1 text-xs border border-gray-500 px-2 py-1 rounded-2xl"
    :class="isLiked ? 'bg-purple text-white' : 'bg-gray-200 text-black'"
    @click="handleAuth(() => toggleLike({ postId }).then(() => refetch()))"
  >
    <IoMdHeart v-if="isLiked" />
    <IoIosHeartEmpty v-else />
    {{ likeCount }}
  </button>
</template>
