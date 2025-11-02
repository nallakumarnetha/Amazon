import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../product/product.model';
import { ProductService } from '../product/product.service';
import { CartService } from '../cart/cart.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent {

  product: Product = {};
  showAnimation = false;

  constructor(private activatedRoute: ActivatedRoute, private productService: ProductService,
    private cartService: CartService, private route: Router
  ) {

  }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    this.productService.findProduct(id!).subscribe(
      res => this.product = res
    );
  }

  addToCart(id?: string) {
    this.cartService.addToCart(id);
    this.addToCartAnimation();
  }

  addToCartAnimation() {
    const audio = new Audio('assets/audios/placeorder.mp3');
    audio.load();
    audio.play().catch(err => console.log('Audio play failed:', err));

    this.showAnimation = true;
    setTimeout(() => {
      this.showAnimation = false;
    }, 5000);
  }

  byNow(id: string) {
    this.route.navigate(['/order'], { queryParams: { buyNowId: id } });
  }

}
