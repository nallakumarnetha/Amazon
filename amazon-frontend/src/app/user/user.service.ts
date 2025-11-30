import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Address, User } from './user.model';
import { Router } from '@angular/router';

export interface UserListResponse {
  users: User[];
  total: number;
}

export interface AddressListResponse {
  addresses: Address[];
  total: number;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/amazon/users';

  constructor(private http: HttpClient, private router: Router) { }

  findAllUsers(page: number, size: number): Observable<UserListResponse> {
    return this.http.get<UserListResponse>(`${this.baseUrl}?page=${page}&size=${size}`);
  }

  findUser(id: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${id}`);
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.baseUrl, user);
  }

  updateUser(id: string | undefined, user: User): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${id}`, user);
  }

  deleteUser(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/current-user`);
  }

  login(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/login`, user);
  }

  registerUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/register`, user);
  }

  logout(): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/logout`, null);
  }

}
