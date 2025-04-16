import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Training } from '../models/training.model'; // Assurez-vous du bon chemin


@Injectable({
  providedIn: 'root'
})
export class TrainingService {
  private apiUrl = 'http://localhost:8086/trainings'; // Corrected to use the base endpoint, not '/add'

  constructor(private http: HttpClient) {}

  // Méthode pour ajouter une formation
  addTraining(training: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, training); // No need to append '/add', since it's handled by @PostMapping directly
  }

 // Méthode pour récupérer toutes les formations
 getTrainings(): Observable<Training[]> {
  return this.http.get<Training[]>(this.apiUrl); // L'URL de ton backend
}


  
 // Méthode pour supprimer une formation
 deleteTraining(id: number): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/${id}`); // Envoie une requête DELETE pour supprimer une formation
}
getTrainingById(id: number): Observable<Training> {
  return this.http.get<Training>(`${this.apiUrl}/${id}`);
}

updateTraining(id: number, training: Partial<Training>): Observable<Training> {
  return this.http.put<Training>(`${this.apiUrl}/${id}`, training);
}

getStudentTrainings(studentId: number): Observable<any> {
  return this.http.get(`http://localhost:8086/students/${studentId}/trainings`);
}


}
