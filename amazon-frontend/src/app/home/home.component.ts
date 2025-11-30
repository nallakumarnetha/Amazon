import { Component } from "@angular/core";
import { Category, Product, ProductListResponse } from "../product/product.model";
import { Observable } from "rxjs";
import { ProductService } from "../product/product.service";
import { MenuComponent } from "../menu/menu.component";
import { CartService } from "../cart/cart.service";
import { Router } from "@angular/router";
import { Order } from "../order/order.model";
import { OrderService } from "../order/order.service";
import { CommonService } from "../common/common.service";
import { FilterRequest } from "../common/common.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  products: Product[] = [];
  product?: Product;
  page = 0;
  size = 10;
  loading = false;
  allLoaded = false;
  showAnimation = false;
  // order: Order = {};
  categories = Object.values(Category).slice(0, 4);

  constructor(private productService: ProductService, private cartService: CartService,
    private route: Router, private orderService: OrderService,
    private commonService: CommonService
  ) {
  }

  ngOnInit() {
    this.loadProducts();

    // Listen for cart updates
    this.cartService.cartObservable$.subscribe(cartItems => {
      this.products = this.products.map(p => {
        const match = cartItems.find(ci => ci.id === p.id);
        return match ? { ...p, cart_count: match.cart_count } : { ...p, cart_count: 0 };
      });
    });

    // Listen for search updates
    this.commonService.search$.subscribe(query => {
      if (query.length > 0) {
        this.productService.searchProducts(query).subscribe(res => {
          this.products = res.products || [];
        });
      } else {
        this.products = [];
        this.page = 0;
        this.allLoaded = false;
        this.loadProducts(); // reload all products when search cleared
      }
    });

    // Listen for category filter updates
    this.commonService.category$.subscribe(category => {
      if (!category || category === 'All') {
        // Reset to all products
        this.products = [];
        this.page = 0;
        this.allLoaded = false;
        this.loadProducts();
      } else {
        let filterRequest: FilterRequest = {};
        filterRequest.category = category;
        this.productService.filterProducts(filterRequest).subscribe(res => {
          this.products = res.products || [];
        });
      }
    });

  }

  loadProducts(): void {
    console.log('load products');
    if (this.loading || this.allLoaded) return; // prevent duplicate calls
    this.loading = true;
    let productsObservable: Observable<ProductListResponse> =
      this.productService.findAllProducts(this.page, this.size);
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

  addToCart(id: string) {
    this.cartService.addToCart(id);
  }

  byNow(id: string) {
    this.route.navigate(['/order'], { queryParams: { buyNowId: id } });
  }

  onCategoryChange(event: any) {
    const value = event.target.value;
    this.commonService.updateCategory(value);
  }

  increaseCount(id: string) {
    this.cartService.increaseCount(id);
  }

  decreaseCount(id: string) {
    this.cartService.decreaseCount(id);
  }

  deleteFromCart(id: string) {
    this.cartService.deleteFromCart(id);
  }

}
