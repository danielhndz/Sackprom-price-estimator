import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { SigninForm } from 'src/app/component/user/user-session/sign-in/signinForm';
import { JwtResponse } from './jwt-response';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  signinUrl = 'http://localhost:8080/api/auth/sign-in';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    console.log("authentication.service - constructor");
  }

  getJwt(signinForm: SigninForm): Observable<JwtResponse> {
    console.log("authentication.service - getJwt");
    return this.http.post<JwtResponse>(this.signinUrl, signinForm)
      .pipe(
        tap(
          jwtResponse => {
            console.log("authentication.service - getJwt - jwtResponse");
            let authorization = jwtResponse.typeToken + ' ' + jwtResponse.accessToken;
            sessionStorage.setItem('authorization', authorization);
            sessionStorage.setItem('username', signinForm.username);
            return jwtResponse;
          }
        ),
        catchError(
          this.handleError<JwtResponse>('getJwt')
        )
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    console.log("authentication.service - getJwt - handleError")
    return (error: any): Observable<T> => {

      // Send the error to remote logging infrastructure
      console.error(error); // Log to console instead

      // Let the app keep running by returning an empty result
      return of(result as T);
    };
  }

  isUserAuthenticated(): boolean {
    console.log("authentication.service - isUserAuthenticated");
    let user = sessionStorage.getItem('authorization')
    return !(user === null)
  }

  signOut(): void {
    console.log("authentication.service - signOut");
    sessionStorage.clear();
    this.router.navigate(['']);
  }
}
