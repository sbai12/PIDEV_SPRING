package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Post;
import esprit.collaborative_space.repository.IPostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImp implements IPostService{

    @Autowired
    private IPostRepository postRepository;
    @Override
    public Post savePost(Post post) {
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setDate(new Date());
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long postId) {
        Optional<Post> optionlPost = postRepository.findById(postId);
        if (optionlPost.isPresent()) {
            Post post = optionlPost.get();
            post.setViewCount(post.getViewCount() + 1);
            return postRepository.save(post);
        }else {
            throw new EntityNotFoundException("Post Not Found");

        }    }

    @Override
    public void likePost(Long postId) {
        Optional<Post> optionlPost = postRepository.findById(postId);
        if (optionlPost.isPresent()) {
            Post post = optionlPost.get();
            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);
        }
        else {
            throw new EntityNotFoundException("Post Not Found wkith id: " + postId);
        }

    }

    @Override
    public List<Post> searchByName(String name) {
        return postRepository.findAllByNameContaining(name);
    }
}
