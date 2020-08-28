import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../../../service/authentication/authentication.service';
import { UserService } from '../../../../service/user-principle/user.service';
import { SigninForm } from './signinForm';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  form: SigninForm;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private userService: UserService
  ) {
    this.form = new SigninForm('', '');
    console.log("sign-in.component - constructor");
  }

  ngOnInit(): void {
    console.log("sign-in.component - ngOnInit");
  }

  signIn(): void {
    console.log("sign-in.component - signIn");
    this.authService.getJwt(this.form).subscribe(
      response => {
        console.log("sign-in.component - signIn - response!");
        console.log("response:");
        console.log(response);
        this.signinSuccessful(this.form.username);
      },
      error => {
        console.log("sign-in.component - signIn - error!");
        console.log("error:");
        console.log(error);
      }
    );
  }

  signinSuccessful(username: string): void {
    console.log("sign-in.component - signinSuccessful");
    this.userService.initUser(username);
    this.router.navigate(['home']);
  }

}
