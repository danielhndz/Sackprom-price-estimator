import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor() {
    console.log("footer.component - constructor");
  }

  ngOnInit(): void {
    console.log("footer.component - ngOnInit");
  }

}
