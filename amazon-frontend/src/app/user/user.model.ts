
export class User {
    id?: string;
    user_id?: string;
    first_name?: string;
    last_name?: string;
    name?: string;
    phone_number?: string;
    gender?: Gender;
    address?: string;
    role?: Role;
    language?: Language;
    files?: string[];
    base64_files?: Map<string, string>;
}

export class Address {
    id?: string;
    street?: string;
    city?: string;
    pincode?: string;
}

export enum Gender {
    Male = 'Male',
    Female = 'Female',
    Others = 'Others'
}

export enum Role {
    Enduser = 'Enduser',
    Seller = 'Seller',
    Admin = 'Admin'
}

export enum Language {
    Telugu = 'Telugu',
    Hindi = 'Hindi',
    English = 'English'
}
