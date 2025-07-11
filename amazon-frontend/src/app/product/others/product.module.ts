import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ProductComponent } from "../product.component";
import { ReactiveFormsModule } from "@angular/forms";


@NgModule({
  declarations: [ProductComponent],
  imports: [
    CommonModule, 
    //from below, we added
    ReactiveFormsModule
  ],
  exports: [ProductComponent]
})
export class ProductModule { }