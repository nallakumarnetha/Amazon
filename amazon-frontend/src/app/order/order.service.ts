import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Order, OrderListResponse } from "./order.model";
import { FilterRequest } from "../common/common.model";

@Injectable({
    providedIn: 'root'
})
export class OrderService {

    // private baseUrl = 'http://localhost:8080/amazon/orders'; // monolithic
    private baseUrl = 'http://localhost:8088/order/orders'; // microservices

    constructor(private http: HttpClient) { }

    findAllOrders(page: number, size: number): Observable<any> {
        return this.http.get(`${this.baseUrl}?page=${page}&size=${size}`);
    }

    findOrderById(id: string): Observable<Order> {
        return this.http.get<Order>(`${this.baseUrl}/${id}`);
    }

    addOrder(order: Order): Observable<Order> {
        return this.http.post<Order>(this.baseUrl, order);
    }

    updateOrder(id: string | undefined, order: Order): Observable<Order> {
        return this.http.put<Order>(`${this.baseUrl}/${id}`, order);
    }

    deleteOrder(id: string): Observable<any> {
        return this.http.delete(`${this.baseUrl}/${id}`);
    }

    searchOrders(query: string): Observable<OrderListResponse> {
        return this.http.get<OrderListResponse>(`${this.baseUrl}/search?name=${query}`);
    }

    filterOrders(request: FilterRequest): Observable<OrderListResponse> {
        return this.http.post<OrderListResponse>(`${this.baseUrl}/filter`, request);
    }

}
