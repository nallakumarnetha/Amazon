import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { FilterRequest } from "../common/common.model";
import { Preferences } from "./preferences.model";

@Injectable({
    providedIn: 'root'
})
export class PreferencesService {

    // BehaviorSubject
    preferences: Preferences = {};
    preferencesSubject = new BehaviorSubject<Preferences>(this.preferences!);
    preferencesObservable$ = this.preferencesSubject.asObservable();

    constructor(private http: HttpClient) {
        this.findPreferences();
    }

    // private baseUrl = 'http://localhost:8080/amazon/preferences' // monolithic
     private baseUrl = 'http://localhost:8088/common/preferences' // microservices

    findPreferences(): void {
        this.http.get(`${this.baseUrl}`).subscribe(res => {
            this.preferences = res;
            this.updatePreferencesSubject();
        });
    }

    updatePreferences(preferences: Preferences): void {
        this.http.put<Preferences>(`${this.baseUrl}`, preferences).subscribe(res => {
            this.preferences = res;
            this.updatePreferencesSubject();
        });
    }

    updatePreferencesSubject() {
        this.preferencesSubject.next(this.preferences);
    }

}