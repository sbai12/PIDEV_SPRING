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
  getTrainings(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  
 // Méthode pour supprimer une formation
 deleteTraining(id: number): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/${id}`); // Envoie une requête DELETE pour supprimer une formation
}

// Méthode pour mettre à jour une formation
updateTraining(id: number, training: Training): Observable<Training> {
  return this.http.put<Training>(`${this.apiUrl}/${id}`, training); // Envoie une requête PUT pour mettre à jour une formation
} 
}
