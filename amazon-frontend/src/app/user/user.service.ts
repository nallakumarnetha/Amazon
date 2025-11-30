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

  // ============ address================
  // private addressBaseUrl = 'http://localhost:8080/amazon/addresses';

  // findAllAddresses(page: number, size: number): Observable<AddressListResponse> {
  //   return this.http.get<AddressListResponse>(`${this.addressBaseUrl}?page=${page}&size=${size}`);
  // }

  // findAddress(id: string): Observable<Address> {
  //   return this.http.get<Address>(`${this.addressBaseUrl}/${id}`);
  // }

  // addAddress(address: Address): Observable<Address> {
  //   return this.http.post<Address>(this.addressBaseUrl, address);
  // }

  // updateAddress(id: string | undefined, address: Address): Observable<Address> {
  //   return this.http.put<Address>(`${this.addressBaseUrl}/${id}`, address);
  // }

  // deleteAddress(id: string): Observable<AddressListResponse> {
  //   return this.http.delete<AddressListResponse>(`${this.addressBaseUrl}/${id}`);
  // }

  // ============ authentication ================

  // login(user: User) {
  //   this.http.post<User>(`${this.baseUrl}/login`, user);
  // }



  logout(): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/logout`, null);
  }

}
