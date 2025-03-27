import { Injectable } from '@angular/core';
import{HttpClient} from '@angular/common/http';
import { Post } from '../models/post.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private baseUrl='http://localhost:8089/api/posts';

  constructor(private http:HttpClient) {}

   // Fonction pour ajouter un post
   addPost(post: Post): Observable<Post> {
    return this.http.post<Post>(`${this.baseUrl}/addPost`, post);
  }

}
