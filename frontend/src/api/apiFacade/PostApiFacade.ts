import { useQuery, useMutation } from 'vue-query';
import PostService from '../services/PostService';

class PostApiFacade {
    // 게시물 관련 메서드
    static useFetchPosts() {
        return useQuery('posts', PostService.fetchPosts);
    }

    static useFetchPost(postId: number) {
        return useQuery(['post', postId], () => PostService.fetchPost(postId));
    }

    static useCreatePost() {
        return useMutation(PostService.createPost);
    }

    static useUpdatePost(postId: number, updatedPost: PostRequest) {
        return useMutation(() => 
            PostService.updatePost(postId, updatedPost)
        );
    }

    static useDeletePost() {
        return useMutation(PostService.deletePost);
    }
}

export default PostApiFacade;
