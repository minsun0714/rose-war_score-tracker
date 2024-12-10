<script setup lang="ts">
import { FormControl, FormField, FormItem, FormMessage } from '../../ui/form'
import { Textarea } from '../../ui/textarea'
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'
import SignatureBtn from '@/components/common/SignatureBtn.vue'
import CommentApiFacade from '@/api/apiFacade/CommentApiFacade'
import { useCreateChildCommentStore } from '@/stores/createChildComment'
import { useHandleAuth } from '@/lib/hooks/useHandleAuth'
const { postId, parentId } = defineProps<{
  postId: number
  parentId: number
}>()

const formSchema = toTypedSchema(
  z.object({
    comment: z.string().min(2).max(1000),
  }),
)

const form = useForm({
  validationSchema: formSchema,
})

const { mutate } = CommentApiFacade.useCreateComment()

const createChildCommentStore = useCreateChildCommentStore()

const { handleAuth } = useHandleAuth()

const onSubmit = form.handleSubmit(values => {
  handleAuth(() =>
    mutate(
      { postId, parentId, content: values.comment },
      {
        onSuccess: () => {
          form.resetForm()
          createChildCommentStore.toggleChildComment(parentId)
        },
      },
    ),
  )
})
</script>

<template>
  <form @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="comment">
      <FormItem>
        <FormControl>
          <Textarea placeholder="댓글을 작성하세요" v-bind="componentField" />
        </FormControl>
      </FormItem>
      <FormMessage />
    </FormField>
    <div class="flex justify-end h-20 items-center">
      <SignatureBtn text="대댓글 작성" />
    </div>
  </form>
</template>
