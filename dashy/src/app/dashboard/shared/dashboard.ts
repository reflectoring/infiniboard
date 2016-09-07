import {WidgetConfig} from './widget-config';
export class Dashboard {

  public id: number;
  public name: string;
  public description: string;

  public widgetConfigsLink: string;

  public widgetConfigs: WidgetConfig[];

  public constructor(id: number, name: string, description: string, widgetsLink: string) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.widgetConfigsLink = widgetsLink;
    this.widgetConfigs = [];
  }
}
