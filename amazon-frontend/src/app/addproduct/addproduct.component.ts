import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Category, Product } from '../product/product.model';
import { ProductService } from '../product/product.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { FileService } from '../file/file.service';

@Component({
  selector: 'app-addproduct',
  templateUrl: './addproduct.component.html',
  styleUrl: './addproduct.component.css'
})
export class AddproductComponent {
  productForm: FormGroup;
  product?: Product = {};
  selectedFiles: File[] = [];
  fileIds: string[] = [];
  selectedFilesBase64: { id: string, data: string }[] = [];
  categories = Object.values(Category).filter(cat => cat !== Category.All);
  submitted = false;
  serverError = '';

  constructor(private fb: FormBuilder, private productService: ProductService, private router: Router,
    private fileService: FileService) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(1)]],
      count: 0,
      category: ['', Validators.required]
    });
  }
  
  addProduct(): void {
    this.submitted = true;
    this.serverError = '';
    if (this.productForm.invalid) {
      return;
    }

    const addProductFun = () => {
      console.log('add product');
      let productObservable: Observable<Product> = this.productService.addProduct(this.product!);
      productObservable.subscribe((data: Product) => {
        this.product = data;
      });
      this.router.navigate(['/products']);
    };

    this.product = this.productForm.value;
    if (this.selectedFiles && this.selectedFiles.length > 0) {
      console.log('uploading files');
      this.fileService.uploadFiles(this.selectedFiles).subscribe({
        next: (res) => {
          this.product!.files = res;
          addProductFun();
          this.selectedFiles = [];
        },
        error: (err) => err
      });
    } else {
      addProductFun();
    }
  }

  onFileSelected(event: any): void {
    this.selectedFiles = Array.from(event.target.files);
    console.log(this.selectedFiles);

    // to show selected files
    this.selectedFiles.forEach(file => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        // Convert to base64 string (remove the "data:image/...;base64," prefix)
        const base64Data = e.target.result.split(',')[1];
        this.selectedFilesBase64.push({ id: file.name, data: base64Data });
      };
      reader.readAsDataURL(file);
    });
  }

  onRemoveFile(id: string) {
    console.log("file remove");

    // Remove from selectedFiles array (for upload)
    const index = this.selectedFiles.findIndex(f => f.name === id);
    if (index > -1) this.selectedFiles.splice(index, 1);

    // Remove from base64 preview array
    const previewIndex = this.selectedFilesBase64.findIndex(f => f.id === id);
    if (previewIndex > -1) this.selectedFilesBase64.splice(previewIndex, 1);
  }


}
