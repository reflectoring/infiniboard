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
    let widgetUpdate = this._widgetService.getWidgetData(this.getId());
    Promise.resolve(widgetUpdate).then(
      update => {
        this.updateData(update.data);
      }
    );
  }

  public updateData(data: any) {
    // implemented by widgets
  }
}
