import {WidgetConfig} from './widget-config';

export class Dashboard {

  public id: String;
  public title: String;
  public description: String;

  public widgetConfigs: WidgetConfig[];

  public constructor(id: String, title: String, description: String, widgetConfigs: WidgetConfig[]) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.widgetConfigs = widgetConfigs;
  }
}
