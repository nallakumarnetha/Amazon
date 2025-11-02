import { OrderStatus } from "../order/order.model";
import { Category } from "../product/product.model";

export class FilterRequest {
  min_price?: number;
  max_price?: number;
  min_count?: number;
  max_count?: number;
  order_status?: OrderStatus;
  category?:Category;
}
