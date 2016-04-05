import {WidgetConfig} from 'widget-config';

export class Dashboard {

  public id: number;
  public name: String;
  public description: String;
  public active: boolean;

  public widgetConfigs: WidgetConfig[];

  public constructor(id: number, name: String, description: String, active: boolean, widgetConfigs: WidgetConfig[]) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.active = active;
    this.widgetConfigs = widgetConfigs;
  }
}
