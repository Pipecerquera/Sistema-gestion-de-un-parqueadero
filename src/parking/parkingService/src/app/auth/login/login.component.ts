import { Component } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { RouterModule, Router, RouterLink } from '@angular/router';
import { FormsModule, NgForm, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loading = false;
  errorMessage: string | null = null;
  isLoginMode = true;

  constructor(private http: HttpClient, private router: Router) { }

  login(loginForm: NgForm) {
    if (!loginForm.valid) {
      return;
    }

    this.loading = true;
    this.errorMessage = null;

    const payload = {
      email: loginForm.value.email,
      password: loginForm.value.password,
    };

    this.http.post('http://localhost:9000/api/auth/auth', payload)
      .subscribe((data: any) => {
        this.loading = false;

        if (data.estado) {
          localStorage.setItem('token', data.token);
          localStorage.setItem('userId', data.id);
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('user', JSON.stringify(data));
          this.router.navigate(['/dashboard']);
        } else {
          this.errorMessage = data.message || 'Email o contraseña incorrectos';
          localStorage.setItem('isLoggedIn', 'false');
        }
      }, error => {
        this.loading = false;
        this.errorMessage = error.message || 'Ocurrió un error al iniciar sesión.';
        localStorage.setItem('isLoggedIn', 'false');
      });
  }

  register(registerForm: NgForm) {
    if (!registerForm.valid) {
      return;
    }

    this.loading = true;
    this.errorMessage = null;

    const payload = {
      photo: 'https://example.com/photo.jpg',
      fullname: registerForm.value.fullname,
      password: registerForm.value.password,
      email: registerForm.value.email,
      role: 'user',
      locate: registerForm.value.locate,
      last_login: new Date().toISOString(),
      created_at: new Date().toISOString(),
      updated_at: new Date().toISOString(),
      status: true,
      notifications: false
    };

    this.http.post('http://localhost:9000/api/auth/add', payload)
      .subscribe((data: any) => {
        this.loading = false;

        if (data.estado) {
          setTimeout(() => {
            this.router.navigate(['/auth']);
            location.reload(); // Recarga la página después de redirigir
          }, 2000);
        } else {
          this.errorMessage = data.message || 'Error al registrar el usuario.';
        }
      }, error => {
        this.loading = false;
        this.errorMessage = error.message || 'Ocurrió un error al registrar.';
      });
  }

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
  }
}
