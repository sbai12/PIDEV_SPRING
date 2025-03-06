import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent {
openFileDialog() {
throw new Error('Method not implemented.');
}
  courseForm: FormGroup;
  file!: File;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.courseForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      duration: [0, Validators.required],
      contentType: ['', Validators.required],
      file: [null]
    });
  }

  onSubmit(): void {
    if (this.courseForm.valid) {
      const formData = new FormData();
      formData.append('file', this.file!, this.file?.name);
      formData.append('title', this.courseForm.value.title);
      formData.append('description', this.courseForm.value.description);
      formData.append('duration', this.courseForm.value.duration.toString());
      formData.append('contentType', this.courseForm.value.contentType);
  
      // Appel de la méthode pour envoyer les données
      this.addCourseWithFile(formData);
    } else {
      console.log('Formulaire invalide');
      this.courseForm.markAllAsTouched(); // Afficher les erreurs
    }
  }

  addCourseWithFile(formData: FormData): void {
    // Envoie les données via HTTP
    this.http.post('votre-URL-d-API', formData).subscribe(
      (response) => {
        console.log('Cours ajouté avec succès', response);
      },
      (error) => {
        console.error('Erreur lors de l\'ajout du cours', error);
      }
    );
  }

  onFileSelected(event: any): void {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.file = fileInput.files[0];
      console.log('Fichier sélectionné:', this.file.name);
    }
  }
}
