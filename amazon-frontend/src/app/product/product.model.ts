export class Product {
    id?: string;
    product_id?: string;
    name?: string;
    price?: number;
    files?: string[];
    base64_files?: Map<string, string>;
    cart_count?: number;
}

export class ProductListResponse {
    start: number = 0;
    rows: number = 10;
    total: number = 0;
    products?: Product[];
}
