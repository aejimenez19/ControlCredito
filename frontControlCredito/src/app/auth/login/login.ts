import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import {FormsModule} from '@angular/forms'
import { LoginRequest } from '../../core/interfaces/auth-interfaces';
import { AuthService } from '../../core/service/auth/auth-service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  username = '';
  password = '';


  constructor(private authService: AuthService){}

  onSubmit() {
    const request: LoginRequest = {
      usuario: this.username, 
      password: this.password
    }

    this.authService.login(request).subscribe({
      next: (response) => {
        console.log('Respuesta del back: ', response)
      },
      error: (err) => {
        console.log('Error en el login: ', err)
      }
    })


  }
}
