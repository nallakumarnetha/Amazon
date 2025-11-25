import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { UserService } from '../user/user.service';
// import jwtDecode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class MyAuthGuard implements CanActivate {

  constructor(private router: Router, private userService: UserService) { }

  canActivate(): boolean {
    const token = localStorage.getItem('jwt');
    if (token) {
      // let isValid: boolean = this.isTokenExpired(token);
      // if (isValid) {
        return true;
      // }
    }
    this.router.navigate(['/login']);
    return false;
  }

  // isTokenExpired(token: string): boolean {
  //   const decoded: any = jwtDecode(token);
  //   const exp = decoded.exp * 1000;
  //   return Date.now() > exp;
  // }

}
