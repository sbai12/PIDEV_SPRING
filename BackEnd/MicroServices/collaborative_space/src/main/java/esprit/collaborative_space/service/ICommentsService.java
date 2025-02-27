package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Comment;

import java.util.List;

public interface ICommentsService {


    Comment createComment(Long postId, String postedBy, String content);

    List<Comment> getCommentsByPostId(Long postId);
}
