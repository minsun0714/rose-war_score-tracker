import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useModifyCommentStore = defineStore('ModifyComment', () => {
  const isModifying = ref(new Set<number>())

  const toggleModifyBtn = (commentId: number) => {
    if (isModifying.value.has(commentId)) {
      isModifying.value.delete(commentId)
      return
    }
    isModifying.value.add(commentId)
  }

  return { isModifying, toggleModifyBtn }
})
