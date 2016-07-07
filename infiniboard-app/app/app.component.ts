import {Component} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS} from 'angular2/router';
import {HTTP_PROVIDERS} from 'angular2/http';
import {JenkinsJobWidgetComponent} from './widget/jenkins/jenkins-job-widget.component';
import {DashboardLinksComponent} from './dashboard/dashboard-links.component';
import {DashboardService} from './dashboard/dashboard.service';
import {DashboardHomeComponent} from './dashboard/dashboard-home.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {WidgetService} from './widget/widget.service';

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
    ROUTER_PROVIDERS,
    HTTP_PROVIDERS,
    DashboardService,
    WidgetService
  ],
  selector: '[infiniboard]',
  styleUrls: ['app/app.component.css'],
  templateUrl: 'app/app.component.html',
})

export class AppComponent {
}
