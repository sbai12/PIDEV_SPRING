import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private apiUrl = 'http://localhost:8082/courses/add-course';

  constructor(private http: HttpClient) {}

  addCourse(course: any): Observable<any> {
    return this.http.post(this.apiUrl, course); 
  }
  
  
  
}
