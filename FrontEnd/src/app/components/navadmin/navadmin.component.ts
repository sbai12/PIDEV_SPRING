import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navadmin',
  templateUrl: './navadmin.component.html',
  styleUrls: ['./navadmin.component.css']
})
export class NavadminComponent {
  isSidebarOpen = true;

  constructor(private router: Router) {}

  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
    console.log('Sidebar toggled, isSidebarOpen:', this.isSidebarOpen);
  }

  onLogout(): void {
    console.log('Logout clicked');
    // Example: this.router.navigate(['/login']);
  }

  checkNotifications(): void {
    console.log('Notifications clicked');
  }

  goToProfile(): void {
    console.log('User info clicked');
  }
}
