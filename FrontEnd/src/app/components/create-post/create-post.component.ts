// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { Router } from '@angular/router';
// import { PostService } from 'src/app/services/post.service';

// @Component({
//   selector: 'app-create-post',
//   templateUrl: './create-post.component.html',
//   styleUrls: ['./create-post.component.css']
// })
// export class CreatePostComponent implements OnInit {
//   postForm!: FormGroup;
//   tags: string[] = [];
//   newTag: string = '';

//   constructor(
//     private fb: FormBuilder,
//     private router: Router,
//     private postService: PostService
//   ) {}

//   ngOnInit(): void {
//     this.postForm = this.fb.group({
//       name: [null, Validators.required],
//       content: [null, [Validators.required, Validators.maxLength(5000)]],
//       img: [null, Validators.required],
//       postedBy: [null, Validators.required]
//     });
//   }

//   // Called when user presses Enter or clicks the "Add Tag" button.
//   addTag(): void {
//     const tag = this.newTag.trim();
//     if (tag && !this.tags.includes(tag)) {
//       this.tags.push(tag);
//     }
//     this.newTag = '';
//   }

//   removeTag(tag: string): void {
//     const index = this.tags.indexOf(tag);
//     if (index >= 0) {
//       this.tags.splice(index, 1);
//     }
//   }

//   createPost(): void {
//     if (this.postForm.invalid) {
//       return;
//     }
//     const data = this.postForm.value;
//     data.tags = this.tags;
//     console.log("Data sent:", data);

//     this.postService.createPost(data).subscribe(
//       (res) => {
//         alert("Post Created Successfully!");
//         this.router.navigateByUrl("/posts");
//       },
//       (error) => {
//         console.error("Error creating post:", error);
//         alert("Something Went Wrong!");
//       }
//     );
//   }
// }
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  postForm!: FormGroup;
  tags: string[] = [];
  newTag: string = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private postService: PostService
  ) {}

  ngOnInit(): void {
    this.postForm = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(3)]],
      content: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(5000)]],
      img: [null, Validators.required],  // You can add a URL pattern validator if needed
      postedBy: [null, [Validators.required, Validators.pattern('^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$')]]
    });
  }

  // Called when user presses Enter or clicks the "Add Tag" button.
  addTag(): void {
    const tag = this.newTag.trim();
    if (tag && !this.tags.includes(tag)) {
      this.tags.push(tag);
    }
    this.newTag = '';
  }

  removeTag(tag: string): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  createPost(): void {
    if (this.postForm.invalid) {
      return;
    }
    const data = this.postForm.value;
    data.tags = this.tags;
    console.log("Data sent:", data);

    this.postService.createPost(data).subscribe(
      (res) => {
        alert("Post Created Successfully!");
        this.router.navigateByUrl("/posts");
      },
      (error) => {
        console.error("Error creating post:", error);
        alert("Something Went Wrong!");
      }
    );
  }
}
