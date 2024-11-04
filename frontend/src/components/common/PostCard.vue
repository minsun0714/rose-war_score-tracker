<script setup lang="ts">
import Like from '../../assets/Like.svg'
import Comments from '../../assets/Comments.svg'

const { card } = defineProps<{
  card: PostResponse
}>()
</script>

<template>
  <div class="bg-slate-100 p-4 text-sm">
    <div class="flex flex-row items-center gap-x-2">
      <p class="text-gray-600">{{ '@' + card.writer }}</p>
      <h2
        class="font-bold text-sm overflow-hidden text-ellipsis whitespace-nowrap"
      >
        {{ card.title }}
      </h2>
    </div>
    <div class="flex justify-start items-center text-gray-500">
      <p>{{ card.createdAt.getSeconds() }}분전</p>
      <span class="flex items-center">
        <img :src="Like" alt="Like" class="w-5 h-5" />
        {{ card.likeCount }}
      </span>
      <span class="flex items-center">
        <img :src="Comments" alt="Comments" class="w-8 h-8" />
        {{ card.likeCount }}
      </span>
    </div>
  </div>
</template>
