import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Order, OrderListResponse, OrderStatus } from '../order/order.model';
import { OrderService } from '../order/order.service';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { Observable } from 'rxjs';
import { Category, PriceRange, ProductListResponse } from '../product/product.model';
import { FilterRequest } from '../common/common.model';
import JSConfetti from 'js-confetti';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent {

  orders: Order[] = [];
  total: number = 0;
  showAnimation = false;
  loading: boolean = false;
  categories = Object.values(Category);
  orderStatus = Object.values(OrderStatus);
  prices = Object.values(PriceRange);

  @ViewChild('tableTemplateRefVar') table!: Table;

  constructor(private router: Router, private orderService: OrderService) {
  }

  ngOnInit() {
    this.placeOrderAnimation();
  }

  loadOrders(event: TableLazyLoadEvent): void {
    console.log('load products');
    // this.loading = true;
    const page = (event?.first ?? 0) / (event?.rows ?? 10);
    const size = event?.rows ?? 10;
    let ordersObservable: Observable<OrderListResponse> =
      this.orderService.findAllOrders(page, size);
    ordersObservable.subscribe((data) => {
      (this.orders = data.orders || []);
      this.total = data.total;
      // this.loading = false;
    });
  }

  placeOrderAnimation() {
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras.state || history.state;

    if (state?.orderPlaced) {
      const audio = new Audio('assets/audios/placeorder.mp3');
      audio.load();
      audio.play().catch(err => console.log('Audio play failed:', err));

      this.showAnimation = true;
      setTimeout(() => {
        this.showConfettiAnimation();
        this.showAnimation = false;
      }, 2500);

      let jsConfetti = new JSConfetti();
      jsConfetti.addConfetti({
        confettiNumber: 400,
        confettiRadius: 6,
        confettiColors: [
          '#FF0A54', '#FF477E', '#FF7096', '#FF85A1',
          '#FBB1BD', '#F9BEC7', '#00C2FF', '#7DF9FF',
          '#28FFBF', '#BCFFB9', '#FFD700', '#FFA500'
        ]
      });
    }
  }

  showConfettiAnimation() {

  }

  onSearch(event: any) {
    const query = event.target.value.trim();
    this.orderService.searchOrders(query).subscribe(res => {
      this.orders = res.orders || [];
      this.total = res.total;
    }
    );
  }

  filterByPrice(event: any) {
    const value = event.target.value;
    let request: FilterRequest = {};
    switch (value) {
      case 'Below 100':
        request.min_price = 0;
        request.max_price = 100;
        break;
      case '100 - 500':
        request.min_price = 100;
        request.max_price = 500;
        break;
      case '500 - 1000':
        request.min_price = 500;
        request.max_price = 1000;
        break;
      case 'Above 1000':
        request.min_price = 1000;
        request.max_price = 10000000;
        break;
    }
    this.orderService.filterOrders(request).subscribe(res => {
      this.orders = res.orders || [];
      this.total = res.total;
    }
    );
  }

  filterByStatus(event: any) {
    const value = event.target.value;
    let request: FilterRequest = {};
    request.order_status = value;
    this.orderService.filterOrders(request).subscribe(res => {
      this.orders = res.orders || [];
      this.total = res.total;
    }
    );
  }

  filterByCategory(event: any) {
    const value = event.target.value;
    if (value === 'All') {
      this.table.first = 0; // resets to first page
      this.orderService.findAllOrders(0, 10).subscribe(res => {
        this.orders = res.orders || [];
        this.total = res.total;
      });
      return;
    }
    let request: FilterRequest = {};
    request.category = value;
    this.orderService.filterOrders(request).subscribe(res => {
      this.orders = res.orders || [];
      this.total = res.total;
    }
    );
  }

}
