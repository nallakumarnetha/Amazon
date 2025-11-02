import { Component } from '@angular/core';
import { Category, Product, ProductListResponse } from './product.model';
import { ProductService } from './product.service';
import { Observable } from 'rxjs';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { MenuItem } from 'primeng/api';
import { Router } from '@angular/router';
import { FileService } from '../file/file.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent {

  product?: Product = {};
  updatedProductForm: FormGroup;
  id: string = '';
  selectedFiles: File[] = [];
  categories = Object.values(Category);

  constructor(private fb: FormBuilder, private productService: ProductService,
    private router: Router, private fileService: FileService
  ) {
    const product = new Product();
    this.updatedProductForm = this.fb.group({
      product_id: product.product_id,
      name: product.name,
      price: product.price,
      count: product.count,
      category: [this.product?.category || ''] // preselect existing category
    });
  }

  ngOnInit() {
    this.id = history.state.id;
    this.showProduct();
  }

  showProduct() {
    this.productService.findProduct(this.id).subscribe((res) => {
      if (res.base64_files && !(res.base64_files instanceof Map)) {
        res.base64_files = new Map(Object.entries(res.base64_files));
      }
      this.product = res;
      this.updatedProductForm?.patchValue(this.product);
    });
  }

  updateProduct(): void {
    const updateProductFun = () => {
      console.log('update product');

      let product = this.updatedProductForm.value;
      product.files = this.product?.files;

      let productObservable: Observable<Product> =
        this.productService.updateProduct(this.id, product);

      productObservable.subscribe((data) => {
        this.product = {};
        this.updatedProductForm.reset();
        this.router.navigateByUrl("/products");
      });
    };

    if (this.selectedFiles && this.selectedFiles.length > 0) {
      console.log('uploading files');
      this.fileService.uploadFiles(this.selectedFiles).subscribe({
        next: (res) => {
          if (this.product) {
            this.product.files = [...(this.product.files || []), ...res];
          }
          updateProductFun();
          this.selectedFiles = [];
        },
        error: (err) => console.error(err)
      });
    } else {
      updateProductFun();
    }
  }

  deleteProduct(id: string): void {
    console.log('delete product');
    this.productService.deleteProduct(id).subscribe(() => {
      this.router.navigateByUrl("/products");
    });
  }

  onFileSelected(event: any): void {
    this.selectedFiles = Array.from(event.target.files);
    console.log(this.selectedFiles);

    // to show selected files
    if (!this.product) this.product = {};
    if (!this.product.base64_files) this.product.base64_files = new Map();

    this.selectedFiles.forEach(file => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const base64Data = e.target.result.split(',')[1]; // remove "data:image/...;base64,"
        this.product!.base64_files!.set(file.name, base64Data);
      };
      reader.readAsDataURL(file);
    });
  }

  triggerFileSelect(fileInput: HTMLInputElement): void {
    fileInput.click();
  }

  onRemoveFile(id: string) {
    console.log("file remove");
    const index = this.product?.files?.indexOf(id);
    if (index! > -1) {
      this.product?.files?.splice(index!, 1);
    }
    //OR this.fileIds = this.fileIds.filter(id => id !== id);

    if (this.product?.base64_files instanceof Map) {
      this.product.base64_files.delete(id);
    }
  }

}

