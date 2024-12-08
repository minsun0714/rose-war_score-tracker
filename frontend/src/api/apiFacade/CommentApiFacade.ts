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

  static useUpdateComment(
    postId: number,
    commentId: number,
    updatedComment: CommentRequest,
  ) {
    return useMutation({
      mutationFn: () =>
        CommentService.updateComment(postId, commentId, updatedComment),
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
