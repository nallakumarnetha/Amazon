import { Component } from "@angular/core";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

showSideBar: boolean=false;
ngOnInit() {
}

toggleSideBar() {
this.showSideBar=!this.showSideBar;
}

}
