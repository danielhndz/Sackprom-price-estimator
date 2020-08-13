import { Component, OnInit } from '@angular/core';
import { HttpClientService } from '../../../../service/http-client/http-client.service';
import { SignupForm } from './signupForm';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  roles: Set<String> = new Set<String>().add("user").add("admin");

  /*user: User = new User("angular3", "angular3", "angular3", ["pm"], "123456");*/

  signupForm: SignupForm = new SignupForm("", "", "", "", ["user"]);

  constructor(
    private httpClientService: HttpClientService
  ) { }

  ngOnInit(): void {
  }

  signUp(): void {
    console.log(this.roles);
    this.httpClientService.signUp(this.signupForm)
    .subscribe(
      data => {alert("User successfully registered.")}
    );
  }
}
