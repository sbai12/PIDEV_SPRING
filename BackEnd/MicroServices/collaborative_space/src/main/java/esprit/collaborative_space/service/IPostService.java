package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Post;

import java.util.List;

public interface IPostService {



    Post savePost(Post post);
    List<Post> getAllPosts();
    Post getPostById(Long postId);
    void likePost(Long postId  );
    List<Post> searchByName(String name);
}
