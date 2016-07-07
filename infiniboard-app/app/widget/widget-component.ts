import {WidgetService} from './widget.service';

export class WidgetComponent {

  private id: string;
  private title: string;
  private updateInterval: number;
  private widgetService: WidgetService;

  public constructor(widgetService: WidgetService) {
    this.widgetService = widgetService;
  }

  public getId() {
    return this.id;
  }

  public setId(id: string) {
    this.id = id;
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

  public initWidget(id: string, title: string) {
    this.setId(id);
    this.setTitle(title);
    this.triggerUpdate();
  }

  public triggerUpdate() {
    setInterval(() => {
      this.updateWidgetData();
    }, this.getUpdateInterval());
  }

  public updateWidgetData() {
    this.widgetService.getWidgetData(this.getId()).subscribe(
      widgetData => {
        if (widgetData.length > 0) {
          this.updateData(widgetData.data);
        }
      },
      error => console.error(error)
    );
  }

  public updateData(data: any) {
    // implemented by widgets
  }
}
