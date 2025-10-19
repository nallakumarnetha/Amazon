import { Component } from '@angular/core';
import { CartProduct, CartService } from './cart.service';
import { Product } from '../product/product.model';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {

  cartProducts?: CartProduct[];
  products?: Product[];

  constructor(private cartService: CartService) {

  }

  ngOnInit() {
    this.loadCartProducts();
  }

  loadCartProducts() {
    this.cartProducts = this.cartService.cartProducts;
    this.products = this.cartService.findCartProducts(this.cartProducts!);
  }

}
