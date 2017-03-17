import {WidgetConfig} from '../shared/widget-config';
import {WidgetService} from '../shared/widget.service';
export class Widget {

  title: string;
  updateInterval: number = 1000;
  widgetConfig: WidgetConfig;
  titleUrl: string;
  description: string;

  constructor(private widgetService: WidgetService) {
  }

  getTitle(): string {
    return this.title;
  }

  initWidget(widgetConfig: WidgetConfig) {
    this.widgetConfig = widgetConfig;
    this.title = widgetConfig.title;
    this.titleUrl = widgetConfig.titleUrl;
    this.description = widgetConfig.description;
    this.triggerUpdate();
  }

  triggerUpdate() {
    setInterval(() => {
      this.updateWidgetData();
    }, this.updateInterval);
  }

  updateWidgetData() {
    this.widgetService.getWidgetData(this.widgetConfig).subscribe(
      widgetData => {
        if (widgetData.length > 0) {
          this.updateData(widgetData);
        }
      },
      error => console.error(error)
    );
  }

  updateData(data: any) {
    // implemented by widgets
  }
}

