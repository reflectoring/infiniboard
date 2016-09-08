import {Component, OnInit, Input, ViewContainerRef, ComponentFactoryResolver, ViewChild} from '@angular/core';
import {Dashboard} from '../shared/dashboard';
import {DashboardService} from '../shared/dashboard.service';
import {WidgetConfig} from '../shared/widget-config';
import {WidgetService} from '../shared/widget.service';
import {ActivatedRoute} from '@angular/router';
import {ConcreteType} from '@angular/core/src/facade/lang';
import {PlatformStatusWidgetComponent} from '../widget/platform-status-widget/platform-status-widget.component';

@Component({
  selector: 'dashboard-detail',
  entryComponents: [
    PlatformStatusWidgetComponent
  ],
  templateUrl: './dashboard-detail.component.html',
  styleUrls: ['./dashboard-detail.component.css']
})
export class DashboardDetailComponent implements OnInit {

  @Input()
  public dashboard: Dashboard;

  @ViewChild('widgets', {read: ViewContainerRef})
  private viewContainer: ViewContainerRef;

  constructor(private dashboardService: DashboardService,
              private widgetService: WidgetService,
              private route: ActivatedRoute,
              private cfr: ComponentFactoryResolver) {
  }

  ngOnInit() {
    this.route.params
      .flatMap(params => this.dashboardService.getDashboard(params['dashboardId']))
      .subscribe(dashboard => this.initializeDashboard(dashboard),
        error => console.error(error));
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
    let cf = this.cfr.resolveComponentFactory(widgetComponent);
    this.viewContainer.createComponent(cf);
    // let promise = this.dynamicComponentLoader.loadIntoLocation(widgetComponent, this.elementRef, 'widgets');
    // Promise.resolve(promise).then(
    //   component => {
    //     component.instance.setUpdateInterval(1000);
    //     component.instance.initWidget(widgetConfig);
    //     component.instance.updateWidgetData();
    //   });
  }

  private getWidgetComponentByType(widgetType: string): ConcreteType<any> {
    switch (widgetType) {
      case 'platform-status':
        return PlatformStatusWidgetComponent;
      //
      // case 'jenkins-job':
      //   return JenkinsJobWidgetComponent;

      default:
        throw new Error('unknown widget type \'' + widgetType + '\'');
    }
  }
}
