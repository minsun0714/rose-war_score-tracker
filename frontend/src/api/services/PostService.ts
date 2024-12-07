import api from '../apiClient';

class PostService {
    // Private 메서드로 API 호출 로직 관리
    static async _createPost(newPost: PostRequest): Promise<PostResponse> {
        const response = await api.post('/api/posts', newPost);
        return response.data;
    }

    static async _fetchPostList(page: number, keyword: string): Promise<PagedPostResponse> {
      const response = await api.get('/api/posts', {
        params: {
            page,
            keyword
          }
        });
        return response.data;
    }

    static async _fetchPost(postId: number): Promise<PostResponse> {
        const response = await api.get(`/api/posts/${postId}`);
        return response.data;
    }

    static async _updatePost(postId: number, updatedPost: PostRequest): Promise<PostResponse> {
        const response = await api.put(`/api/posts/${postId}`, updatedPost);
        return response.data;
    }

    static async _deletePost(postId: number): Promise<void> {
        await api.delete(`/api/posts/${postId}`);
    }

    // Public 메서드로 외부에서 사용할 수 있도록 제공
    static createPost(newPost: PostRequest): Promise<PostResponse> {
        return this._createPost(newPost);
    }

    static fetchPostList(page: number, keyword: string): Promise<PagedPostResponse> {
        return this._fetchPostList(page, keyword);
    }

    static fetchPost(postId: number): Promise<PostResponse> {
        return this._fetchPost(postId);
    }

    static updatePost(postId: number, updatedPost: PostRequest): Promise<PostResponse> {
        return this._updatePost(postId, updatedPost);
    }

    static deletePost(postId: number): Promise<void> {
        return this._deletePost(postId);
    }
}

export default PostService;
