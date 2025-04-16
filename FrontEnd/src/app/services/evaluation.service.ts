import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Evaluation } from '../models/evaluation.model';  // Importer le modèle

@Injectable({
  providedIn: 'root'
})
export class EvaluationService {

  // URL de l'API pour les évaluations
  private apiUrl = 'http://localhost:8086/api/evaluations';

  constructor(private http: HttpClient) { }

  submitEvaluation(evaluation: Evaluation): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add`, evaluation).pipe(
      catchError(error => {
        console.error('Error submitting review:', error);
        throw error;
      })
    );
  }

  getAverageRating(idFormation: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/moyenne/${idFormation}`).pipe(
      catchError(error => {
        console.error('Error fetching average rating:', error);
        return [0]; // Default value on error
      })
    );
  }
}
