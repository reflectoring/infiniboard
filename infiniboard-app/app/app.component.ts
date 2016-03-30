import {Component} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS} from 'angular2/router';
import {JenkinsJobWidgetComponent} from './widget/jenkins/jenkins-job-widget.component';
import {JenkinsService} from './widget/jenkins/jenkins.service';
import {DashboardLinksComponent} from './dashboard/dashboard-links.component';
import {DashboardService} from './dashboard/dashboard.service';
import {DashboardHomeComponent} from './dashboard/dashboard-home.component';
import {DashboardComponent} from './dashboard/dashboard.component';


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
    DashboardService,
    JenkinsService,
  ],
  selector: '[infiniboard]',
  styleUrls: ['app/app.component.css'],
  templateUrl: 'app/app.component.html',
})

export class AppComponent {
}
