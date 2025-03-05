import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from '../models/comment.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  // Base URL for comment endpoints; note that the GET for comments is on "/comments/comments/{postId}"
  private apiUrl = 'http://localhost:8089/api/comments';

  constructor(private http: HttpClient) {}
  filterInappropriateWords(content: string): string {
    const inappropriateWords = ['badword1', 'badword2', 'badword3']; // Liste des mots inappropriés
    let filteredContent = content;
    inappropriateWords.forEach(word => {
      const regex = new RegExp(`\\b${word}\\b`, 'gi');  // Mot complet et insensible à la casse
      filteredContent = filteredContent.replace(regex, '*****');  // Remplacer par des astérisques
    });

    return filteredContent;
  }

  // Create a new comment for a given post
  createComment(postId: number, postedBy: string, content: string): Observable<Comment> {
    const filteredContent = this.filterInappropriateWords(content);  // Filtrer avant l'envoi
    const params = new HttpParams()
      .set('postId', postId.toString())
      .set('postedBy', postedBy);
    return this.http.post<Comment>(this.apiUrl, content, { params });
  }

  // Get all comments for a specific post
  getCommentsByPostId(postId: number): Observable<Comment[]> {
    // Based on your backend, the endpoint is /api/comments/comments/{postId}
    return this.http.get<Comment[]>(`${this.apiUrl}/comments/${postId}`);
  }

  deleteComment(commentId: number): Observable<any> {
    return this.http.delete(`http://localhost:8089/api/comments/comments/${commentId}`);
  }
}
