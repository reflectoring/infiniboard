import {WidgetService} from './widget.service';
import {WidgetConfig} from '../dashboard/widget-config';

export class WidgetComponent {

  private title: string;
  private updateInterval: number;
  private widgetConfig: WidgetConfig;

  private widgetService: WidgetService;

  public constructor(widgetService: WidgetService) {
    this.widgetService = widgetService;
  }

  public getTitle(): string {
    return this.title;
  }

  public setTitle(title: string) {
    this.title = title;
  }

  public getUpdateInterval(): number {
    return this.updateInterval;
  }

  public setUpdateInterval(value: number) {
    this.updateInterval = value;
  }


  public initWidget(widgetConfig: WidgetConfig) {
    this.widgetConfig = widgetConfig;
    this.setTitle(widgetConfig.title);
    this.triggerUpdate();
  }

  public triggerUpdate() {
    setInterval(() => {
      this.updateWidgetData();
    }, this.getUpdateInterval());
  }

  public updateWidgetData() {
    this.widgetService.fetchWidgetData(this.widgetConfig).subscribe(
      widgetData => {
        if (widgetData.length > 0) {
          this.updateData(widgetData);
        }
      },
      error => console.error(error)
    );
  }

  public updateData(data: any) {
    // implemented by widgets
  }
}
