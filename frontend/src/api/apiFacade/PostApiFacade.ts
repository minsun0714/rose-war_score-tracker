import { useInfiniteQuery, useMutation, useQuery } from '@tanstack/vue-query'
import PostService from '../services/PostService'
import { useRoute, useRouter } from 'vue-router'
import { computed, ref, watch } from 'vue'
import { useDebounce } from '@vueuse/core'
import type { PostDeleteRequest, PostRequest } from '../interface/request'

class PostApiFacade {
  // 게시물 관련 메서드
  static useFetchPostList() {
    const route = useRoute()

    const keyword = ref(route.query.keyword)

    watch(
      () => route.query.keyword,
      newKeyword => {
        keyword.value = newKeyword || ''
      },
      { immediate: true },
    )

    const debouncedKeyword = useDebounce(keyword, 300)

    return useInfiniteQuery({
      queryKey: computed(() => ['posts', debouncedKeyword.value]),
      queryFn: ({ pageParam = 0 }) =>
        PostService.fetchPostList(pageParam, debouncedKeyword.value as string),
      getNextPageParam: (page: PagedPostResponse) => {
        return page.last ? undefined : page.currentPage + 1
      },
      initialPageParam: 0,
    })
  }

  static useFetchPost(postId: number) {
    return useQuery({
      queryKey: ['post', postId],
      queryFn: () => PostService.fetchPost(postId),
    })
  }

  static useCreatePost() {
    const router = useRouter()
    return useMutation({
      mutationFn: ({ title, content }: PostRequest) =>
        PostService.createPost(title, content),
      onSuccess: response => router.push(`/board/${response.postId}`),
    })
  }

  static useUpdatePost(postId: number, updatedPost: PostRequest) {
    return useMutation({
      mutationFn: () => PostService.updatePost(postId, updatedPost),
    })
  }

  static useDeletePost() {
    const router = useRouter()
    return useMutation({
      mutationFn: async ({ postId }: PostDeleteRequest) => {
        return await PostService.deletePost(postId)
      },
      onSuccess: () => router.push('/board/list'),
    })
  }
}

export default PostApiFacade
