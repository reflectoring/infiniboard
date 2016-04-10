export class WidgetData {

  widgetId: string;
  data: any;

  public constructor(widgetId: string, data: any) {
    this.widgetId = widgetId;
    this.data = data;
  }
}
