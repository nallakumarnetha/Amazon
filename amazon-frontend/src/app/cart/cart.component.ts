import { Component } from '@angular/core';
import { CartService } from './cart.service';
import { Product } from '../product/product.model';

export enum Status {
  Active = 'Active',
  Inactive = 'Inactive'
}

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {

  products?: Product[];
  Status = Status;

  constructor(private cartService: CartService) {
    // localStorage.clear();
  }

  ngOnInit() {
    this.loadCartProducts();
  }

  loadCartProducts() {
    this.cartService.findProducts();
    this.cartService.cartObservable$.subscribe(res => {
      this.products = res;
    });
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

  changeStatus(id: string, isChecked: boolean) {
    const status = isChecked ? Status.Active : Status.Inactive;
    this.cartService.changeStatus(id, status);
  }

}
