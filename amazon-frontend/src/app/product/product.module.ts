import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { MenubarModule } from 'primeng/menubar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProductComponent } from "./product.component";

@NgModule({
  declarations: [ProductComponent],
  imports: [
    CommonModule, 
    //from below, we added
    ReactiveFormsModule, MenubarModule, BrowserAnimationsModule
  ],
  exports: [ProductComponent]
})
export class ProductModule { }