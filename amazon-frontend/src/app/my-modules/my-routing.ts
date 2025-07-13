import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "../home/home.component";
import { AccountComponent } from "../account/account.component";
import { ProductsComponent } from "../products/products.component";

let myRoutes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'account', component: AccountComponent },
    { path: 'products', component: ProductsComponent }];

@NgModule({
    imports: [RouterModule.forRoot(myRoutes)],
    exports: []
})
export class MyRouting {

}