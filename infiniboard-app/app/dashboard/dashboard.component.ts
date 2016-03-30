import {Component, Input, OnInit} from 'angular2/core';
import {RouteParams} from 'angular2/router';
import {Dashboard} from './dashboard';
import {DashboardService} from './dashboard.service';

@Component({
  selector: 'dashboard',
  templateUrl: 'app/dashboard/dashboard.component.html'
})

export class DashboardComponent implements OnInit {

  @Input()
  public dashboard: Dashboard;

  private _dashboardService: DashboardService;
  private _routeParams: RouteParams;

  constructor(_dashboardService: DashboardService, _routeParams: RouteParams) {
    this._dashboardService = _dashboardService;
    this._routeParams = _routeParams;
  }

  ngOnInit() {
    // route params are always strings
    // + converts the string to a number
    let id = +this._routeParams.get('id');

    this._dashboardService.getDashboard(id)
      .then(dashboard => this.dashboard = dashboard);
  }
}
