import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CommentService } from 'src/app/services/comment.service';
import { PostService } from 'src/app/services/post.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  postId: number;
  postData: any;
  comments: any[] = []; // Comments array
  qrCodeUrl: string = ''; // To store the generated QR code URL

  // Comment form
  commentForm!: FormGroup;

  constructor(
    private postService: PostService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private commentService: CommentService,
    private http: HttpClient // Import HttpClient to send email request
  ) {
    this.postId = Number(this.activatedRoute.snapshot.params['id']);
  }

  ngOnInit(): void {
    console.log("Post ID:", this.postId);
    this.getPostById();

    // Updated form validation: "content" min 5 characters, "postedBy" only letters and spaces
    this.commentForm = this.fb.group({
      content: [null, [Validators.required, Validators.minLength(5)]],
      postedBy: [null, [Validators.required, Validators.pattern('^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$')]]
    });
  }

  // Publish comment method
  publishComment(): void {
    if (this.commentForm.invalid) {
      return;
    }
    const postedBy = this.commentForm.get('postedBy')?.value;
    const content = this.commentForm.get('content')?.value;

    this.commentService.createComment(this.postId, postedBy, content).subscribe(
      res => {
        alert("Comment published successfully");
        this.getCommentsByPost(); // Update the comments list
        this.commentForm.reset(); // Reset the form after publishing
      },
      error => {
        alert("Something went wrong while publishing the comment!");
      }
    );
  }

  // Fetch comments for the current post
  getCommentsByPost(): void {
    this.commentService.getCommentsByPostId(this.postId).subscribe(
      (res: any[]) => {
        this.comments = res;
      },
      error => {
        alert("Something went wrong while retrieving comments!");
      }
    );
  }

  // Handle Report button click
  reportPost(postId: number): void {
    const emailContent = {
      postId: postId, // Use the passed postId
    };

    // Send email to backend for processing
    this.http.post('http://localhost:8089/api/report', emailContent).subscribe(
      (response: any) => {
        if (response.success) {
          alert(response.message);
        } else {
          alert('Failed to send the report!');
        }
      },
      error => {
        console.error('Error sending report:', error);
        alert('Failed to send the report.');
      }
    );
  }

  // Fetch post details by ID
  getPostById(): void {
    this.postService.getPostById(this.postId).subscribe(
      res => {
        this.postData = res;
        console.log("Post data:", res);
        this.getCommentsByPost();
      },
      error => {
        alert("Something went wrong while retrieving the post!");
      }
    );
  }

  // Delete a comment
  deleteComment(commentId: number): void {
    if (confirm("Voulez-vous vraiment supprimer ce commentaire ?")) {
      this.commentService.deleteComment(commentId).subscribe({
        next: () => {
          this.comments = this.comments.filter(comment => comment.id !== commentId);
          alert("Comment deleted successfully");
        },
        error: (err) => {
          console.error("Error deleting comment:", err);
          alert("Something went wrong while deleting the comment");
        }
      });
    }
  }

  // Like a post
  likePost(): void {
    this.postService.likePost(this.postId).subscribe(
      () => {
        alert("Post liked successfully");
        this.postData.likeCount += 1; // Update like count locally
      },
      error => {
        alert("Something went wrong while liking the post!");
      }
    );
  }

  // Generate the QR Code for the comments section when the button is clicked
  generateQRCode(): void {
    this.commentService.generateQRCode(this.postId).subscribe(
      (res: Blob) => {
        const url = window.URL.createObjectURL(res);
        this.qrCodeUrl = url; // Store the generated QR code URL
      },
      error => {
        console.error("Error fetching QR code:", error);
        alert("Failed to generate QR code.");
      }
    );
  }
}
