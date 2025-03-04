import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-view-all',
  templateUrl: './view-all.component.html',
  styleUrls: ['./view-all.component.css']
})
export class ViewAllComponent implements OnInit {
  
  allPosts: any[] = [];
  filteredPosts: any[] = [];
  page: number = 1;
  pageSize: number = 3;
  totalPages: number = 0;
  searchQuery: string = '';

  speechSynthesis: SpeechSynthesis = window.speechSynthesis; // Speech Synthesis API
  currentUtterance: SpeechSynthesisUtterance | null = null;

  constructor(private postService: PostService, private router: Router) {}

  ngOnInit() {
    this.getAllPosts();
  }

  getAllPosts() {
    this.postService.getAllPosts().subscribe(
      (res) => {
        console.log("Données reçues :", res);
        this.allPosts = res.map((post: any) => ({
          ...post,
          content: post.content || ""
        }));
        this.filteredPosts = [...this.allPosts];
        this.calculateTotalPages();
      },
      error => {
        alert("Something Went Wrong !!!!");
      }
    );
  }

  filterPosts() {
    if (this.searchQuery) {
      this.filteredPosts = this.allPosts.filter(post =>
        post.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        post.content.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      this.filteredPosts = [...this.allPosts];
    }
    this.calculateTotalPages();
  }

  calculateTotalPages() {
    this.totalPages = Math.ceil(this.filteredPosts.length / this.pageSize);
  }

  goToPage(pageNumber: number) {
    if (pageNumber >= 1 && pageNumber <= this.totalPages) {
      this.page = pageNumber;
    }
  }

  nextPage() {
    if (this.page < this.totalPages) {
      this.page++;
    }
  }

  previousPage() {
    if (this.page > 1) {
      this.page--;
    }
  }

  deletePost(postId: number) {
    if (confirm("Voulez-vous vraiment supprimer cet article ?")) {
      this.postService.deletePost(postId).subscribe(
        (response: any) => { 
          if (response === "Post supprimé avec succès") {
            this.allPosts = this.allPosts.filter((post: any) => post.id !== postId);
            this.filteredPosts = this.filteredPosts.filter((post: any) => post.id !== postId);
            alert("Article supprimé avec succès");
            this.calculateTotalPages();
          } else {
            alert("Erreur lors de la suppression");
          }
        },
        error => {
          console.error("Erreur lors de la suppression :", error);
          alert("Erreur lors de la suppression");
        }
      );
    }
  }

  goToUsernamePage(): void {
    this.router.navigate(['/client/blog/chat']);
  }

  // Methods for Speech Synthesis
  readPost(content: string) {
    this.stopReading(); // Stop any ongoing reading
    this.currentUtterance = new SpeechSynthesisUtterance(content);
    this.currentUtterance.lang = 'fr-FR'; // Set language to French
    this.currentUtterance.rate = 1; // Reading rate
    this.speechSynthesis.speak(this.currentUtterance);
  }

  stopReading() {
    if (this.speechSynthesis.speaking) {
      this.speechSynthesis.cancel();
    }
  }

  generateSummary(postId: number) {
    this.postService.getSummary(postId).subscribe(
      (summary: string) => {
        const post = this.allPosts.find(p => p.id === postId);
        if (post) {
          post.summary = summary;
          post.showSummary = true; // Show the summary alert

          // Hide the summary alert after 30 seconds
          setTimeout(() => {
            post.showSummary = false;
          }, 30000);
        }
      },
      error => {
        alert("Erreur lors de la génération du résumé");
      }
    );
  }
}
