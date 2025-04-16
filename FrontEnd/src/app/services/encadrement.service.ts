import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Encadrement } from '../models/Encadrement';

@Injectable({
  providedIn: 'root'
})
export class EncadrementService {
  private apiUrl = 'http://localhost:8083/encadrements'; 

  constructor(private http: HttpClient) {}

  getEncadrements(): Observable<Encadrement[]> {
    return this.http.get<Encadrement[]>(this.apiUrl);
  }

  getEncadrementById(id: number): Observable<Encadrement> {
    return this.http.get<Encadrement>(`${this.apiUrl}/${id}`);
  }

  createEncadrement(encadrement: Encadrement): Observable<Encadrement> {
    return this.http.post<Encadrement>(this.apiUrl, encadrement);
  }

  updateEncadrement(id: number, encadrement: Encadrement): Observable<Encadrement> {
    return this.http.put<Encadrement>(`${this.apiUrl}/${id}`, encadrement);
  }

  deleteEncadrement(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
