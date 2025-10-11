import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Product } from '../product/product.model';
import { ProductService } from '../product/product.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { FileComponent } from '../file/file.component';
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
  fileIds: String[] = [];

  constructor(private fb: FormBuilder, private productService: ProductService, private router: Router
    , private fileService: FileService) {
    this.productForm = this.fb.group({
      name: '', price: 0
    });
  }
  addProduct(): void {
    const addProductFun = () => {
      console.log('add product');
      let productObservable: Observable<Product> = this.productService.addProduct(this.product!);
      productObservable.subscribe((data: Product) => {
        this.product = data;
      });
      this.router.navigate(['/products']);
    };

    this.product = this.productForm.value;
    if (this.selectedFiles && this.selectedFiles.length>0) {
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
    console.log(event.target.files);
    this.selectedFiles = Array.from(event.target.files);
    console.log(this.selectedFiles);
  }

}
