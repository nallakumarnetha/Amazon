import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ProductModule } from './product/product.module';
import { HttpClientModule } from '@angular/common/http';

import { MyPrimeNgModule } from './my-modules/my-prime-ng.module';
import { MyModule } from './my-modules/my.module';
import { MyRouting } from './my-modules/my-routing';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { PaymentComponent } from './payment/payment.component';
import { OrderComponent } from './order/order.component';

@NgModule({
  declarations: [
    AppComponent,
    CheckoutComponent,
    PaymentComponent,
    OrderComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    //from below, we added
    ReactiveFormsModule, HttpClientModule,
    ProductModule, MyModule, MyPrimeNgModule, MyRouting
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 

}
