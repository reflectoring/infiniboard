import {Component, OnInit} from '@angular/core';
import {DashboardService} from '../shared/dashboard.service';
import {Dashboard} from '../shared/dashboard';
import {FormControl} from '@angular/forms';
import {Response} from '@angular/http';

@Component({
  selector: 'dashboard-sidebar-links',
  templateUrl: './dashboard-sidebar-links.component.html',
  styleUrls: ['./dashboard-sidebar-links.component.css']
})
export class DashboardSidebarLinksComponent implements OnInit {

  public name = new FormControl('');

  public slug = new FormControl('');

  public description = new FormControl('');

  public error = new FormControl('');

  public dashboards: Dashboard[];

  constructor(private dashboardService: DashboardService) {
  }

  ngOnInit() {
    this.getDashboards();
  }

  private getDashboards() {
    this.dashboardService.getDashboards().subscribe(
      dashboards => {
        this.dashboards = dashboards;
      },
      error => console.error(error)
    );
  }

  public createDashboard() {
    const request = this.dashboardService.createDashboard(this.name.value, this.slug.value, this.description.value);
    request
    .map(this.extractData)
    .subscribe();
  }

  private extractData(res: Response) {
    const body = res.json();
    const error = ((body || {}).errors || {});
    console.error(body || {});
    this.error.setValue(body);
    return body || {};
  }
}
