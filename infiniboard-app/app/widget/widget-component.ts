import {WidgetService} from './widget.service';

export class WidgetComponent {

  private _id: string;
  private _updateInterval: number;
  private _widgetService: WidgetService;

  public constructor(widgetService: WidgetService) {
    this._widgetService = widgetService;
  }

  public getId() {
    return this._id;
  }

  public setId(id: string) {
    this._id = id;
  }

  public getUpdateInterval(): number {
    return this._updateInterval;
  }

  public setUpdateInterval(value: number) {
    this._updateInterval = value;
  }

  public initWidget(id: string) {
    this.setId(id);
    this.triggerUpdate();
  }

  public triggerUpdate() {
    setInterval(() => {
      this.updateWidgetData();
    }, this.getUpdateInterval());
  }

  public updateWidgetData() {
    this._widgetService.getWidgetData(this.getId()).subscribe(
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
