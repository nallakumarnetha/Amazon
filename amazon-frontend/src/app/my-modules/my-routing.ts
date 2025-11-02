import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "../home/home.component";
import { ProductsComponent } from "../products/products.component";
import { AddproductComponent } from "../addproduct/addproduct.component";
import { ProductComponent } from "../product/product.component";
import { CartComponent } from "../cart/cart.component";
import { CheckoutComponent } from "../checkout/checkout.component";
import { PaymentComponent } from "../payment/payment.component";
import { OrderComponent } from "../order/order.component";
import { OrdersComponent } from "../orders/orders.component";
import { ProductDetailComponent } from "../product-detail/product-detail.component";
import { UserComponent } from "../user/user.component";
import { PreferencesComponent } from "../preferences/preferences.component";
import { DemoModeComponent } from "../demo-mode/demo-mode.component";

let myRoutes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'addProduct', component: AddproductComponent },
    { path: 'products', component: ProductsComponent },
    { path: 'product', component: ProductComponent },
    { path: 'cart', component: CartComponent },
    { path: 'checkout', component: CheckoutComponent },
    { path: 'payment', component: PaymentComponent },
    { path: 'order', component: OrderComponent },
    { path: 'orders', component: OrdersComponent },
    { path: 'product-detail/:id', component: ProductDetailComponent },
    { path: 'user', component: UserComponent },
    { path: 'preferences', component: PreferencesComponent },
    { path: 'demo-mode', component: DemoModeComponent },
    { path: '**', redirectTo: '/home' }
];

@NgModule({
    imports: [RouterModule.forRoot(myRoutes)],
    exports: []
})
export class MyRouting {

}