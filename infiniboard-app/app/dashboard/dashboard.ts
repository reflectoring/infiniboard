import {WidgetConfig} from './widget-config';

export class Dashboard {

  public id: number;
  public name: String;
  public description: String;

  public widgetConfigs: WidgetConfig[];

  public constructor(id: number, name: String, description: String, widgetConfigs: WidgetConfig[]) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.widgetConfigs = widgetConfigs;
  }
}
