export class Product {
    id?: string;
    name?: string;
    price?: number;
    files?: string[];
}

export class ProductListResponse {
    start: number = 0;
    rows: number = 10;
    products?: Product[];
}