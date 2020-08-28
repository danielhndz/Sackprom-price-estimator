import { Component, OnInit } from '@angular/core';
import { HttpClientService } from '../../../service/http-client/http-client.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: string[];

  constructor(
    private httpClientService: HttpClientService
  ) { }

  ngOnInit(): void {
    this.httpClientService.getUsers().subscribe(
      response => this.handleSuccessfulResponse(response),
    );
  }
  
  handleSuccessfulResponse(response: any): void {
    this.users = response;
  }

}
