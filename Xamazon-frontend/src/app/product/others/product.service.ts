import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from './product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  private baseUrl = 'http://localhost:8080/amazon/products'

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.baseUrl, product);
  }
}
