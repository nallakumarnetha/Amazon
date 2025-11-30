import { ChangeDetectorRef, Component, Renderer2 } from '@angular/core';
import { Address, Gender, Language, Role, User } from '../user/user.model';
import { UserService } from '../user/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FileService } from '../file/file.service';
import { Preferences } from '../preferences/preferences.model';
import { PreferencesService } from '../preferences/preferences.service';

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
  selectedFiles: File[] = [];
  user: User = { base64_files: new Map() };
  submitted = false;
  serverError = '';

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private fileService: FileService,
    private renderer: Renderer2,
    private cd: ChangeDetectorRef,
    private preferencesService: PreferencesService
  ) {
    const user = new User();
    this.registerUserForm = this.fb.group({
      user_id: user.user_id,
      name: [user.name, Validators.required],
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
      user_name: [user.user_name, Validators.required],
      password: [user.password, [Validators.required]],
      email: [user.email, [Validators.required, Validators.email]],
      dob: user.dob
      // access_token: user.access_token
    });
  }

  // registerUser() {
  //   this.submitted = true;
  //   this.serverError = '';
  //   if (this.registerUserForm.invalid) {
  //     return;
  //   }
  //   let user = this.registerUserForm.value;
  //   this.userService.registerUser(user).subscribe(res => {
  //     // localStorage.setItem('jwt', res.jwt!);
  //     this.router.navigate(['/products']);
  //   },
  //     err => {
  //       this.serverError = err.error?.message || 'Registration failed!';
  //     });
  // }

  registerUser(): void {
    this.submitted = true;
    this.serverError = '';

    if (this.registerUserForm.invalid) {
      return;
    }

    const registerUserFun = () => {
      console.log('register user');
      let user = this.registerUserForm.value;

      this.userService.registerUser(user).subscribe({
        next: (res) => {
          // this.preferencesService.findPreferences();
          this.router.navigate(['/home']).then(() => {
            window.location.reload();
          });;
        },
        error: (err) => {
          this.serverError = err.error?.message || 'Registration failed!';
        }
      });
    };

    // USER OBJECT BEFORE UPLOAD
    let user = this.registerUserForm.value;

    // ✔ If files selected → upload first
    if (this.selectedFiles && this.selectedFiles.length > 0) {
      console.log('uploading files');

      this.fileService.uploadFiles(this.selectedFiles).subscribe({
        next: (res) => {
          user.files = res;   // add uploaded file data to user
          this.selectedFiles = [];
          registerUserFun();  // finally register
        },
        error: (err) => {
          this.serverError = 'File upload failed!';
        }
      });

    } else {
      // No files → directly register
      registerUserFun();
    }
    
  }


  ngOnInit() {
    // hide menu & footer globally
    this.renderer.addClass(document.body, 'full-page');
    this.genders = Object.values(Gender);
    this.roles = Object.values(Role);
    this.languages = Object.values(Language);
  }

  ngOnDestroy() {
    // show them again when leaving this page
    this.renderer.removeClass(document.body, 'full-page');
  }

  onFileSelected(event: any): void {
    this.selectedFiles = Array.from(event.target.files);
    console.log(this.selectedFiles);

    if (!this.user) this.user = {};
    if (!this.user.base64_files) this.user.base64_files = new Map();

    this.selectedFiles.forEach(file => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const base64Data = e.target.result.split(',')[1];
        this.user!.base64_files!.set(file.name, base64Data);
        this.cd.markForCheck();
      };
      reader.readAsDataURL(file);
    });
  }

  triggerFileSelect(fileInput: HTMLInputElement): void {
    fileInput.click();
  }

  onRemoveFile(id: string) {
    console.log('removing user file');
    const index = this.user?.files?.indexOf(id);
    if (index! > -1) {
      this.user?.files?.splice(index!, 1);
    }

    if (this.user?.base64_files instanceof Map) {
      this.user.base64_files.delete(id);
    }
  }

  /*  onFileSelected(event: any) {
      this.selectedFiles = Array.from(event.target.files);
  
      this.selectedFiles.forEach(file => {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          const base64Data = e.target.result.split(',')[1];
          this.user.base64_files!.set(file.name, base64Data);
          this.cd.markForCheck();
        };
        reader.readAsDataURL(file);
      });
    }
  
    triggerFileSelect(fileInput: HTMLInputElement): void {
      fileInput.click();
    }
  
    // removeFile(fileName: string) {
    //   const index = this.selectedFiles.findIndex(f => f.name === fileName);
    //   if (index > -1) this.selectedFiles.splice(index, 1);
    //   this.user.base64_files?.delete(fileName);
    // }
  
    onRemoveFile(id: string) {
      console.log('removing user file');
      const index = this.user?.files?.indexOf(id);
      if (index! > -1) {
        this.user?.files?.splice(index!, 1);
      }
  
      if (this.user?.base64_files instanceof Map) {
        this.user.base64_files.delete(id);
      }
    }*/
}
