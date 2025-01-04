import { HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";

export const authInterceptor: HttpInterceptorFn = (
    req: HttpRequest<any>,
    next: HttpHandlerFn
): Observable<HttpEvent<any>> => {

    const router = inject(Router);
    const now = Math.floor(new Date().getTime() / 1000);
    const expireTime = Number(localStorage.getItem('expireTime'));

    if (now > expireTime) {
        router.navigate(['/auth/login']);
    }

    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
        const cloned = req.clone({
            setHeaders: {
                Authorization: 'Bearer ' + accessToken,
            }
        });
        return next(cloned);
    } else {
        return next(req);
    }

}