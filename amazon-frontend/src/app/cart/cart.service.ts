import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { Product } from "../product/product.model";
import { HttpClient } from "@angular/common/http";
import { ProductService } from "../product/product.service";

export class CartProduct {
    id?: string;
    count?: number = 0;
}

@Injectable({
    providedIn: 'root'
})
export class CartService {

    cartProducts?: CartProduct[];
    cartSubject = new BehaviorSubject<CartProduct[]>(this.cartProducts!);
    cartObservable$ = this.cartSubject.asObservable();

    constructor(private http: HttpClient, private productService: ProductService) {
        const cartStr = localStorage.getItem('cart');
        if (cartStr) {
            this.cartProducts = JSON.parse(cartStr);
            this.cartSubject.next([...this.cartProducts!]);
        }
    }

    addToCart(id?: string) {
        console.log("add to cart");
        const product = this.cartProducts?.find(p => p.id === id);
        if (product && product.count) {
            product.count += 1;
        } else {
            this.cartProducts?.push({ id, count: 1 });
        }
        localStorage.setItem('cart', JSON.stringify(this.cartProducts));
        this.cartSubject.next([...this.cartProducts!]);
    }

    findCartProducts(cartProducts: Product[]): Product[] {
        let products: Product[] = [];
        cartProducts.forEach(cartProduct => {
            let product: Product;
            this.productService.findProduct(cartProduct.id!).subscribe(
                (res) => {
                    product = res;
                    products.push(product);
                }
            );
        });
        return products;
    }

}

