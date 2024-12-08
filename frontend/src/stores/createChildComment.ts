import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useCreateChildCommentStore = defineStore('createChildComment', () => {
  const isChildCommentOpen = ref(new Set<number>())

  const toggleChildComment = (commentId: number) => {
    if (isChildCommentOpen.value.has(commentId)) {
      isChildCommentOpen.value.delete(commentId)
      return
    }
    isChildCommentOpen.value.add(commentId)
  }

  return { isChildCommentOpen, toggleChildComment }
})
