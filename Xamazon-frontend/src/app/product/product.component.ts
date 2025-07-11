import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProductService } from './others/product.service';
import { Observable } from 'rxjs';
import { Product } from './others/product.model';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent {
  private product: Product = {};
  // private products: Product[] = []; 

  private productForm: FormGroup;

  constructor(private fb: FormBuilder, private productService: ProductService) {
    this.productForm = this.fb.group({
      name: '',
      age: 0
    });
  }

  addProduct(): void {
    let product = this.productForm.value;
    let productObservable: Observable<Product> = this.productService.addProduct(product);
    productObservable.subscribe(
      (product: Product) => {this.product = product}
    );
  }

}
