import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useAccordionStore = defineStore('accordion', () => {
  const isAccordionOpen = ref(false)
  const activeItem = ref('') // 현재 열려 있는 아이템의 value를 저장

  function toggleAccordion() {
    isAccordionOpen.value = !isAccordionOpen.value
  }

  function setActiveItem(value: string) {
    activeItem.value = value
  }

  return { isAccordionOpen, activeItem, toggleAccordion, setActiveItem }
})
