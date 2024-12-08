import { useQuery, useMutation } from '@tanstack/vue-query'
import CommentService from '../services/CommentService'
import type { CommentDeleteRequest, CommentRequest } from '../interface/request'
import { queryClient } from '@/main'

class CommentApiFacade {
  // 게시물 관련 메서드
  static useFetchCommentList(postId: number) {
    return useQuery({
      queryKey: ['commentList', postId],
      queryFn: () => CommentService.fetchCommentList(postId),
    })
  }

  static useCreateComment() {
    return useMutation({
      mutationFn: ({ postId, content, parentId }: CommentRequest) =>
        CommentService.createComment(postId, content, parentId),
      onSuccess: response => {
        queryClient.invalidateQueries({
          queryKey: ['commentList', response.postId],
        })
      },
    })
  }

  static useUpdateComment() {
    return useMutation({
      mutationFn: ({ postId, commentId, content }: CommentRequest) =>
        CommentService.updateComment(postId, commentId, content),
      onSuccess: (_, variables) => {
        queryClient.invalidateQueries({
          queryKey: ['commentList', variables.postId],
        })
      },
    })
  }

  static useDeleteComment() {
    return useMutation({
      mutationFn: ({ postId, commentId }: CommentDeleteRequest) =>
        CommentService.deleteComment(postId, commentId),
      onSuccess: (_, variables) => {
        queryClient.invalidateQueries({
          queryKey: ['commentList', variables.postId],
        })
      },
    })
  }
}

export default CommentApiFacade
