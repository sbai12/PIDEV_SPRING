import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CourseContentService {

  private apiUrl = 'http://localhost:8082/courses'; // Remplace par l'URL correcte de ton API

  constructor(private http: HttpClient) { }

  // Récupérer les fichiers d'un cours
  getCourseFiles(courseId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${courseId}/files`);
  }

  // Ajouter un fichier à un cours
  uploadCourseFile(courseId: number, file: FormData): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${courseId}/files`, file);
  }
}
