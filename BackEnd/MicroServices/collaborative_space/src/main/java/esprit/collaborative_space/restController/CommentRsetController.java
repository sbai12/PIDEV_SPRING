package esprit.collaborative_space.restController;

import esprit.collaborative_space.service.ICommentsService;
import esprit.collaborative_space.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentRsetController {

    @Autowired
    private ICommentsService commentsService;
    @PostMapping
    public ResponseEntity<?>createComment(@RequestParam Long postId,@RequestParam String postedBy,@RequestBody String content) {
        try{
            return ResponseEntity.ok(commentsService.createComment(postId,postedBy,content));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("comments/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        try{
            return ResponseEntity.ok(commentsService.getCommentsByPostId(postId));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }
}
