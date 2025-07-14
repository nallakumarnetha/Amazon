import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ButtonModule } from "primeng/button";
import { MenubarModule } from "primeng/menubar";
import { SidebarModule } from "primeng/sidebar";
import { CardModule } from 'primeng/card';
import {TableModule} from 'primeng/table';


const myPrimeNgModules = [
  MenubarModule, SidebarModule, ButtonModule, CardModule, TableModule
];

@NgModule({
  imports: [
    CommonModule, ...myPrimeNgModules
  ], 
  exports:[...myPrimeNgModules]
})
export class MyPrimeNgModule { }
