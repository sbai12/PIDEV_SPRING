import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-course',
  templateUrl: './edit-course.component.html',
  styleUrls: ['./edit-course.component.css']
})
export class EditCourseComponent implements OnInit {
  courseForm: FormGroup;
  courseId: number=0;

  constructor(
    private activatedRoute: ActivatedRoute,
    private http: HttpClient,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.courseForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      duration: ['', Validators.required],
      contentType: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Récupérer l'ID du cours depuis l'URL
    this.courseId = +this.activatedRoute.snapshot.paramMap.get('id')!;
    
    // Charger les données du cours
    this.loadCourseData();
  }

  // Charger les données du cours dans le formulaire
  loadCourseData(): void {
    this.http.get<any>(`http://localhost:8082/courses/${this.courseId}`).subscribe(course => {
      this.courseForm.patchValue({
        title: course.title,
        description: course.description,
        duration: course.duration,
        contentType: course.contentType
      });
    });
  }

  // Méthode pour soumettre le formulaire
  updateCourse(): void {
    if (this.courseForm.valid) {
      this.http.put(`http://localhost:8082/courses/${this.courseId}`, this.courseForm.value).subscribe(() => {
        alert('Course updated successfully!');
        this.router.navigate(['/courses']); // Redirection vers la liste des cours
      });
    } else {
      alert('Please fill out all fields!');
    }
  }
}
