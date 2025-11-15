import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProductService } from '../product/product.service';
import { Observable } from 'rxjs';
import { Category, CountRange, PriceRange, Product, ProductListResponse } from '../product/product.model';
import { Router } from '@angular/router';
import { LazyLoadEvent } from 'primeng/api';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { FilterRequest } from '../common/common.model';

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
  categories = Object.values(Category);
  prices = Object.values(PriceRange);
  counts = Object.values(CountRange);

  @ViewChild('tableTemplateRefVar') table!: Table;


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
    this.router.navigate(['/product'], { state: { id } });  //state:{id, name, price }
  }

  deleteProduct(id: string): void {
    console.log('delete product');
    this.productService.deleteProduct(id).subscribe(() => {
      this.router.navigateByUrl("/products");
    });
  }

  onSearch(event: any) {
    const query = event.target.value.trim();
    this.productService.searchProducts(query).subscribe(res => {
      this.products = res.products || [];
      this.total = res.total;
    }
    );
  }

  filterByPrice(event: any) {
    const value = event.target.value;
    let request: FilterRequest = {};
    switch (value) {
      case 'Below 100':
        request.min_price = 0;
        request.max_price = 100;
        break;
      case '100 - 500':
        request.min_price = 100;
        request.max_price = 500;
        break;
      case '500 - 1000':
        request.min_price = 500;
        request.max_price = 1000;
        break;
      case 'Above 1000':
        request.min_price = 1000;
        request.max_price = 10000000;
        break;
    }
    this.productService.filterProducts(request).subscribe(res => {
      this.products = res.products || [];
      this.total = res.total;
    }
    );
  }

  filterByCount(event: any) {
    const value = event.target.value;
    let request: FilterRequest = {};
    switch (value) {
      case 'Below 10':
        request.min_count = 1;
        request.max_count = 10;
        break;
      case '10 - 50':
        request.min_count = 10;
        request.max_count = 50;
        break;
      case '50 - 100':
        request.min_count = 50;
        request.max_count = 100;
        break;
      case 'Above 100':
        request.min_count = 100;
        request.max_count = 1000000;
        break;
      // case 'Out of stock':
      //   request.min_count = 0;
      //   request.max_count = 0;
      //   break;
    }
    this.productService.filterProducts(request).subscribe(res => {
      this.products = res.products || [];
      this.total = res.total;
    }
    );
  }

  filterByCategory(event: any) {
    const value = event.target.value;
    if (value === 'All') {
      this.table.first = 0; // resets to first page
      this.productService.findAllProducts(0, 10).subscribe(res => {
        this.products = res.products || [];
        this.total = res.total;
      });
      return;
    }
    let request: FilterRequest = {};
    request.category = value;
    this.productService.filterProducts(request).subscribe(res => {
      this.products = res.products || [];
      this.total = res.total;
    }
    );
  }

}
