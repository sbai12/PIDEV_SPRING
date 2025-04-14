import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import { Post } from 'src/app/models/post.model';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  postForm!: FormGroup;
  tags: string[] = [];
  newTag: string = '';
  selectedFile: File | null = null;
  post: Post = new Post(0, '', '', '', '', new Date(), 0, 0); // Initialize the Post model

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private postService: PostService
  ) {}

  ngOnInit(): void {
    this.postForm = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(3)]],
      content: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(5000)]],
      postedBy: [null, [Validators.required, Validators.pattern('^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$')]],
      newTag: ['']  // Add this line if you're binding 'newTag' with the formGroup.
    });
  }

  // Add a new tag to the list
  addTag(): void {
    const tag = this.newTag.trim();
    if (tag && !this.tags.includes(tag)) {
      this.tags.push(tag);
    }
    this.newTag = '';  // Clear the new tag input
  }

  // Remove a tag from the list
  removeTag(tag: string): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;  // Store the selected file
      console.log('Selected file:', this.selectedFile);
    }
  }

  // Method to create a post
  createPost() {
    // Update the post object with form values
    this.post.name = this.postForm.get('name')?.value;
    this.post.content = this.postForm.get('content')?.value;
    this.post.postedBy = this.postForm.get('postedBy')?.value;
    this.post.tags = this.tags;
  
    if (this.selectedFile) {
      // Call the service method and pass the post data and selected file
      this.postService.addPost(this.post, this.selectedFile).subscribe(
        (createdPost) => {
          console.log('Post created successfully', createdPost);
          alert('Post created successfully');
        },
        (error) => {
          console.error('Error creating post:', error);
          alert('Error creating post: ' + error.statusText);
        }
      );
    } else {
      alert('Please select a file to upload');
    }
  }
  
}
