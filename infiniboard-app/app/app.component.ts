import {Component, OnInit} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES} from 'angular2/router';
import {HTTP_PROVIDERS} from 'angular2/http';
import {JenkinsJobWidgetComponent} from './widget/jenkins/jenkins-job-widget.component';
import {DashboardLinksComponent} from './dashboard/dashboard-links.component';
import {DashboardService} from './dashboard/dashboard.service';
import {DashboardHomeComponent} from './dashboard/dashboard-home.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {WidgetService} from './widget/widget.service';
import {StatusService} from './widget/platform-status/status.service';

@RouteConfig([{
  path: '/dashboards',
  name: 'DashboardHome',
  component: DashboardHomeComponent,
  useAsDefault: true,
}, {
  path: '/dashboards/:id',
  name: 'Dashboard',
  component: DashboardComponent,
}])

@Component({
  directives: [
    ROUTER_DIRECTIVES,
    DashboardLinksComponent,
    JenkinsJobWidgetComponent],
  providers: [
    HTTP_PROVIDERS,
    DashboardService,
    WidgetService,
    StatusService
  ],
  selector: '[infiniboard]',
  styleUrls: ['app/app.component.css'],
  templateUrl: 'app/app.component.html',
})

export class AppComponent implements OnInit {

  public ngOnInit(): any {
    // every component which defines the div with the class
    // content-wrapper must call this fix method on init
    // to recalculate the height of the window
    $.AdminLTE.layout.fix();
  }

}
