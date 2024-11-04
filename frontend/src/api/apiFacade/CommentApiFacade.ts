import { useQuery, useMutation } from 'vue-query';
import CommentService from '../services/CommentService';

class CommentApiFacade {
    // 게시물 관련 메서드
    static useFetchCommentList(postId: number) {
        return useQuery(['commentList', postId], () => CommentService.fetchCommentList(postId));
    }

    static useCreatePost(postId: number, newComment: CommentRequest) {
        return useMutation(() => CommentService.createComment(postId, newComment));
    }

    static useUpdatePost(postId: number, commentId: number, updatedComment: CommentRequest) {
        return useMutation(() => 
            CommentService.updateComment(postId, commentId, updatedComment)
        );
    }

    static useDeletePost(postId: number, commentId: number) {
        return useMutation(()=> CommentService.deleteComment(postId, commentId));
    }
}

export default CommentApiFacade;
