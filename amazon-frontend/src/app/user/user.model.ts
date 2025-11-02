
export class User {
    id?: string;
    user_id?: string;
    first_name?: string;
    last_name?: string;
    name?: string;
    phone_number?: string;
    gender?: string;
    address?: Address;
    role?: string;
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
    MALE, FEMALE, OTHERS
}

export enum Role {
    ENDUSER, SELLER, ADMIN
}

export enum Language {
    TELUGU, HINDI, ENGLISH
}
