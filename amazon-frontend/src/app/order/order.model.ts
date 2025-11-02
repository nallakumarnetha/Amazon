import { Category } from "../product/product.model";

export interface Order {
    id?: string;
    user_id?: string;
    product_id?: string;
    count?: number;
    amount?: number;
    payment_id?: string;
    status?: OrderStatus;
    category?: Category
}

export class OrderListResponse {
    start: number = 0;
    rows: number = 10;
    total: number = 0;
    orders?: Order[];
}

export enum OrderStatus {
    Pending = "Pending",
    Processing = "Processing",
    Shipped = "Shipped",
    Delivered = "Delivered",
    Cancelled = "Cancelled",
    Returned = "Returned"
}


