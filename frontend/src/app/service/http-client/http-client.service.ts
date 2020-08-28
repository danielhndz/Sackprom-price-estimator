import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SignupForm } from '../../component/user/user-session/sign-up/signupForm';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(
    private http: HttpClient
  ) {
    console.log("http-client.service - constructor");
  }

  getUsers() {
    console.log("http-client.service - getUsers");
    return this.http.get('http://localhost:8080/showList');
  }

  /**
   * signUp
   */
  signUp(signupForm: SignupForm) {
    console.log("http-client.service - signUp");
    return this.http.post<any>("http://localhost:8080/api/auth/signup", signupForm)
  }

}
