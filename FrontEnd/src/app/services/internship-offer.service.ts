import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { InternshipOffer } from '../models/InternshipOffer';

@Injectable({
  providedIn: 'root'
})
export class InternshipOfferService {
  private apiUrl = 'http://localhost:8083/internship-offers'; 

  constructor(private http: HttpClient) {}

  getInternshipOffers(): Observable<InternshipOffer[]> {
    return this.http.get<InternshipOffer[]>(this.apiUrl);
  }

  getInternshipOfferById(id: number): Observable<InternshipOffer> {
    return this.http.get<InternshipOffer>(`${this.apiUrl}/${id}`);
  }

  createInternshipOffer(offer: InternshipOffer): Observable<InternshipOffer> {
    return this.http.post<InternshipOffer>(this.apiUrl, offer);
  }

  updateInternshipOffer(id: number, offer: InternshipOffer): Observable<InternshipOffer> {
    return this.http.put<InternshipOffer>(`${this.apiUrl}/${id}`, offer);
  }

  deleteInternshipOffer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  assignCandidate(offerId: number, candidateId: number): Observable<InternshipOffer> {
    return this.http.put<InternshipOffer>(`${this.apiUrl}/${offerId}/assign-candidate/${candidateId}`, {});
  }

  assignCompany(offerId: number, companyId: number): Observable<InternshipOffer> {
    return this.http.put<InternshipOffer>(`${this.apiUrl}/${offerId}/assign-company/${companyId}`, {});
  }

  updateOfferStatus(offerId: number, newStatus: string): Observable<InternshipOffer> {
    const params = new HttpParams().set('newStatus', newStatus);
    return this.http.put<InternshipOffer>(`${this.apiUrl}/${offerId}/update-status`, {}, { params });
  }
}
