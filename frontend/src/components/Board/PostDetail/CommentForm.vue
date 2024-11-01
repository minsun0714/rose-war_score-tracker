<script setup lang="ts">
import { FormControl, FormField, FormItem } from '../../ui/form'
import { Textarea } from '../../ui/textarea'
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'
import SignatureBtn from '@/components/common/SignatureBtn.vue'
import ProfileImg from '@/assets/Male User.svg'

const postData = {
  writer: 'minsun',
  title: '이민선이라고 합니다. 잘 부탁드립니다.',
  content:
    '이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.이민선이라고 합니다. 잘 부탁드립니다.',
  createdAt: new Date(),
  likeCount: 0,
  commentCount: 2,
  commentList: [
    {
      user: { nickname: 'minsun', id: 'minsun', profileImg: ProfileImg },
      content:
        '댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.댓글입니다.',
      createdAt: new Date(),
      likeCount: 0,
      children: [
        {
          user: { nickname: 'minsun', id: 'minsun', profileImg: ProfileImg },
          content: '대댓글입니다.',
          createdAt: new Date(),
          likeCount: 0,
        },
        {
          user: { nickname: 'minsun', id: 'minsun', profileImg: ProfileImg },
          content:
            '대댓글입니다대댓글입니다대댓글입니다대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.대댓글입니다.',
          createdAt: new Date(),
          likeCount: 0,
        },
      ],
    },
    {
      user: { nickname: 'minsun', id: 'minsun', profileImg: ProfileImg },
      content: '댓글입니다.',
      createdAt: new Date(),
      likeCount: 0,
    },
  ],
}

const formSchema = toTypedSchema(
  z.object({
    comment: z.string().min(2).max(50),
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
  <h2 class="h-8">댓글 총 {{ postData.commentCount }}개</h2>
  <form @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="comment">
      <FormItem>
        <FormControl>
          <Textarea placeholder="댓글을 작성하세요" v-bind="componentField" />
        </FormControl>
      </FormItem>
    </FormField>
    <div class="flex justify-end h-20 items-center">
      <SignatureBtn text="댓글 작성" />
    </div>
  </form>
</template>
