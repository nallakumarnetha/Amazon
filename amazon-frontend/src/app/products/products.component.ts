import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProductService } from '../product/product.service';
import { Observable } from 'rxjs';
import { Product, ProductListResponse } from '../product/product.model';
import { Router } from '@angular/router';
import { LazyLoadEvent } from 'primeng/api';
import { TableLazyLoadEvent } from 'primeng/table';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent {
  productForm: FormGroup;
  product?: Product;
  products: Product[] = [];
  total: number = 0;
  loading: boolean = false;

  constructor(private fb: FormBuilder, 
    private productService: ProductService,
    private router: Router
  ) {
    this.productForm = this.fb.group({
      name: '', price: 0
    });
  }

  ngOnInit() {

  }

  loadProducts(event: TableLazyLoadEvent): void {
    console.log('load products');
    // this.loading = true;
    const page = (event?.first ?? 0) / (event?.rows ?? 10);
    const size = event?.rows ?? 10;
    let productsObservable: Observable<ProductListResponse> =
      this.productService.findAllProducts(page, size);
    productsObservable.subscribe((data) => {
      (this.products = data.products || []);
      this.total = data.total;
      // this.loading = false;
  });
  }

  showProduct(id: string): void {
    this.router.navigate(['/product'], {state:{id}});  //state:{id, name, price }
  }

  deleteProduct(id: string): void {
    console.log('delete product');
    this.productService.deleteProduct(id).subscribe(()=>{
      this.router.navigateByUrl("/products");
    });
  }

}
