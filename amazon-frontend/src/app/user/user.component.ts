import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { UserService } from './user.service';
import { Address, Gender, Language, Role, User } from './user.model';
import { first, Observable } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FileService } from '../file/file.service';
import { CartService } from '../cart/cart.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserComponent {

  id?: string;
  user: User = {};
  address: Address = {};
  updatedUserForm: FormGroup;
  isUpdate = false;
  selectedFiles: File[] = [];
  genders?: Gender[];
  roles?: Role[];
  languages?: Language[];
  submitted = false;
  serverError = '';

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private fileService: FileService,
    private cd: ChangeDetectorRef,
    private cartService: CartService
  ) {
    const user = new User();
    this.updatedUserForm = this.fb.group({
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
      password: ['**********', [Validators.required]],
      email: [user.email, [Validators.required, Validators.email]],
      dob: ['']
    });
  }

  ngOnInit() {
    this.loadUser();
    this.genders = Object.values(Gender);
    this.roles = Object.values(Role);
    this.languages = Object.values(Language);
  }

  loadUser() {
    this.userService.getCurrentUser().subscribe(res => {
      if (res.base64_files && !(res.base64_files instanceof Map)) {
        res.base64_files = new Map(Object.entries(res.base64_files));
      }
      this.user = res;
      this.id = res.id;
      // this.userService.findAddress(res.address || '').subscribe(res => {
      //   this.address.street = res.street;
      //   this.address.city = res.city;
      //   this.address.pincode = res.pincode;
      // });
      if (res.dob) {
        const d = new Date(res.dob);
        res.dob = d.toISOString().substring(0, 10);  // string yyyy-MM-dd
      }
      this.updatedUserForm?.patchValue(this.user);
      this.cd.detectChanges();
    });
  }

  updateUser(): void {
    this.submitted = true;
    this.serverError = '';
    if (this.updatedUserForm.invalid) {
      return;
    }

    const updateUserFun = () => {
      console.log('update user');
      let user = this.updatedUserForm.value;
      user.files = this.user?.files;

      let userObservable: Observable<User> =
        this.userService.updateUser(this.id!, user);

      userObservable.subscribe(res => {
        this.isUpdate = false;
        this.loadUser();
        this.selectedFiles = [];
      },
        err => {
          this.serverError = err.error?.message || 'User updation failed!';
          this.cd.detectChanges();
        });
    };

    if (this.selectedFiles && this.selectedFiles.length > 0) {
      console.log('uploading user files');
      this.fileService.uploadFiles(this.selectedFiles).subscribe({
        next: (res) => {
          if (this.user) {
            this.user.files = [...(this.user.files || []), ...res];
          }
          updateUserFun();
        },
        error: (err) => console.error(err)
      });
    } else {
      updateUserFun();
    }
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

  logoutUser() {
    this.userService.logout().subscribe({
      next: (res: any) => {
        if (res.status === 200) {
          this.router.navigate(['/login']);
        }
      },
      error: (err: any) => {
        console.error('Logout failed', err);
      }
    });
  }

}
