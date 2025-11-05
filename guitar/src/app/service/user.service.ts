import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { BaseHttpService } from './base-http.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseHttpService {

  constructor(http: HttpClient) {
    super(http, '/api/user');
  }

  // Method to add a new user
  addUser(user: User): Observable<User> {
    return this.add(user); // Calls the inherited add method
  }

  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/users`);
  }


  signIn(email: string, password: string): Observable<any> {
    const loginData = { email, password };
    return this.add(loginData);
  }
}
