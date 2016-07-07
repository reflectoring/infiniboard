import {Component, OnInit} from 'angular2/core';
import {Router} from 'angular2/router';
import {Dashboard} from './dashboard';
import {DashboardService} from './dashboard.service';

@Component({

  selector: 'dashboard-links',
  templateUrl: 'app/dashboard/dashboard-links.component.html',
})

export class DashboardLinksComponent implements OnInit {

  public dashboards: Dashboard[];

  private _router: Router;
  private _dashboardService: DashboardService;

  constructor(_router: Router, _dashboardService: DashboardService) {
    this._router = _router;
    this._dashboardService = _dashboardService;
  }

  ngOnInit() {
    this.getDashboards();
  }

  public gotoDashboard(dashboard: Dashboard) {
    let link = ['Dashboard', {id: dashboard.id}];
    this._router.navigate(link);
  }

  private getDashboards() {
    console.log('getting dashboards from service');
    this._dashboardService.getDashboards().subscribe(
      dashboards => {
        this.dashboards = dashboards;
        console.log(this.dashboards);
      },
      error => console.error(error)
    );
  }

}
