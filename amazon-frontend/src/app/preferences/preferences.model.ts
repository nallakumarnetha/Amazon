export class Preferences {
    id?: string;
    user_id?: string;
    ai?: boolean;
    color?: Color;
    text_value?: string;
    hex_color?: string;
    prime?: boolean;
}

export enum Color {
    Red = 'Red',
    Blue = 'Blue',
    Green = 'Green'
}