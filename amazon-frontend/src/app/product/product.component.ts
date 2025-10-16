import { Component } from '@angular/core';
import { Product, ProductListResponse } from './product.model';
import { ProductService } from './product.service';
import { Observable } from 'rxjs';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { MenuItem } from 'primeng/api';
import { Router } from '@angular/router';
// import { stat } from 'fs';
// import { error } from 'console';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent {

  product?: Product = {};
  updatedProductForm: FormGroup;
  id: string = '';

constructor(private fb: FormBuilder, private productService: ProductService,
  private router: Router
) {
    const product = new Product();
    this.updatedProductForm = this.fb.group({
      product_id: product.product_id,
      name: product.name,
      price: product.price,
    });
  }

ngOnInit() {
  this.id = history.state.id;
  this.showProduct();
}

showProduct() {
  this.productService.findProduct(this.id).subscribe((res) => {
    this.product = res;
    this.updatedProductForm?.patchValue(this.product);
  });
}

  updateProduct(): void {
    console.log('update product');
    let product = this.updatedProductForm.value;
    let productObservable: Observable<Product> =
      this.productService.updateProduct(this.id, product);
    productObservable.subscribe((data) => {
      this.product = {};
      this.updatedProductForm.reset();
      this.router.navigateByUrl("/products");
    });
  }

  deleteProduct(id: string): void {
    console.log('delete product');
    this.productService.deleteProduct(id).subscribe(()=>{
      this.router.navigateByUrl("/products");
    });
  }

}
