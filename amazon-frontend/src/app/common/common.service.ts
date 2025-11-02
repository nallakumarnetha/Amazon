import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Category } from '../product/product.model';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class CommonService {

    private searchSubject = new BehaviorSubject<string>('');
    search$ = this.searchSubject.asObservable();

    private categorySubject = new BehaviorSubject<Category>(Category.All);
    category$ = this.categorySubject.asObservable();

    constructor(private http: HttpClient) {
    }

    updateSearch(query: string) {
        this.searchSubject.next(query);
    }

    updateCategory(category: Category) {
        this.categorySubject.next(category);
    }

    dumpData() {
        let baseUrl = 'http://localhost:8080/amazon/common/dump-data';
        this.http.get(baseUrl).subscribe();
    }

    clearData() {
        let baseUrl = 'http://localhost:8080/amazon/common/clear-data';
        this.http.delete(baseUrl).subscribe();
    }

}
