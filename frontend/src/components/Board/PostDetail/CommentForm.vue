<script setup lang="ts">
import { FormControl, FormField, FormItem, FormMessage } from '../../ui/form'
import { Textarea } from '../../ui/textarea'
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'
import SignatureBtn from '@/components/common/SignatureBtn.vue'
import CommentApiFacade from '@/api/apiFacade/CommentApiFacade'
import { useRoute } from 'vue-router'

const route = useRoute()
const postId = Number(route.params.id)
const { data: commentData } = CommentApiFacade.useFetchCommentList(postId)

const formSchema = toTypedSchema(
  z.object({
    comment: z.string().min(2).max(1000),
  }),
)

const form = useForm({
  validationSchema: formSchema,
})

const { mutate } = CommentApiFacade.useCreateComment()

const onSubmit = form.handleSubmit(values => {
  mutate({ postId, content: values.comment }, {
    onSuccess: () => {
      form.resetForm()
    },
  })
})
</script>

<template>
  <h2 class="h-8">댓글 총 {{ commentData?.length }}개</h2>
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
      <SignatureBtn text="댓글 작성" />
    </div>
  </form>
</template>
