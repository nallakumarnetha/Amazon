import { HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router, RouterConfigOptions } from "@angular/router";
import { catchError, throwError } from "rxjs";

@Injectable()
export class MyInterceptor implements HttpInterceptor {

    constructor(private router: Router) { }

    // for any http call, if response is 401 then redirect to login page
    intercept(req: HttpRequest<any>, next: HttpHandler) {
        const modifiedReq = req.clone({
            withCredentials: true
        });
        return next.handle(modifiedReq).pipe(
            catchError((err: any) => {
                if (err instanceof HttpErrorResponse && err.status === 401) {
                    this.router.navigate(['/login']);
                }
                return throwError(() => err);
            })
        );
    }

}
