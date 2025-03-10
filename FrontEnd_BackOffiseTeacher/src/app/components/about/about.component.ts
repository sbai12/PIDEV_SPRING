import { Component } from '@angular/core';

import { PostService } from 'src/app/services/post.service';  
import { Post } from 'src/app/models/post.model'; 
@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent {
 post: Post = new Post(0, '', '', '', '', new Date(), 0, 0);  // Initialise le modèle Post

  constructor(private postService: PostService) {}

  // Méthode pour créer un post en appelant le service
  createPost() {
    this.postService.addPost(this.post).subscribe(
      (createdPost) => {
        console.log('Post créé avec succès', createdPost);
        // Vous pouvez rediriger ou afficher un message de succès ici
      },
      (error) => {
        console.error('Erreur lors de la création du post', error);
        // Gérer l'erreur si nécessaire
      }
    );
  }
}
