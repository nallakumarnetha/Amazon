import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ProductComponent } from "../product.component";
import { ReactiveFormsModule } from "@angular/forms";
import { MenubarModule } from 'primeng/menubar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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