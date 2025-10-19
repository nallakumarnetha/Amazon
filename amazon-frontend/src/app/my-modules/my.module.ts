import { MenuComponent } from '../menu/menu.component';
import { HomeComponent } from '../home/home.component';
import { AccountComponent } from '../account/account.component';
import { ProductsComponent } from '../products/products.component';
import { FooterComponent } from '../footer/footer.component';
import { MyPrimeNgModule } from './my-prime-ng.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AddproductComponent } from '../addproduct/addproduct.component';
import { FileComponent } from '../file/file.component';
import { CartComponent } from '../cart/cart.component';

let myComponents = [MenuComponent, HomeComponent, AccountComponent, ProductsComponent, FooterComponent, AddproductComponent
  , FileComponent, CartComponent
];

@NgModule({
  declarations: [ ...myComponents],
  imports: [
    CommonModule, MyPrimeNgModule, ReactiveFormsModule
  ], 
  exports:[...myComponents]
})
export class MyModule { }
