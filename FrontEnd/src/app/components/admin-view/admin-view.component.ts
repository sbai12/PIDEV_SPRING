import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-admin-view',
  templateUrl: './admin-view.component.html',
  styleUrls: ['./admin-view.component.css']
})
export class AdminViewComponent implements OnInit {
  posts: any[] = [];
  filteredPosts: any[] = [];
  searchTerm: string = '';
  notifications: string[] = [];
  unreadCount: number = 0;

  constructor(
    private adminService: AdminService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getPosts();
    this.listenForNotifications();
  }

  getPosts(): void {
    this.adminService.getAllPosts().subscribe(
      (posts: any[]) => {
        this.posts = posts;
        this.filteredPosts = posts;
      },
      error => {
        window.alert("Erreur lors du chargement des posts");
      }
    );
  }

  deletePost(postId: number): void {
    if (confirm("Voulez-vous vraiment supprimer ce post ?")) {
      this.adminService.deletePost(postId).subscribe(
        () => {
          this.posts = this.posts.filter(post => post.id !== postId);
          this.filteredPosts = this.filteredPosts.filter(post => post.id !== postId);
          window.alert("Post supprimé avec succès");
        },
        error => {
          window.alert("Erreur lors de la suppression");
        }
      );
    }
  }

  viewPost(postId: number): void {
    this.router.navigate(['/admin/post', postId]);
  }

  goToStatistics(): void {
    this.router.navigate(['/admin/statistic']);
  }

  filterPosts(): void {
    this.filteredPosts = this.posts.filter(post =>
      post.id.toString().includes(this.searchTerm) ||
      post.content.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  listenForNotifications(): void {
    this.adminService.connectToNotifications().subscribe(message => {
      this.notifications.push(message);
      this.unreadCount++;
      window.alert(message);
    });
  }

  clearNotifications(): void {
    this.notifications = [];
    this.unreadCount = 0;
  }

  
}
