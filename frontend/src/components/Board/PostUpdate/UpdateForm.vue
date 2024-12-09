<script setup lang="ts">
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'

import {
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form'
import { Textarea } from '@/components/ui/textarea'
import PostApiFacade from '@/api/apiFacade/PostApiFacade'
import SignatureBtn from '@/components/common/SignatureBtn.vue'
import { useRoute } from 'vue-router'

const formSchema = toTypedSchema(
  z.object({
    title: z.string().min(2).max(50),
    content: z.string().min(2).max(5000),
  }),
)
const route = useRoute()
const { data: oldPost } = PostApiFacade.useFetchPost(Number(route.params.id))

const form = useForm({
  validationSchema: formSchema,
  initialValues: {
    title: oldPost?.value?.title,
    content: oldPost?.value?.content,
  },
})

const { mutate } = PostApiFacade.useUpdatePost()

const onSubmit = form.handleSubmit(values => {
  mutate({
    postId: oldPost?.value.postId,
    title: values?.title,
    content: values?.content,
  })
})
</script>

<template>
  <form @submit="onSubmit" class="w-5/6 flex flex-col justify-center">
    <div class="border py-8">
      <FormField v-slot="{ componentField }" name="title">
        <FormItem class="flex flex-col items-center justify-center w-full h-28">
          <FormLabel class="flex items-start w-4/5">제목</FormLabel>
          <FormControl>
            <Textarea v-bind="componentField" class="border w-4/5 h-12 p-2" />
          </FormControl>
          <FormMessage class="w-4/5 flex justify-end" />
        </FormItem>
      </FormField>
      <FormField v-slot="{ componentField }" name="content">
        <FormItem class="flex flex-col items-center justify-center w-full h-80">
          <FormLabel class="flex items-start w-4/5">내용</FormLabel>
          <FormControl>
            <Textarea v-bind="componentField" class="border w-4/5 h-48 p-2" />
          </FormControl>
          <FormMessage class="w-4/5 flex justify-end" />
        </FormItem>
      </FormField>
    </div>
    <div class="w-full flex justify-end items-center h-28">
      <SignatureBtn text="글 수정" />
    </div>
  </form>
</template>
