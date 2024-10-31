<script setup lang="ts">
import MaleImg from '@/assets/Male User.svg'
const cardList = [
  {
    rank: 1,
    profileImg: MaleImg,
    nickName: 'minsun',
    winCount: 72,
    maxScore: 250,
  },
  {
    rank: 2,
    profileImg: MaleImg,
    nickName: 'kangmin',
    winCount: 65,
    maxScore: 200,
  },
]
</script>

<template>
  <div class="flex justify-center flex-col gap-4">
    <h1 class="text-xl text-center">Ranking</h1>
    <ul class="flex justify-center flex-col gap-4">
      <li
        v-for="(card, index) in cardList"
        :key="index"
        class="border flex items-center"
      >
        <div class="border w-40">
          <p class="bg-purple text-white w-full text-center">{{ card.rank }}</p>
          <div class="flex flex-col justify-center items-center">
            <img :src="card.profileImg" alt="Profile Image" class="w-12 h-12" />
            <h2 class="font-semibold">{{ card.nickName }}</h2>
          </div>
        </div>
        <div class="flex flex-row justify-center gap-x-4 w-full">
          <div class="flex flex-col items-center">
            <span class="text-xs">우승 횟수</span>
            <strong>{{ card.winCount }}</strong>
          </div>
          <div class="flex flex-col items-center">
            <span class="text-xs">최고 점수</span>
            <strong>{{ card.maxScore }}</strong>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>
