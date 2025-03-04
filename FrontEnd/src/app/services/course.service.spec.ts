import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {
  private apiUrl = 'http://localhost:8082/courses/add-course';  // Assurez-vous que l'URL est correcte

  constructor(private http: HttpClient) {}

  addCourse(course: any): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('token')  // Assurez-vous que le token est dans le localStorage
    });

    return this.http.post(this.apiUrl, course, { headers });  // Vérifiez que cette URL correspond à celle utilisée avec Insomnia
  }
}
