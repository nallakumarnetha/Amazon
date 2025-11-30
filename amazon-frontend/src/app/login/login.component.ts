import { Component, Renderer2 } from '@angular/core';
import { Address, User } from '../user/user.model';
import { UserService } from '../user/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Constants } from '../common/constants';
import { CartService } from '../cart/cart.service';
import { PreferencesService } from '../preferences/preferences.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  // user: User = {};
  address: Address = {};
  loginUserForm: FormGroup;
  submitted = false;
  serverError = '';

  constructor(private userService: UserService, private fb: FormBuilder,
    private router: Router, private renderer: Renderer2, private cartService: CartService,
  private preferencesService: PreferencesService) {
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
      user_name: ['', Validators.required],
      password: ['', Validators.required],
      // jwt: user.jwt
    });
  }

  loginUser() {
    this.submitted = true;
    this.serverError = '';
    if (this.loginUserForm.invalid) {
      return;
    }
    const user = this.loginUserForm.value;
    this.userService.login(user).subscribe({
      next: (res: any) => {
        // this.preferencesService.findPreferences();
        this.router.navigate(['/home']).then(() => {
          window.location.reload();
        });
      },
      error: (err: any) => {
        this.serverError = err.error?.message || 'Login failed';
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
    window.location.assign(url);
  }

  goHome() {
    this.router.navigate(['/home']);
  }

  ngOnInit() {
    // hide menu & footer globally
    this.renderer.addClass(document.body, 'full-page');
  }

  ngOnDestroy() {
    // show them again when leaving this page
    this.renderer.removeClass(document.body, 'full-page');
  }

}
