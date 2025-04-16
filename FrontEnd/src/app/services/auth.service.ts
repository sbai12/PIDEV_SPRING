import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8086/users'; // Ton backend

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { email, password });
  }

  register(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  registerStudent(student: any): Observable<any> {
    return this.http.post(`http://localhost:8086/students/register`, student);
  }
  
  registerAdmin(admin: any): Observable<any> {
    return this.http.post(`http://localhost:8086/admins/register`, admin);
  }
  
}
