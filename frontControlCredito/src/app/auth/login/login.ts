import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LoginRequest } from '../../core/interfaces/auth-interfaces';
import { AuthService } from '../../core/service/auth/auth-service';
import { TokenService } from '../../core/service/token/token-service';

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  username = '';
  password = '';
  errorLogin = false;

  constructor(
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  onSubmit() {
    const request: LoginRequest = {
      usuario: this.username,
      password: this.password,
    };

    this.authService.login(request).subscribe({
      next: (response) => {
        this.tokenService.setToken(response.accessToken);
        this.router.navigate(['/dashboard/main']);
      },
      error: (err) => {
        if (err.status === 500) {
          this.errorLogin = true;
        }
      },
    });
  }
}
