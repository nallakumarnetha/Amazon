import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "../home/home.component";
import { AccountComponent } from "../account/account.component";
import { ProductsComponent } from "../products/products.component";
import { AddproductComponent } from "../addproduct/addproduct.component";
import { ProductComponent } from "../product/product.component";

let myRoutes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'account', component: AccountComponent },
    { path: 'addProduct', component: AddproductComponent },
    { path: 'products', component: ProductsComponent },
    { path: 'product', component: ProductComponent }];

@NgModule({
    imports: [RouterModule.forRoot(myRoutes)],
    exports: []
})
export class MyRouting {

}