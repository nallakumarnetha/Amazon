import { Component } from '@angular/core';
import { UserService } from './user.service';
import { User } from './user.model';
import { Observable } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { FileService } from '../file/file.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent {

  id?: string;
  user: User = {};
  updatedUserForm: FormGroup;
  isUpdate = false;
  selectedFiles: File[] = [];

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private fileService: FileService
  ) {
    const user = new User();
    this.updatedUserForm = this.fb.group({
      user_id: user.user_id,
      name: user.name,
      phone_number: user.phone_number,
      gender: user.gender,
      role: user.role
    });
  }

  ngOnInit() {
    this.loadUser();
  }

  loadUser() {
    this.id = 'u1'; // TODO: take from session
    this.userService.findUser(this.id).subscribe(res => {
      if (res.base64_files && !(res.base64_files instanceof Map)) {
        res.base64_files = new Map(Object.entries(res.base64_files));
      }
      this.user = res;
      this.updatedUserForm?.patchValue(this.user);
    });
  }

  updateUser(): void {
    const updateUserFun = () => {
      console.log('update user');
      let user = this.updatedUserForm.value;
      user.files = this.user?.files;

      let userObservable: Observable<User> =
        this.userService.updateUser(this.id!, user);

      userObservable.subscribe(() => {
        this.isUpdate = false;
        this.loadUser();
        this.selectedFiles = [];
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
}
