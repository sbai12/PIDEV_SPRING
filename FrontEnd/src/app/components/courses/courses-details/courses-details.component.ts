import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../../../models/courses.model';
import { HttpErrorResponse } from '@angular/common/http';
import { CoursesService } from 'src/app/services/course.service';

@Component({
  selector: 'app-courses-details',
  templateUrl: './courses-details.component.html',
  styleUrls: ['./courses-details.component.css']
})
export class CourseDetailsComponent implements OnInit {
  courseId: number = 0;
  course: Course | null = null;
  selectedFile: File | null = null;

  constructor(
    private coursesService: CoursesService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.courseId = +this.route.snapshot.paramMap.get('id')!; // Récupère l'ID du cours depuis l'URL
    this.getCourseDetails();
  }

  // Récupérer les détails du cours depuis le backend
  getCourseDetails(): void {
    this.coursesService.getCourseById(this.courseId).subscribe(
      (course: Course) => {
        this.course = course;
      },
      (error: HttpErrorResponse) => { // Gestion d'erreurs explicite
        console.error('Erreur lors de la récupération du cours', error);
      }
    );
  }

  goToEditCourse(): void {
    this.router.navigate([`/edit-course/${this.courseId}`]); // Redirection vers la page d'édition
  }

  // Méthode pour supprimer le cours
  onDeleteCourse(): void {
    if (this.course) {
      const confirmation = confirm('Êtes-vous sûr de vouloir supprimer ce cours ?');
      if (confirmation) {
        this.coursesService.deleteCourse(this.course.id).subscribe( // Correction ici : Utilisation de this.course.id
          () => {
            alert('Cours supprimé avec succès.');
            this.router.navigate(['/courses']); // Rediriger vers la liste des cours après la suppression
          },
          (error: HttpErrorResponse) => {
            console.error('Erreur lors de la suppression du cours', error); // Gestion d'erreurs explicite
          }
        );
      }
    }
  }

  // Méthode pour ajouter un fichier
  onAddFile(event: any): void {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput?.files?.length) {
      this.selectedFile = fileInput.files[0];
      console.log('Fichier sélectionné:', this.selectedFile?.name);
      // Optionnellement, tu peux ajouter une logique pour envoyer ce fichier au backend
    }
  }

  uploadFile(): void {
    if (this.selectedFile && this.course) {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      formData.append('courseId', this.course.id.toString());

      this.coursesService.uploadCourseFile(formData).subscribe(
        () => {
          alert('Fichier ajouté avec succès.');
          this.getCourseDetails(); // Mettre à jour les détails du cours après l'ajout du fichier
        },
        (error: HttpErrorResponse) => {
          console.error('Erreur lors de l\'ajout du fichier', error);
        }
      );
    }
  }
}
