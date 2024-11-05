
import { useMutation, useQuery } from '@tanstack/vue-query';
import PostService from '../services/PostService';

class PostApiFacade {
    // 게시물 관련 메서드
    static useFetchPostList() {
        return useQuery({ queryKey:['posts'], queryFn: () => PostService.fetchPostList() });
    }

    static useFetchPost(postId: number) {
        return useQuery({queryKey: ['post', postId],queryFn: () => PostService.fetchPost(postId)});
    }

    static useCreatePost(newPost: PostRequest) {
        return useMutation({ mutationFn: () => PostService.createPost(newPost) });
    }

    static useUpdatePost(postId: number, updatedPost: PostRequest) {
        return useMutation({
            mutationFn: () =>
                PostService.updatePost(postId, updatedPost)
        });
    }

    static useDeletePost(postId: number) {
        return useMutation({ mutationFn: () => PostService.deletePost(postId) });
    }
}

export default PostApiFacade;
