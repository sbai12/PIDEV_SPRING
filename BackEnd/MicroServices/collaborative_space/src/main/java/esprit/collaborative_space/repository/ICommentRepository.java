package esprit.collaborative_space.repository;

import esprit.collaborative_space.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(Long postId);

}
