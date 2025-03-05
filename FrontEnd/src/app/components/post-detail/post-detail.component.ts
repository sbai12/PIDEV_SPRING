import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit {
  post: any;
  comments: any[] = [];
  postId: number;

  // Reactive form for adding a comment (if needed later)
  commentForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private adminService: AdminService
  ) {
    this.postId = Number(this.route.snapshot.params['id']);
    // Initialize the reactive form
    this.commentForm = new FormGroup({
      content: new FormControl('', Validators.required),
      postedBy: new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {
    this.getPostDetails();
  }

  getPostDetails(): void {
    this.adminService.getPostById(this.postId).subscribe(
      data => {
        this.post = data;
        this.getCommentsForPost();
      },
      error => {
        window.alert("Erreur lors du chargement du post");
      }
    );
  }

  getCommentsForPost(): void {
    this.adminService.getCommentsByPost(this.postId).subscribe(
      comments => {
        this.comments = comments;
      },
      error => {
        window.alert("Erreur lors du chargement des commentaires");
      }
    );
  }

  // Method to delete a comment (locally; integrate API call if needed)
  deleteComment(commentId: number): void {
    console.log(`Comment ${commentId} deleted!`);
    this.comments = this.comments.filter(comment => comment.id !== commentId);
  }
}
