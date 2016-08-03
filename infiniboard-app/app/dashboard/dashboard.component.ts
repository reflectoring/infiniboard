import {Component, Input, OnInit, DynamicComponentLoader, ElementRef} from 'angular2/core';
import {RouteParams} from 'angular2/router';
import {Type} from 'angular2/src/facade/lang';
import {Dashboard} from './dashboard';
import {DashboardService} from './dashboard.service';
import {WidgetConfig} from './widget-config';
import {PlatformStatusWidgetComponent} from '../widget/platform-status/platform-status-widget.component';
import {JenkinsJobWidgetComponent} from '../widget/jenkins/jenkins-job-widget.component';
import {WidgetService} from '../widget/widget.service';

@Component({
  selector: 'dashboard',
  directives: [PlatformStatusWidgetComponent],
  templateUrl: 'app/dashboard/dashboard.component.html'
})

export class DashboardComponent implements OnInit {

  @Input()
  public dashboard: Dashboard;

  private widgetService: WidgetService;

  private elementRef: ElementRef;
  private dynamicComponentLoader: DynamicComponentLoader;

  private dashboardService: DashboardService;
  private routeParams: RouteParams;

  constructor(dynamicComponentLoader: DynamicComponentLoader, elementRef: ElementRef,
              dashboardService: DashboardService, widgetService: WidgetService, routeParams: RouteParams) {

    this.dynamicComponentLoader = dynamicComponentLoader;
    this.elementRef = elementRef;
    this.dashboardService = dashboardService;
    this.widgetService = widgetService;
    this.routeParams = routeParams;
  }

  ngOnInit() {
    // route params are always strings
    // + converts the string to a number
    let id = +this.routeParams.get('id');

    this.dashboardService.getDashboard(id).subscribe(
      dashboard => this.initializeDashboard(dashboard),
      error => console.error(error)
    );
  }

  private initializeDashboard(dashboard: Dashboard) {
    this.dashboard = dashboard;
    this.widgetService.fetchWidgets(this.dashboard).subscribe(
      widgetConfigs => this.initializeWidgets(widgetConfigs),
      error => console.error(error)
    );
  }

  private initializeWidgets(widgetConfigs: WidgetConfig[]) {
    widgetConfigs.forEach(widget => this.initializeWidget(widget));
  }

  private initializeWidget(widgetConfig: WidgetConfig) {
    let widgetComponent = this.getWidgetComponentByType(widgetConfig.type);
    let promise = this.dynamicComponentLoader.loadIntoLocation(widgetComponent, this.elementRef, 'widgets');
    Promise.resolve(promise).then(
      component => {
        component.instance.setUpdateInterval(1000);
        component.instance.initWidget(widgetConfig);
        component.instance.updateWidgetData();
      });
  }

  private getWidgetComponentByType(widgetType: string): Type {
    switch (widgetType) {
      case 'platform-status':
        return PlatformStatusWidgetComponent;

      case 'jenkins-job':
        return JenkinsJobWidgetComponent;

      default:
        throw new Error('unknown widget type \'' + widgetType + '\'');
    }
  }
}
