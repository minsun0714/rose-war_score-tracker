<script setup lang="ts">
const { player1Id, player2Id, gameBoard } = defineProps<{
  player1Id: PlayerId,
  player2Id: PlayerId,
  gameBoard: number[][]
}>()
import GameResultApiFacade from '@/api/apiFacade/GameResultApiFacade';
import SignatureBtn from '../common/SignatureBtn.vue';

const { mutate } = GameResultApiFacade.useCreateGameResult()

</script>

<template>
  <div class="h-24 flex justify-end items-center">
    <SignatureBtn text="결과 확인" @click="() => mutate({ player1Id, player2Id, gameBoard })"/>
  </div>
</template>
