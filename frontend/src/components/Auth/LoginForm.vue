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
import SignatureBtn from '../common/SignatureBtn.vue'

const formSchema = toTypedSchema(
  z.object({
    id: z.string().min(2).max(50),
    password: z.string().min(2).max(50),
  }),
)

const form = useForm({
  validationSchema: formSchema,
})

const onSubmit = form.handleSubmit(values => {
  console.log('Form submitted!', values)
})
</script>

<template>
  <form @submit="onSubmit" class="w-5/6 flex flex-col justify-center">
    <div class="border">
      <FormField v-slot="{ componentField }" name="id">
        <FormItem class="flex flex-col items-center justify-center w-full h-28">
          <FormLabel class="flex items-start w-4/5">아이디</FormLabel>
          <FormControl>
            <Input
              type="text"
              v-bind="componentField"
              class="border w-4/5 h-12 p-2"
            />
          </FormControl>
          <FormMessage class="w-4/5 flex justify-end" />
        </FormItem>
      </FormField>
      <FormField v-slot="{ componentField }" name="password">
        <FormItem class="flex flex-col items-center justify-center w-full h-28">
          <FormLabel class="flex items-start w-4/5">비밀번호</FormLabel>
          <FormControl>
            <Input
              type="password"
              v-bind="componentField"
              class="border w-4/5 h-12 p-2"
            />
          </FormControl>
          <FormMessage class="w-4/5 flex justify-end" />
        </FormItem>
      </FormField>
    </div>
    <div class="w-full flex justify-end items-center h-28">
      <SignatureBtn text="로그인" />
    </div>
  </form>
</template>
