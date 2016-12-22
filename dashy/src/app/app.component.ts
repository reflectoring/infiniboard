import {Component, OnInit} from '@angular/core';
import {InfoService} from './shared/info.service';

@Component({
  selector: 'dashy-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  version: string;

  constructor(private infoService: InfoService) {

  }

  ngOnInit(): void {
    this.infoService.getServerInfo().subscribe(
      infos => this.version = infos.version,
      error => console.error(error)
    );
  }

}
