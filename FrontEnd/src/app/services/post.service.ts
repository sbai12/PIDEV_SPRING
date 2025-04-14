import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Post } from '../models/post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  // Adjust the URL and port as necessary for your backend
  private apiUrl = 'http://localhost:8089/api/posts';

  constructor(private http: HttpClient) {}
  updatePost(postId: number, postData: any): Observable<any> {
    return this.http.put(`http://localhost:8089/api/posts/${postId}`, postData)

  }
  getSummary(postId: number): Observable<string> {
    return this.http.get(`http://localhost:8089/api/posts/${postId}/summary`, { responseType: 'text' });
  }
  
  deletePost(postId: number) {
    return this.http.delete(`http://localhost:8089/api/posts/${postId}`, { responseType: 'text' });
  }
  
  addPost(post: Post, selectedFile: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', selectedFile, selectedFile.name);  // Add the file to FormData
    formData.append('postData', JSON.stringify(post));  // Add the post data as a JSON string
  
    // Send the FormData to the backend using POST method
    return this.http.post(`${this.apiUrl}/addPost`, formData)
      .pipe(
        catchError(this.handleError) // Handle any errors here if necessary
      );
  }
  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error); // Log the error to the console
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
    


  // Retrieve all posts
  getAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/getPost`);
  }

  // Get a single post by its ID (this will also increment the view count on the backend)
  getPostById(postId: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/get/${postId}`);
  }

  // Like a post
  likePost(postId: number): Observable<any> {
    // Note: We're sending null as the body because the backend only needs the path variable
    return this.http.put(`${this.apiUrl}/${postId}/like`, null);
  }

  // Search posts by name
  searchByName(name: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/search/${name}`);
  }
}
