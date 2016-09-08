import {Component, OnInit} from '@angular/core';
import {DashboardService} from '../shared/dashboard.service';
import {Dashboard} from '../shared/dashboard';

@Component({
  selector: 'dashboard-sidebar-links',
  templateUrl: './dashboard-sidebar-links.component.html',
  styleUrls: ['./dashboard-sidebar-links.component.css']
})
export class DashboardSidebarLinksComponent implements OnInit {

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
}
