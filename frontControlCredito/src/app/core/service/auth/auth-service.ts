import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/auth/'

  constructor( private http: HttpClient) { }


  login(data: any): Observable<any> {
    return this.http.post(this.apiUrl + 'login', data)
  } 
}
