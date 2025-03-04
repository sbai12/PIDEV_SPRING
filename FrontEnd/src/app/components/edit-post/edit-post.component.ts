import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {

  editForm!: FormGroup;
  postId!: number;

  constructor(
    private fb: FormBuilder,
    private postService: PostService,
    private route: ActivatedRoute,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.postId = Number(this.route.snapshot.paramMap.get('id'));

    this.editForm = this.fb.group({
      name: [''],
      content: [''],
      postedBy: [''],
      img: ['']
    });

    this.loadPostData();
  }

  loadPostData(): void {
    this.postService.getPostById(this.postId).subscribe(post => {
      this.editForm.patchValue(post);
    });
  }

  updatePost(): void {
    if (this.editForm.valid) {
      this.postService.updatePost(this.postId, this.editForm.value).subscribe(() => {
        this.router.navigate(['/viewall']);
      });
    }
  }
}
