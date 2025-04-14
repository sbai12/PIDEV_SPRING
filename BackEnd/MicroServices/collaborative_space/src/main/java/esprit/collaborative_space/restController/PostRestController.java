package esprit.collaborative_space.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import esprit.collaborative_space.entity.Post;
import esprit.collaborative_space.service.IPostService;
import esprit.collaborative_space.service.ImageDetectionService; // Import your image detection service
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
public class PostRestController {

    @Autowired
    private IPostService postService;

    @Autowired
    private ImageDetectionService imageDetectionService; // Your service for object detection

    private static final String UPLOAD_DIR = "uploads/";

    // Create a new post with image upload
    @PostMapping("addPost")
    public ResponseEntity<?> createPost(@RequestParam("file") MultipartFile file,
                                        @RequestParam("postData") String postData) {
        try {
            // Ensure the upload directory exists
            Path path = Paths.get(UPLOAD_DIR);
            if (!Files.exists(path)) {
                Files.createDirectories(path);  // Create the directory if it doesn't exist
            }

            // Generate a unique filename
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String newFilename = UUID.randomUUID().toString() + "_" + originalFilename;

            Path filePath = path.resolve(newFilename);  // Resolve the path to the uploads directory
            Files.copy(file.getInputStream(), filePath);  // Save the file to the directory

            // Deserialize postData (which contains the post fields) into a Post object
            Post post = new ObjectMapper().readValue(postData, Post.class);
            post.setImg(newFilename);  // Save the image filename in the database

            // Perform detection on the uploaded image
            String detectionResult = imageDetectionService.detectImage(filePath.toString());

            // Log the detection result for debugging
            System.out.println("Detection result: " + detectionResult);

            // Initialize content and description
            String content = post.getContent();
            String detectionStatus = "No harmful content detected.";  // Default status
            String description = "No description available";  // Default description

            // Check if harmful content is detected
            if (detectionResult.contains("Yes")) {
                // If harmful content detected, extract the description
                String[] parts = detectionResult.split(":");
                detectionStatus = "Yes, harmful content detected";
                if (parts.length > 1) {
                    description = parts[1].trim(); // If description is present after the ":"
                }
            } else {
                // If no harmful content detected, keep the default status
                detectionStatus = "No harmful content detected.";
            }

            // Append the detection result and description to the content
            post.setContent(content + " | " + detectionStatus + ": " + description);

            // Save the post in the database
            Post createdPost = postService.savePost(post);

            // Return a successful response with the created post's details
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);

        } catch (IOException e) {
            e.printStackTrace();  // Log the error to the console for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        } catch (Exception e) {
            e.printStackTrace();  // Log the error to the console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the post");
        }
    }



    // Get all posts and perform harmful content detection on images
    @GetMapping("getPost")
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            // Fetch all posts
            List<Post> posts = postService.getAllPosts();
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get post by ID
    @GetMapping("get/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        try {
            Post post = postService.getPostById(postId);
            return ResponseEntity.ok(post);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Like a post
    @PutMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        try {
            postService.likePost(postId);
            return ResponseEntity.ok(new String[]{"Post liked successfully."});
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete a post
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error during deletion");
        }
    }
}
