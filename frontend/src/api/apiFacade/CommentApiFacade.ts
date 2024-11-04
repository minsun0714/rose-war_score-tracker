import { useQuery, useMutation } from '@tanstack/vue-query';
import CommentService from '../services/CommentService';

class CommentApiFacade {
    // 게시물 관련 메서드
    static useFetchCommentList(postId: number) {
        return useQuery({ queryKey: ['commentList', postId], queryFn: () => CommentService.fetchCommentList(postId) });
    }

    static useCreatePost(postId: number, newComment: CommentRequest) {
        return useMutation({ mutationFn: () => CommentService.createComment(postId, newComment) });
    }

    static useUpdatePost(postId: number, commentId: number, updatedComment: CommentRequest) {
        return useMutation({
            mutationFn: () =>
                CommentService.updateComment(postId, commentId, updatedComment)
        });
    }

    static useDeletePost(postId: number, commentId: number) {
        return useMutation({
            mutationFn: () =>
                CommentService.deleteComment(postId, commentId)
        })
    }
}

export default CommentApiFacade;
