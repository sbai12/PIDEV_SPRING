package esprit.collaborative_space.restController;

import esprit.collaborative_space.service.ICommentsService;
import esprit.collaborative_space.service.IPostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/comments")
@CrossOrigin("*")
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
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long id) {
        try {
            commentsService.deleteComment(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Commentaire supprimé avec succès");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Commentaire non trouvé"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Erreur interne lors de la suppression du commentaire"));
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
