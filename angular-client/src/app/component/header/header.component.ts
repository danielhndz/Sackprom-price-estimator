import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(
    public authService:AuthenticationService
  ) {
    console.log("header.component - constructor");
  }

  ngOnInit(): void {
    console.log("header.component - ngOnInit");
  }

}
