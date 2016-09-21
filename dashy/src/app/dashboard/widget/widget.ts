import {WidgetConfig} from '../shared/widget-config';
import {WidgetService} from '../shared/widget.service';
export class Widget {

  title: string;
  updateInterval: number = 1000;
  widgetConfig: WidgetConfig;

  public constructor(private widgetService: WidgetService) {
  }

  public getTitle(): string {
    return this.title;
  }

  public initWidget(widgetConfig: WidgetConfig) {
    this.widgetConfig = widgetConfig;
    this.title = widgetConfig.title;
    this.triggerUpdate();
  }

  public triggerUpdate() {
    setInterval(() => {
      this.updateWidgetData();
    }, this.updateInterval);
  }

  public updateWidgetData() {
    this.widgetService.getWidgetData(this.widgetConfig).subscribe(
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

