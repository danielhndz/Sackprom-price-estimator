import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor() {
    console.log("http-interceptor.service - constructor");
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    console.log("http-interceptor.service - intercept");
    if (sessionStorage.getItem('authorization')) {
      console.log("http-interceptor.service - intercept - if");
      req = req.clone({
        setHeaders: {
          Authorization: sessionStorage.getItem('authorization')
        }
      });
    }

    return next.handle(req);
  }
}
