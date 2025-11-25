import { Component } from '@angular/core';
import { Address, Gender, Language, Role, User } from '../user/user.model';
import { UserService } from '../user/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FileService } from '../file/file.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  address: Address = {};
  registerUserForm: FormGroup;
  genders?: Gender[];
  roles?: Role[];
  languages?: Language[];

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private fileService: FileService
  ) {
    const user = new User();
    this.registerUserForm = this.fb.group({
      user_id: user.user_id,
      name: user.name,
      first_name: user.first_name,
      last_name: user.last_name,
      phone_number: user.phone_number,
      gender: user.gender,
      role: user.role,
      base64_files: user.base64_files,
      language: user.language,
      files: user.files,
      address: this.fb.group({
        id: this.address.id,
        street: this.address.street,
        city: this.address.city,
        pincode: this.address.pincode
      }),
      user_name: user.user_name,
      password: [user.password, Validators.required],
      access_token: user.access_token
    });
  }

  registerUser() {
    let user = this.registerUserForm.value;
    this.userService.registerUser(user).subscribe(res => {
      // localStorage.setItem('jwt', res.jwt!);
      this.router.navigate(['/products']);
    });
  }

}
