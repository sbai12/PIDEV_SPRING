import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteUserComponent } from '../delete-user/delete-user.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  users: User[] = [];
  searchTerm = '';
  showModal = false;
  isEditMode = false;
  selectedUser: User = {
    firstname: '',
    lastname: '',
    email: '',
    password: '',
    specialite: '',
    competence: '',
    niveau: '',
    phone: '',
    status: '',
    role: 'Candidate'
  };
  isSidebarOpen = true;
  roles: string[] = ['Admin', 'Supervisor', 'Candidate', 'Teacher', 'Representant']; // Role list
  selectedRole: string = ''; // Stores selected role

  constructor(private userService: UserService, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadUsers();
  }
  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
    console.log('Sidebar toggled, isSidebarOpen:', this.isSidebarOpen);
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe((data) => {
      console.log("Fetched Users:", data);
      this.users = data;
    });
  }

  filterUsersByRole() {
    if (this.selectedRole) {
      this.userService.getUsersByRole(this.selectedRole).subscribe((data) => {
        this.users = data;
      });
    } else {
      this.loadUsers(); // If "All Roles" is selected, fetch all users
    }
  }

  filteredUsers() {
    return this.users.filter((user) =>
      `${user.firstname} ${user.lastname}`.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  openModal(user: User | null = null) {
    this.isEditMode = !!user;
    this.selectedUser = user ? { ...user } : { firstname: '', lastname: '', email: '', password: '', role: 'Candidate' };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  saveUser() {
    if (this.isEditMode && this.selectedUser.id != null) {
      this.userService.updateUser(this.selectedUser.id, this.selectedUser).subscribe(() => {
        this.loadUsers();
        this.closeModal();
      });
    } else {
      this.userService.addUser(this.selectedUser).subscribe((newUser) => {
        if (newUser && newUser.id != null) {
          this.users.push(newUser);
        }
        this.closeModal();
      });
    }
  }

  deleteUser(id?: number, name?: string) {
    if (!id) {
      console.error('Invalid user ID: Cannot delete!');
      return;
    }

    const dialogRef = this.dialog.open(DeleteUserComponent, {
      width: '400px',
      maxWidth: '90vw',
      disableClose: true,
      hasBackdrop: true,
      autoFocus: false,
      data: { companyName: name }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.userService.deleteUser(id).subscribe(() => {
          console.log(`Deleted user with ID: ${id}`);
          this.loadUsers();
        });
      }
    });
  }
}
