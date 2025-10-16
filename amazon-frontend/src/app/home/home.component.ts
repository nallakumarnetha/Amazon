import { Component } from "@angular/core";
import { Product, ProductListResponse } from "../product/product.model";
import { Observable } from "rxjs";
import { ProductService } from "../product/product.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  products: Product[] = [];
  page = 0;
  size = 10;
  loading = false;
  allLoaded = false;

  constructor(private productService: ProductService) {
  }
  ngOnInit() {
    this.loadProducts();
  }

  loadProducts(): void {
    console.log('load products');
    if (this.loading || this.allLoaded) return; // prevent duplicate calls
    this.loading = true;
    let productsObservable: Observable<ProductListResponse> =
      this.productService.loadProducts(this.page, this.size);
    productsObservable.subscribe((data) => {
      const products = data?.products ?? [];
      if (products.length === 0) {
        this.allLoaded = true;
      }
      this.products = [...this.products, ...products!];
      this.page++;
      this.loading = false;
    });
  }

  onScroll(event: any) {
    const element = event.target;
    if (element.scrollHeight - element.scrollTop === element.clientHeight) {
      this.loadProducts();
    }
  }

}
