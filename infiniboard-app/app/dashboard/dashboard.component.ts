import {Component, Input, OnInit, DynamicComponentLoader, ElementRef} from 'angular2/core';
import {RouteParams} from 'angular2/router';
import {Dashboard} from './dashboard';
import {DashboardService} from './dashboard.service';
import {PlatformStatusWidgetComponent} from '../widget/platform-status/platform-status-widget.component';
import {WidgetConfig} from './widget-config';

@Component({
  selector: 'dashboard',
  directives: [PlatformStatusWidgetComponent],
  templateUrl: 'app/dashboard/dashboard.component.html'
})

export class DashboardComponent implements OnInit {

  @Input()
  public dashboard: Dashboard;

  private elementRef: ElementRef;
  private dynamicComponentLoader: DynamicComponentLoader;

  private _dashboardService: DashboardService;
  private _routeParams: RouteParams;

  constructor(dynamicComponentLoader: DynamicComponentLoader, elementRef: ElementRef,
              dashboardService: DashboardService, routeParams: RouteParams) {

    this.dynamicComponentLoader = dynamicComponentLoader;
    this.elementRef = elementRef;
    this._dashboardService = dashboardService;
    this._routeParams = routeParams;
  }

  ngOnInit() {
    // route params are always strings
    // + converts the string to a number
    let id = +this._routeParams.get('id');

    this._dashboardService.getDashboard(id)
      .then(dashboard => this.initializeDashboard(dashboard));
  }

  private initializeDashboard(dashboard: Dashboard) {
    this.dashboard = dashboard;

    this.initializeWidgets();
  }

  private initializeWidgets() {
    let widgetConfigs = this.dashboard.widgetConfigs;
    widgetConfigs.forEach(widget => this.initializeWidget(widget));
  }

  private initializeWidget(widgetConfig: WidgetConfig) {
    let widgetComponent = this.getWidgetComponentByType(widgetConfig.type);
    this.dynamicComponentLoader.loadIntoLocation(widgetComponent, this.elementRef, 'widgets');
  }

  private getWidgetComponentByType(widgetType: string) {
    switch (widgetType) {
      case 'platform-status':
        return PlatformStatusWidgetComponent;
    }
  }
}
