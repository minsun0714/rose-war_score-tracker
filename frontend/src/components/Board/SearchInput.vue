<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import Search from '../../assets/Search.svg';

const keyword = ref('');
const router = useRouter();

const updateQuery = () => {
  router.push({
    query: { keyword: keyword.value },
  });
};
</script>

<template>
  <div class="p-4 flex flex-row">
    <img :src="Search" alt="검색" />
    <input
      v-model="keyword"
      @input="updateQuery"
      type="text"
      placeholder="검색어를 입력해주세요."
      class="border-b w-full p-2"
    />
  </div>
</template>
