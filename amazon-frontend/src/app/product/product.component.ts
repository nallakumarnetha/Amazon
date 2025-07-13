import { Component } from '@angular/core';
import { Product, ProductListResponse } from './others/product.model';
import { ProductService } from './others/product.service';
import { Observable } from 'rxjs';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent {
  product: Product = {};
  products?: Product[];
  productForm: FormGroup;
  selectedProductId?: string;
  selectedProductForm: FormGroup;

 constructor(private fb: FormBuilder, private productService: ProductService) {
    this.productForm = this.fb.group({
      name: '',
      price: 0,
    });
    this.selectedProductForm = this.fb.group({
      name: '',
      price: 0,
    });
  }

  ngOnInit() {
    this.loadProducts();
  }

  //CRUD start
  loadProducts(): void {
    console.log('load products');
    let productsObservable: Observable<ProductListResponse> =
      this.productService.loadProducts();
    productsObservable.subscribe((data) => (this.products = data.products));
  }

  findProduct(id: string): void {
    console.log('find product');
    let productObservable: Observable<Product> =
      this.productService.findProduct(id);
    productObservable.subscribe((data) => (this.product = data));
  }

  addProduct(): void {
    console.log('add product');
    let product = this.productForm.value;
    let productObservable: Observable<Product> =
      this.productService.addProduct(product);
    productObservable.subscribe((data: Product) => {
      this.product = data;
    });
  }

  editProduct(product: Product): void {
    this.selectedProductId = product.id;
    this.selectedProductForm?.patchValue({
      name: product.name,
      price: product.price,
    });
  }

  updateProduct(id: string | undefined): void {
    console.log('update product');
    let product = this.selectedProductForm.value;
    let productObservable: Observable<Product> =
      this.productService.updateProduct(id, product);
    productObservable.subscribe((data) => {
      this.product = data;
      this.selectedProductId = '';
      this.selectedProductForm.reset();
      this.loadProducts();
    });
  }

  deleteProduct(id: string): void {
    console.log('delete product');
    this.productService.deleteProduct(id);
  }

  //CRUD end

}
