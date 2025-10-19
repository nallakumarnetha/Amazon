import { ChangeDetectorRef, Component } from "@angular/core";
import { CartService } from "../cart/cart.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

  showSideBar: boolean = false;
  cartCount?: number = 0;

  constructor(private cartService: CartService, private changeDetecorRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.updateCartCount();
  }

  toggleSideBar() {
    this.showSideBar = !this.showSideBar;
  }

  updateCartCount() {
    // const cartStr = localStorage.getItem('cart') || '';
    // const cartItems =  JSON.parse(cartStr);
    // this.cartCount = cartItems.length;
    this.cartService.cartObservable$.subscribe(
      cart => { this.cartCount = cart.length;
        this.changeDetecorRef.detectChanges();
       }
    );
  }

}
