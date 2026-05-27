import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  username = '';
  password = '';
  error: string | null = null;
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.error = null;
    this.loading = true;

    this.authService.login({ username: this.username, password: this.password }).subscribe({
      next: () => this.router.navigate(['/products']),
      error: (err) => {
        this.error =
          err.status === 401 ? 'Invalid username or password.' : 'Login failed. Please try again.';
        this.loading = false;
      },
    });
  }
}
