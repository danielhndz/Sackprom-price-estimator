import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserPrinciple } from './user-principle';
import { AuthenticationService } from '../authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private getUserUrl: string;
  private user: UserPrinciple;

  constructor(
    private http: HttpClient,
    private authService: AuthenticationService
  ) {
    console.log("user.service - constructor");

    this.getUserUrl = 'http://localhost:8080/api/user/user-principle';

    console.log("username = " + sessionStorage.getItem('username'));
    console.log("this.user:");
    console.log(this.user);

    if (sessionStorage.getItem('username') && !this.user) {
      console.log("user.service - constructor - if");
      this.initUser(sessionStorage.getItem('username'));
    }
  }

  initUser(username: string): void {
    console.log("user.service - initUser");
    this.initUserFromServer(username);
  }

  /**
   * 
   * @param username 
   */
  private initUserFromServer(username: string): void {
    console.log("user.service - initUserFromServer");
    this.http.get<UserPrinciple>(this.getUserUrl + '/' + username).subscribe(
      response => {
        console.log("user.service - initUserFromServer - response");
        console.log("response:");
        console.log(response);
        this.setUserWithServerResponse(response);
        return response;
      }, 
      error => {
        console.log("user.service - initUserFromServer - error");
        console.log("error:");
        console.log(error);
        this.authService.signOut();
        return error;
      }
    );
  }

  private setUserWithServerResponse(response: UserPrinciple): void {
    console.log("user.service - setUserWithServerResponse");
    /*this.user = new UserPrinciple(
      response.id,
      response.name,
      response.username,
      response.email,
      response.authorities
    );*/
    this.user = response;
    console.log("this.user:");
    console.log(this.user);
  }

  getUser(): UserPrinciple {
    return this.user;
  }
}
