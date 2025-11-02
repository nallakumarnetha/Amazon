import { Status } from "../cart/cart.component";

export class Product {
  id?: string;
  product_id?: string;
  name?: string;
  price?: number;
  files?: string[];
  base64_files?: Map<string, string>;
  count?: number;
  category?: Category;
  cart_count?: number; // CartProduct
  status?: Status;    // CartProduct
}

export class ProductListResponse {
  start: number = 0;
  rows: number = 10;
  total: number = 0;
  products?: Product[];
}

export enum Category {
  All = 'All',
  Arts = 'Arts',
  Baby = 'Baby',
  Beauty = 'Beauty',
  Books = 'Books',
  Clothes = 'Clothes',
  Electronics = 'Electronics',
  Footwear = 'Footwear',
  Furniture = 'Furniture',
  Grocery = 'Grocery',
  Home_appliances = 'Home_appliances',
  Men = 'Men',
  Women = 'Women'
}


export enum PriceRange {
  BELOW_100 = "Below 100",
  BETWEEN_100_AND_500 = "100 - 500",
  BETWEEN_500_AND_1000 = "500 - 1000",
  ABOVE_1000 = "Above 1000"
}

export enum CountRange {
  BELOW_10 = "Below 10",
  BETWEEN_10_AND_50 = "10 - 50",
  BETWEEN_50_AND_100 = "50 - 100",
  ABOVE_100 = "Above 100",
  //   OUT_OF_STOCK = "Out of stock"
}
