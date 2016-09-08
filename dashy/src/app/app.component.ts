import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'dashy',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public ngOnInit(): void {
    // every component which defines the div with the class
    // content-wrapper must call this fix method on init
    // to recalculate the height of the window
    // jQuery.AdminLTE.layout.fix();
  }

}
