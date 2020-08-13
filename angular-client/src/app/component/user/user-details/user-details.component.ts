import { Component, OnInit, Input } from '@angular/core';
import { UserService } from 'src/app/service/user-principle/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  constructor(
    public userService: UserService
  ) {}

  ngOnInit(): void {}
}
