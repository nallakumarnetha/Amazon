import { Component } from "@angular/core";
import { Product, ProductListResponse } from "../product/others/product.model";
import { Observable } from "rxjs";
import { ProductService } from "../product/others/product.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  products?: Product[];

  constructor(private productService: ProductService) {
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
  
}
