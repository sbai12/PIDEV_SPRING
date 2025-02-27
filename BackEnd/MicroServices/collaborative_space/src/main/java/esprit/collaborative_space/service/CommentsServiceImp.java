package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Comment;
import esprit.collaborative_space.entity.Post;
import esprit.collaborative_space.entity.User;
import esprit.collaborative_space.repository.ICommentRepository;
import esprit.collaborative_space.repository.IPostRepository;
import esprit.collaborative_space.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.annotations.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImp implements ICommentsService{

    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IPostRepository postRepository;


    public Comment createComment(Long postId, String postedBy, String content) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            Comment comment = new Comment();
            comment.setPost(optionalPost.get());
            comment.setContent(content);
            comment.setPostedBy(postedBy);
            comment.setCreatedAt(new Date());
            return commentRepository.save(comment);
        }
        throw new EntityNotFoundException("Post not found");

    }

    public List<Comment> getCommentsByPostId(Long postId){
        return commentRepository.findByPostId(postId);
    }

}
