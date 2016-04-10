import {Component, Input, OnInit, DynamicComponentLoader, ElementRef} from 'angular2/core';
import {RouteParams} from 'angular2/router';
import {Type} from 'angular2/src/facade/lang';
import {Dashboard} from './dashboard';
import {DashboardService} from './dashboard.service';
import {WidgetConfig} from './widget-config';
import {PlatformStatusWidgetComponent} from '../widget/platform-status/platform-status-widget.component';
import {JenkinsJobWidgetComponent} from '../widget/jenkins/jenkins-job-widget.component';

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
    let promise = this.dynamicComponentLoader.loadIntoLocation(widgetComponent, this.elementRef, 'widgets');
    Promise.resolve(promise).then(
      component => {
        component.instance.setId(widgetConfig.id);
        component.instance.updateWidgetData();
      });
  }

  private getWidgetComponentByType(widgetType: string): Type {
    switch (widgetType) {
      case 'platform-status':
        return PlatformStatusWidgetComponent;

      case 'jenkins-job':
        return JenkinsJobWidgetComponent;
    }
  }
}
