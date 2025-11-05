import { Component } from '@angular/core';
import { CartService } from '../cart/cart.service';
import { Product } from '../product/product.model';
import { Status } from '../cart/cart.component';
import { OrderService } from './order.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Order } from './order.model';
import { ProductService } from '../product/product.service';
import { User } from '../user/user.model';
import { UserService } from '../user/user.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent {

  products: Product[] = [];
  order: Order = {};
  currentUser?: User;

  constructor(private cartService: CartService, private orderService: OrderService,
    private route: Router, private activatedRoute: ActivatedRoute, private productService: ProductService
    , private userService: UserService
  ) {
  }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.queryParamMap.get('buyNowId');
    if (id) {
      this.productService.findProduct(id).subscribe(res => this.products = [res]);
    } else {
      this.cartService.findProducts(Status.Active);
      this.cartService.cartObservable$.subscribe(
        res => this.products = res
      );
    }
    this.updateDeliverTo();
  }

  placeOrder() {
    console.log('place order')
    this.products.forEach(product => {
      this.order.count = product.cart_count;
      this.order.product_id = product.product_id;
      this.order.amount = product.price! * product.cart_count!;
      this.order.payment_id = 'p1';
      this.order.category = product.category;
      this.orderService.addOrder(this.order!).subscribe();

      // delete ordered products from cart
      this.cartService.deleteFromCart(product.id);

      // decrease product count
      product.count = product.count! - product?.cart_count!;
      this.productService.updateProduct(product.id, product).subscribe();
    });
    this.route.navigateByUrl('/orders', { state: { orderPlaced: true } });
  }

  updateDeliverTo() {
    this.userService.getCurrentUser().subscribe(res => {
      this.currentUser = res;
    });
  }

  getTotal(): number {
    return this.products
      ?.reduce((sum, p) => sum + (p.cart_count! * p.price!), 0) || 0;
  }


}
