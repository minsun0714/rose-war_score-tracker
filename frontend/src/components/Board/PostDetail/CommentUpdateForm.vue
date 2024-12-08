<script setup lang="ts">
import SignatureBtn from '@/components/common/SignatureBtn.vue'
import { FormControl, FormField, FormItem, FormMessage } from '../../ui/form'
import { Textarea } from '../../ui/textarea'
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'
import CommentApiFacade from '@/api/apiFacade/CommentApiFacade'

const { postId, commentId, content, toggleModifyButton } = defineProps<{
  postId: number
  commentId: number
  content: string
  toggleModifyButton: (commentId: number) => void
}>()

const formSchema = toTypedSchema(
  z.object({
    comment: z
      .string()
      .min(2, '댓글은 최소 2자 이상이어야 합니다.')
      .max(1000, '댓글은 1000자를 초과할 수 없습니다.'),
  }),
)

const form = useForm({
  validationSchema: formSchema,
  initialValues: {
    comment: content,
  },
})

const { mutateAsync } = CommentApiFacade.useUpdateComment()

const onSubmit = form.handleSubmit(values => {
  mutateAsync({ postId, commentId, content: values.comment }).then(() =>
    toggleModifyButton(commentId),
  )
})
</script>

<template>
  <form @submit.prevent="onSubmit">
    <FormField v-slot="{ componentField }" name="comment">
      <FormItem>
        <FormControl>
          <Textarea v-bind="componentField" placeholder="댓글을 작성하세요" />
        </FormControl>
      </FormItem>
      <FormMessage />
    </FormField>
    <div class="flex justify-end h-20 items-center">
      <button @click="() => toggleModifyButton(commentId)" class="flex justify-end items-center w-2/3 h-full text-purple pr-5">취소</button>
      <SignatureBtn text="댓글 수정" />
    </div>
  </form>
</template>
