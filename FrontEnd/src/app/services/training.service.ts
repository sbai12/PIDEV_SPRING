import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TrainingService {
  private apiUrl = 'http://localhost:8086/trainings'; // Corrected to use the base endpoint, not '/add'

  constructor(private http: HttpClient) {}

  // Updated method to match the correct endpoint
  addTraining(training: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, training); // No need to append '/add', since it's handled by @PostMapping directly
  }

  getTrainings(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
