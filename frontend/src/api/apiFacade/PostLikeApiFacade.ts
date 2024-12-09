import { useMutation, useQuery } from '@tanstack/vue-query'
import PostLikeService from '../services/PostLikeService'
import type { PostLikeRequest } from '../interface/request'
import { queryClient } from '@/main'

class PostLikeApiFacade {
  // 좋아요 관련 메서드

  static useFetchLikeStatus(postId: number){
    console.log(!!postId)
    return useQuery({
      queryKey: ['postLike', postId],
      queryFn: () => PostLikeService.getLikeStatus(postId),
      retry: 0
    })
  }

  static useTogglePostLike() {
    return useMutation({
      mutationFn: ({ postId }: PostLikeRequest) =>
        PostLikeService.togglePostLike(postId),
      onSuccess: (_, variables) => {
        // 서버 데이터로 동기화
        queryClient.invalidateQueries({ queryKey: ['post', variables.postId] })
      },
    })
  }
}

export default PostLikeApiFacade
