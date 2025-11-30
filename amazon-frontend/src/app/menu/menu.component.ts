import { ChangeDetectorRef, Component } from "@angular/core";
import { CartService } from "../cart/cart.service";
import { ProductService } from "../product/product.service";
import { CommonService } from "../common/common.service";
import { Category } from "../product/product.model";
import { UserService } from "../user/user.service";
import { User } from "../user/user.model";
import { Preferences } from "../preferences/preferences.model";
import { PreferencesService } from "../preferences/preferences.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

  showSideBar: boolean = false;
  cartCount?: number = 0;
  categories = Object.values(Category);
  currentUser?: User;
  isSearchFocused?: boolean;
  preferences?: Preferences;
  lastScrollTop = 0;

  constructor(private cartService: CartService, private changeDetecorRef: ChangeDetectorRef,
    private commonService: CommonService, private userService: UserService,
    private preferencesService: PreferencesService
  ) {
  }

  ngOnInit() {
    this.updateCartCount();
    this.updateDeliverTo();
    this.loadPreferences();
    this.onScroll();
  }

  toggleSideBar() {
    this.showSideBar = !this.showSideBar;
  }

  updateCartCount() {
    this.cartService.cartObservable$.subscribe(
      cart => {
        this.cartCount = cart.length;
        // this.changeDetecorRef.detectChanges();
        // this.changeDetecorRef.markForCheck();
      }
    );
  }

  onSearch(event: any) {
    const query = event.target.value.trim();
    this.commonService.updateSearch(query);
  }

  filterByCategory(event: any) {
    const value = event.target.value;
    this.commonService.updateCategory(value);
  }

  updateDeliverTo() {
    this.userService.getCurrentUser().subscribe(res => {
      this.currentUser = res;
    });
  }

  loadPreferences() {
    this.preferencesService.preferencesSubject.subscribe(
      res => {
        this.preferences = res;
        // this.changeDetecorRef.detectChanges();
        // this.changeDetecorRef.markForCheck();
      }
    );
  }

  onScroll() {
    window.addEventListener("scroll", () => {
      const currentScroll = window.pageYOffset;

      if (currentScroll < this.lastScrollTop) {
        // Scrolling UP
        document.querySelector(".menu-one-container")
          ?.classList.add("sticky-up");
      } else {
        // Scrolling DOWN
        document.querySelector(".menu-one-container")
          ?.classList.remove("sticky-up");
      }

      this.lastScrollTop = currentScroll;
    });
  }


}
