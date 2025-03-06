import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-course-content',
  templateUrl: './course-content.component.html',
  styleUrls: ['./course-content.component.css']
})
export class CourseContentComponent implements OnInit {

  fileContent: any;
  loading: boolean = true;
  error: string | null = null;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    const courseId = 67;  // Exemple d'ID de cours
    this.getCourseFile(courseId);
  }

  getCourseFile(courseId: number): void {
    this.http.get(`http://localhost:8082/courses/${courseId}/file`, { responseType: 'arraybuffer' })
      .subscribe(
        (response: any) => {
          this.fileContent = response;  // Le fichier est stocké en tant que ArrayBuffer
          this.loading = false;  // Arrêt du chargement
        },
        error => {
          console.error('Error fetching file', error);
          this.error = 'Erreur lors du chargement du fichier. Veuillez réessayer plus tard.';  // Message d'erreur
          this.loading = false;  // Arrêt du chargement
        }
      );
  }

  downloadFile(): void {
    if (!this.fileContent) {
      console.error('File content is not loaded');
      return;
    }

    // Créer un Blob à partir du contenu du fichier
    const blob = new Blob([this.fileContent], { type: 'application/pdf' });

    // Créer un lien temporaire pour le téléchargement
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'course-file.pdf';
    link.click();  // Simule un clic pour démarrer le téléchargement
  }
}
