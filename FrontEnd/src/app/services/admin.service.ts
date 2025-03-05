import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASIC_URL = 'http://localhost:8089/api/admin/';

@Injectable({
    providedIn: 'root'
})
export class AdminService {
    private notificationSocket!: WebSocket; // Ajout de "!"

    constructor(private http: HttpClient) { }

    getAllPosts(): Observable<any[]> {
        return this.http.get<any[]>(`${BASIC_URL}posts`);
    }

    getPostById(postId: number): Observable<any> {
        return this.http.get<any>(`${BASIC_URL}posts/${postId}`);
    }

    getCommentsByPost(postId: number): Observable<any[]> {
        return this.http.get<any[]>(`${BASIC_URL}posts/${postId}/comments`);
    }

    deletePost(postId: number): Observable<any> {
        return this.http.delete(`${BASIC_URL}posts/${postId}`, { responseType: 'text' });
    }

    deleteComment(commentId: number): Observable<any> {
        return this.http.delete(`${BASIC_URL}comments/${commentId}`, { responseType: 'text' });
    }

    getCommentsPerPost(): Observable<any> {
        return this.http.get<any>('http://localhost:8089/api/comments/comments-per-post');
    }

    // Nouvelle fonction pour écouter les notifications WebSocket
    connectToNotifications(): Observable<string> {
        return new Observable(observer => {
            this.notificationSocket = new WebSocket('ws://localhost:8089/notifications'); // Initialisation correcte

            this.notificationSocket.onmessage = (event) => {
                observer.next(event.data);
            };

            this.notificationSocket.onerror = (error) => {
                console.error('WebSocket Error:', error);
            };

            return () => this.notificationSocket.close();
        });
    }
}
