import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  firstName = '';
  lastName = '';
  email = '';
  password = '';
  confirmPassword = '';
  role = ''; // 'STUDENT' or 'ADMIN'

  errorMessage = '';
  successMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      this.successMessage = '';
      return;
    }

    const newUser = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      password: this.password
    };

    if (this.role === 'STUDENT') {
      this.authService.registerStudent(newUser).subscribe(
        (student) => {
          localStorage.setItem('user_id', student.id); // pour charger ses données ensuite
          this.router.navigate(['/']); // ✅ redirection vers la page d'accueil
        },
        () => this.errorMessage = 'Student registration failed.'
      );
    } else if (this.role === 'ADMIN') {
      this.authService.registerAdmin(newUser).subscribe(
        () => this.router.navigate(['/admin']),
        () => this.errorMessage = 'Admin registration failed.'
      );
    } else {
      this.errorMessage = 'Please choose a role.';
    }
  }
}