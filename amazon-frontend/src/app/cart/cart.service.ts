import { Injectable } from "@angular/core";
import { BehaviorSubject, forkJoin, map, Observable, of } from "rxjs";
import { Product, ProductListResponse } from "../product/product.model";
import { HttpClient } from "@angular/common/http";
import { ProductService } from "../product/product.service";
import { Status } from "./cart.component";
import { Cart } from "./cart.model";

@Injectable({
    providedIn: 'root'
})
export class CartService {
    // BehaviorSubject
    products: Product[] = [];
    cartSubject = new BehaviorSubject<Product[]>(this.products!);
    cartObservable$ = this.cartSubject.asObservable();

    isLoggedIn: boolean = true;
    private baseUrl = 'http://localhost:8080/amazon/cart'
    cartProduct?: Product;

    constructor(private http: HttpClient, private productService: ProductService) {
        if (!this.isLoggedIn) {
            const cartStr = localStorage.getItem('cart');
            if (cartStr && cartStr !== 'undefined' && cartStr !== 'null') {
                this.products = JSON.parse(cartStr);
                this.updateCart();
            }
        } else {
            this.findProducts();
        }
    }

    findProducts(status?: Status): void {
        if (!this.isLoggedIn) {
            const requests = this.products.map(p =>
                this.productService.findProduct(p.id!).pipe(
                    map(res => ({ ...res, cart_count: p.cart_count }))
                )
            );
            forkJoin(requests).subscribe(products => this.products = products);
        } else {
            const url = status
                ? `${this.baseUrl}?status=${status}`
                : this.baseUrl;

            this.http.get<ProductListResponse>(url).subscribe(res => {
                this.products = res.products || [];
                this.updateCart();
            });
        }
    }

    addToCart(id?: string) {
        console.log("add to cart");
        if (!this.isLoggedIn) {
            const product = this.products?.find(p => p.id === id);
            if (product && product.cart_count) {
                product.cart_count += 1;
            } else {
                this.products?.push({ id, cart_count: 1 });
            }
            this.updateCartLocalStorage();
        } else {
            this.http.post<Product>(`${this.baseUrl}/${id}`, null).subscribe(
                res => this.findProducts()
            );
        }
    }

    deleteFromCart(id?: string) {
        if (!this.isLoggedIn) {
            this.products = this.products.filter(p => p.id !== id);
            this.updateCartLocalStorage();
        } else {
            this.http.delete(`${this.baseUrl}/${id}`).subscribe(() => this.findProducts());
        }
    }

    increaseCount(id: string) {
        if (!this.isLoggedIn) {
            this.products.forEach(p => {
                if (p.id === id) {
                    if (p.cart_count != null) {
                        p.cart_count += 1;
                    }
                    return;
                }
            });
            this.updateCartLocalStorage();
        } else {
            this.http.put(`${this.baseUrl}/${id}/increase`, null).subscribe(() => this.findProducts());
        }
    }

    decreaseCount(id: string) {
        if (!this.isLoggedIn) {
            this.products.forEach(p => {
                if (p.id === id) {
                    if (p.cart_count != null && p.cart_count > 0) {
                        p.cart_count -= 1;
                    }
                    return;
                }
            });
            this.updateCartLocalStorage();
        } else {
            this.http.put(`${this.baseUrl}/${id}/decrease`, null).subscribe(() => this.findProducts());
        }
    }

    changeStatus(id: string, status: Status) {
        console.log('change cart product status | selected or deselected')
        this.http.put(`${this.baseUrl}/${id}` + '?status=' + status, null).subscribe(() => this.findProducts());
    }

    updateCart() {
        this.cartSubject.next([...this.products!]);
    }

    updateCartLocalStorage() {
        localStorage.setItem('cart', JSON.stringify(this.products));
        this.cartSubject.next([...this.products]);
    }

    findCart(productId: string): Observable<Cart> {
        return this.http.get<Cart>(`${this.baseUrl}/${productId}`);
    }

}

