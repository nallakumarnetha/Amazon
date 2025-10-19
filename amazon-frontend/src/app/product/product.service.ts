import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Product, ProductListResponse } from "./product.model";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  private baseUrl = 'http://localhost:8080/amazon/products'

  //CRUD start

  findAllProducts(page: number, size: number): Observable<ProductListResponse> {
    return this.http.get<ProductListResponse>(this.baseUrl+'?page='+page+'&size='+size);
  }

  findProduct(id: string): Observable<Product> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.baseUrl, product);
  }

  updateProduct(id: string | undefined, product: Product): Observable<Product> {
    return this.http.put(`${this.baseUrl}/${id}`, product);
  }

  deleteProduct(id: string): Observable<ProductListResponse> {
    return this.http.delete<ProductListResponse>(`${this.baseUrl}/${id}`);
  }

  //CRUD end

}