import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Course } from '../models/courses.model';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {
  private apiUrl = 'http://localhost:8082/courses'; 

  constructor(private http: HttpClient) {}

  // Récupérer tous les cours
  getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.apiUrl}/getall-courses`);
  }



  // Ajouter un cours
  addCourse(course: Course): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add-course`, course);
  }

  addCourseWithFile(course: Course, file: File): Observable<Course> {
    const formData = new FormData();
    formData.append('course', JSON.stringify(course));  // Convertir le cours en chaîne JSON
    formData.append('file', file);

    return this.http.post<Course>(`${this.apiUrl}/add`, formData);  // Adapte l'URL de l'API
  }

  // Mettre à jour un cours
  updateCourse(courseId: number, course: Course): Observable<Course> {
    return this.http.put<Course>(`${this.apiUrl}/update/${courseId}`, course);
  }



  // Upload d'un fichier pour un cours
  uploadCourseFile(courseId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post(`${this.apiUrl}/${courseId}/upload`, formData);
  }

  getCourseFile(courseId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${courseId}/file`, { responseType: 'blob' });
  }

  // Générer le PDF d'un cours
  genererPdfCours(idCours: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/genererPdf/${idCours}`, { responseType: 'blob' });
  }

  // Récupérer les détails d'un cours
  getCourseDetails(courseId: number): Observable<Course> {
    return this.http.get<Course>(`${this.apiUrl}/get-Course/${courseId}`);
  }
  getCourseById(courseId: number): Observable<Course> {
    return this.http.get<Course>(`${this.apiUrl}/get-Course/${courseId}`);
  }
  
  deleteCourse(courseId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${courseId}`);
  }
  
}
