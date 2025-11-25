import { Component } from '@angular/core';
import { Address, User } from '../user/user.model';
import { UserService } from '../user/user.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Constants } from '../common/constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  // user: User = {};
  address: Address = {};
  loginUserForm: FormGroup;

  constructor(private userService: UserService, private fb: FormBuilder,
    private router: Router) {
    const user = new User();
    this.loginUserForm = this.fb.group({
      // user_id: user.user_id,
      // name: user.name,
      // first_name: user.first_name,
      // last_name: user.last_name,
      // phone_number: user.phone_number,
      // gender: user.gender,
      // role: user.role,
      // base64_files: user.base64_files,
      // language: user.language,
      // files: user.files,
      // address: this.fb.group({
      //   id: this.address.id,
      //   street: this.address.street,
      //   city: this.address.city,
      //   pincode: this.address.pincode
      // }),
      user_name: user.user_name,
      password: user.password,
      // jwt: user.jwt
    });
  }

  loginUser() {
    const user = this.loginUserForm.value;
    this.userService.login(user).subscribe({
      next: (res: any) => {
        this.router.navigate(['/home']);
      },
      error: (err: any) => {
        console.error('Login failed', err);
        this.router.navigate(['/login']);
      }
    });
  }

  loginWithGoogle() {
    const url = `${Constants.AUTH_URI_GOOGLE}` +
      `?client_id=${Constants.CLIENT_ID_GOOGLE}` +
      `&redirect_uri=${Constants.REDIRECT_URI_GOOGLE}` +
      `&response_type=${Constants.RESPONSE_TYPE_GOOGLE}` +
      `&scope=${Constants.SCOPE_GOOGLE}` +
      `&access_type=${Constants.ACCESS_TYPE_GOOGLE}` +
      `&prompt=${Constants.PROMPT_GOOGLE}`;
    // window.location.href = url;
    window.open(url, '_blank');
  }

  goHome() {
    this.router.navigate(['/home']);
  }

}
