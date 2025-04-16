import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  errorMessage = '';
  isNotRegistered = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.login(this.email, this.password).subscribe(
      (res) => {
        if (res.role === 'ADMIN') {
          this.router.navigate(['/admin']);
        } else if (res.role === 'STUDENT') {
          localStorage.setItem('user_id', res.id); // ⬅ Sauvegarder l'ID étudiant
          this.router.navigate(['/']);
        }
        
      },
      (err) => {
        if (err.status === 404) {
          this.isNotRegistered = true;
          this.errorMessage = '';
        } else if (err.status === 401) {
          this.errorMessage = 'Invalid email or password.';
          this.isNotRegistered = false;
        }
      }
    );
  }
}