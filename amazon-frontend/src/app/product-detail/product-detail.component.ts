import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../product/product.model';
import { ProductService } from '../product/product.service';
import { CartService } from '../cart/cart.service';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent {

  product: Product = {};
  productValue: Product = {};

  constructor(private activatedRoute: ActivatedRoute, private productService: ProductService,
    private cartService: CartService, private route: Router
  ) {

  }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    // Load initial product details
    this.productService.findProduct(id!).subscribe(
      res => this.product = res
    );
    // Listen to cart updates
    this.cartService.cartObservable$.subscribe(res => {
      const updatedProduct = res.find(p => p.id === id);
      if (updatedProduct) {
        this.product.cart_count = updatedProduct.cart_count;
      } else {
        this.product.cart_count = 0; // <- item removed
      }
    });
  }

  addToCart(id?: string) {
    this.cartService.addToCart(id);
  }

  byNow(id: string) {
    this.route.navigate(['/order'], { queryParams: { buyNowId: id } });
  }

  decreaseCount(id: string) {
    this.cartService.decreaseCount(id);
    // this.productService.
  }

  increaseCount(id: string) {
    this.cartService.increaseCount(id);
  }

  deleteFromCart(id: string) {
    this.cartService.deleteFromCart(id);
  }

}
