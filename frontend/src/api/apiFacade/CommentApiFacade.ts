import { useQuery, useMutation } from '@tanstack/vue-query';
import CommentService from '../services/CommentService';
import type { CommentRequest } from '../interface/request';

class CommentApiFacade {
    // 게시물 관련 메서드
    static useFetchCommentList(postId: number) {
        return useQuery({ queryKey: ['commentList', postId], queryFn: () => CommentService.fetchCommentList(postId) });
    }

    static useCreateComment() {
        return useMutation({ mutationFn: ({postId, content}: CommentRequest) => CommentService.createComment(postId, content) });
    }

    static useUpdateComment(postId: number, commentId: number, updatedComment: CommentRequest) {
        return useMutation({
            mutationFn: () =>
                CommentService.updateComment(postId, commentId, updatedComment)
        });
    }

    static useDeleteComment(postId: number, commentId: number) {
        return useMutation({
            mutationFn: () =>
                CommentService.deleteComment(postId, commentId)
        })
    }
}

export default CommentApiFacade;
