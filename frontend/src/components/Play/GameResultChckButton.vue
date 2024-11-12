<script setup lang="ts">
import ProfileImg from '@/assets/Male User.svg'
const { player1Id, player2Id, gameBoard } = defineProps<{
  player1Id: PlayerId,
  player2Id: PlayerId,
  gameBoard: number[][]
}>()
import GameResultApiFacade from '@/api/apiFacade/GameResultApiFacade';
import SignatureBtn from '../common/SignatureBtn.vue';

const { data, mutate, isSuccess } = GameResultApiFacade.useCreateGameResult()

</script>

<template>
  <div class="h-24 flex justify-end items-center">
    <SignatureBtn text="결과 확인" @click="() => mutate({ player1Id, player2Id, gameBoard })"/>
  </div>
  <h1 class="border-b w-full">결과</h1>
  <div class="flex justify-around p-6" v-if="isSuccess">
    <div class="flex flex-col items-center">
        <img :src="data?.player1.profileImg || ProfileImg">
        <span class="">{{ data?.player1.nickname }}</span>
        <span class="text-3xl">{{ data?.score1 }}</span>
    </div>
    <h1 class="flex items-center">VS</h1>
    <div class="flex flex-col items-center">
        <img :src="data?.player2.profileImg || ProfileImg">
        <span class="">{{ data?.player2.nickname }}</span>
        <span class="text-3xl">{{ data?.score2 }}</span>
    </div>
  </div>
</template>
