export class Product {
    id?: string;
    name?: string;
    price?: number;
}

export class ProductListResponse {
    start: number = 0;
    rows: number = 10;
    products?: Product[];
}