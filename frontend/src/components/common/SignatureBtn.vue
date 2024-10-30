<script setup lang="ts">
defineProps({
  text: String,
})
</script>

<template>
  <button class="bg-[#5C3AD9] w-32 h-10 text-white text-sm">{{ text }}</button>
</template>
