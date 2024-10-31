<script setup lang="ts">
import { ref } from 'vue'
import GameInfo from './GameInfo.vue'
import MyInfo from './MyInfo.vue'

const currentTab = ref('gameInfo')

const switchTab = (tab: string) => {
  currentTab.value = tab
}
</script>

<template>
  <div class="flex justify-center flex-col gap-4 p-6">
    <h1 class="text-xl text-center">마이페이지</h1>
    <div class="flex justify-start gap-4 border-b">
      <button
        @click="switchTab('gameInfo')"
        class="p-2 border border-transparent"
        :class="{ 'border-t-purple font-bold': currentTab === 'gameInfo' }"
      >
        게임
      </button>
      <button
        @click="switchTab('myInfo')"
        class="p-2 border border-transparent"
        :class="{ 'border-t-purple font-bold': currentTab === 'myInfo' }"
      >
        내 정보
      </button>
    </div>
    <ul class="flex justify-center flex-col gap-4">
      <li v-if="currentTab === 'gameInfo'"><GameInfo /></li>
      <li v-if="currentTab === 'myInfo'"><MyInfo /></li>
    </ul>
  </div>
</template>
