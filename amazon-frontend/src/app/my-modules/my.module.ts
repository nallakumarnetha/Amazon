import { MenuComponent } from '../menu/menu.component';
import { HomeComponent } from '../home/home.component';
import { AccountComponent } from '../account/account.component';
import { ProductsComponent } from '../products/products.component';
import { FooterComponent } from '../footer/footer.component';
import { MyPrimeNgModule } from './my-prime-ng.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

let myComponents = [MenuComponent, HomeComponent, AccountComponent, ProductsComponent, FooterComponent];

@NgModule({
  declarations: [ ...myComponents],
  imports: [
    CommonModule, MyPrimeNgModule
  ], 
  exports:[...myComponents]
})
export class MyModule { }
