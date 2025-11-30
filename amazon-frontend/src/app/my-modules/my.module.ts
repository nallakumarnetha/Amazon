import { MenuComponent } from '../menu/menu.component';
import { HomeComponent } from '../home/home.component';
import { ProductsComponent } from '../products/products.component';
import { FooterComponent } from '../footer/footer.component';
import { MyPrimeNgModule } from './my-prime-ng.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AddproductComponent } from '../addproduct/addproduct.component';
import { FileComponent } from '../file/file.component';
import { CartComponent } from '../cart/cart.component';
import { OrderComponent } from '../order/order.component';
import { OrdersComponent } from '../orders/orders.component';
import { ProductDetailComponent } from '../product-detail/product-detail.component';
import { UserComponent } from '../user/user.component';
import { PreferencesComponent } from '../preferences/preferences.component';
import { DemoModeComponent } from '../demo-mode/demo-mode.component';
import { ToastModule } from 'primeng/toast';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { SignupComponent } from '../signup/signup.component';
import { LoginComponent } from '../login/login.component';
import { PasswordModule } from 'primeng/password';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { MyInterceptor } from '../common/myinterceptor.interceptor';


let myComponents = [MenuComponent, HomeComponent, ProductsComponent, FooterComponent, AddproductComponent
  , FileComponent, CartComponent, OrderComponent, OrdersComponent, ProductDetailComponent
  , UserComponent, PreferencesComponent, DemoModeComponent, SignupComponent, LoginComponent
];

@NgModule({
  declarations: [...myComponents],
  imports: [
    CommonModule, MyPrimeNgModule, ReactiveFormsModule, BrowserAnimationsModule, ToastModule, ConfirmDialogModule, PasswordModule
  ],
  providers: [MessageService, ConfirmationService, {
    provide: HTTP_INTERCEPTORS,
    useClass: MyInterceptor,
    multi: true
  }],
  exports: [...myComponents]
})
export class MyModule { }
