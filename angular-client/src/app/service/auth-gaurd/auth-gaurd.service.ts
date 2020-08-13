import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { AuthenticationService } from '../authentication/authentication.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGaurdService implements CanActivate {

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
    console.log("auth-gaurd.component - constructor");
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): 
  boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> 
  {
    console.log("auth-gaurd.component - constructor");
    if (this.authService.isUserAuthenticated()) {
      console.log("auth-gaurd.component - constructor - if");
      return true;
    } else {
      console.log("auth-gaurd.component - constructor - else");
      this.router.navigate(['sign-in']);
      return false;
    }
  }
}
