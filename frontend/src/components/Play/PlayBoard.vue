<script setup lang="ts">
import { ref } from 'vue'
import ProfileImg from '../../assets/Male User.svg'
import GameResultChckButton from './GameResultChckButton.vue'
import AuthApiFacade from '@/api/apiFacade/AuthApiFacade'

const { data: user1 } = AuthApiFacade.useFetchUserInfo()
const user2 = { profileImg: ProfileImg, nickname: 'guest', userId: 'guest' }

const board = ref(
  Array.from({ length: 10 }, () => Array.from({ length: 10 }, () => 0)),
)

const turn = ref<1 | 2>(1)

const changeTurn = (user: 1 | 2) => {
  turn.value = user
}

const clickCell = (user: 1 | 2, rowIdx: number, colIdx: number) => {
  board.value[rowIdx][colIdx] = board.value[rowIdx][colIdx] == user ? 0 : user
}
</script>

<template>
  <div class="flex flex-col justify-center">
    <h1 class="text-center h-12 flex items-center justify-center">
      tab cells!
    </h1>

    <button
      @click="changeTurn(1)"
      :class="{
        'bg-red-500 text-white': turn === 1,
        'bg-white': turn !== 1,
      }"
      class="flex justify-center items-center h-12 gap-2 border border-slate-700"
    >
      <img
        :src="user1?.profileImg || ProfileImg"
        alt=""
        class="border border-white rounded-full h-6 w-6"
      />
      {{ user1?.nickname || 'guest' }}
    </button>

    <ul class="flex bg-purple gap-y-1 p-2 justify-center">
      <li v-for="(row, rowIndex) in board" :key="rowIndex">
        <span
          v-for="(cell, colIndex) in row"
          :key="colIndex"
          @click="clickCell(turn, rowIndex, colIndex)"
          :class="{
            'bg-red-500': cell === 1,
            'bg-yellow-400': cell === 2,
            'bg-white': cell === 0,
          }"
          class="w-6 h-6 border border-gray-300 flex items-center justify-center text-transparent cursor-pointer"
        >
          {{ cell }}
        </span>
      </li>
    </ul>

    <button
      @click="changeTurn(2)"
      :class="{
        'bg-yellow-400 text-white': turn === 2,
        'bg-white': turn !== 2,
      }"
      class="flex justify-center items-center h-12 gap-2 border border-slate-700"
    >
      <img
        :src="user2.profileImg"
        alt=""
        class="border border-white rounded-full h-6 w-6"
      />
      {{ user2.userId }}
    </button>
  </div>
  <GameResultChckButton
    :player1Id="user1?.userId"
    :player2Id="user2.userId"
    :gameBoard="board"
  />
</template>
