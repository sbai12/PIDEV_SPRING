import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EvaluationService {

  // URL de l'API pour les évaluations
  private apiUrl = 'http://localhost:8086/api/evaluations';

  constructor(private http: HttpClient) { }

  // Soumettre une évaluation
  submitEvaluation(evaluation: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add`, evaluation);
  }

  // Récupérer la moyenne des étoiles pour une formation
  getAverageRating(idFormation: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/moyenne/${idFormation}`);
  }
}
