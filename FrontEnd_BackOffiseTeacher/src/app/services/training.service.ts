import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TrainingService {
  private apiUrl = 'http://localhost:8086/trainings'; // URL de ton backend Spring Boot pour les formations

  constructor(private http: HttpClient) {}

  // Générer un lien de réunion
  generateMeetingLink(idForm: number, dateSession: string): Observable<string> {
    return this.http.post(`${this.apiUrl}/generate-meeting-link?idForm=${idForm}&dateSession=${dateSession}`, null, { responseType: 'text' });
  }

  // Envoyer des invitations
  sendInvitations(idForm: number, idEtudiants: number[], dateSession: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/send-invitations?idForm=${idForm}&dateSession=${dateSession}`, idEtudiants);
  }

  // Récupérer toutes les formations
  getAllTrainings(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  // (Optionnel) Ajouter une formation (si ton backend le supporte)
  addTraining(training: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, training);
  }


  // Méthode pour inscrire un étudiant à une formation
registerStudentToTraining(trainingId: number, studentId: number): Observable<any> {
  return this.http.post(`${this.apiUrl}/register?trainingId=${trainingId}&studentId=${studentId}`, {});
}

  

}