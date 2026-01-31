import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileService {

constructor(private http: HttpClient) { }

  // private baseUrl = 'http://localhost:8080/amazon/files' // monolithic
  private baseUrl = 'http://localhost:8088/file/files' // microservices

   uploadFiles(files: File[]): Observable<string[]> {
    let formData = new FormData();
    files.forEach(file=>formData.append('files', file));
      return this.http.post<string[]>(this.baseUrl, formData);
    }
}
