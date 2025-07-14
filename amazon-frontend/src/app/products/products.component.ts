import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProductService } from '../product/others/product.service';
import { Observable } from 'rxjs';
import { Product, ProductListResponse } from '../product/others/product.model';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent {
productForm: FormGroup;
product?: Product;
  products?: Product[];


constructor(private fb: FormBuilder, private productService: ProductService) {
this.productForm = this.fb.group({
  name:'',price:0});
}

  ngOnInit() {
    this.loadProducts();
  }

    loadProducts(): void {
      console.log('load products');
      let productsObservable: Observable<ProductListResponse> =
        this.productService.loadProducts();
      productsObservable.subscribe((data) => (this.products = data.products));
    }

  addProduct(): void {
    console.log('add product');
    let product = this.productForm.value;
    let productObservable: Observable<Product> = this.productService.addProduct(product);
    productObservable.subscribe((data: Product) => {
      this.product = data;
    });
  }
}
