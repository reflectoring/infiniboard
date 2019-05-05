import {WidgetConfig} from './widget-config';
export class Dashboard {

  public id: number;
  public name: string;
  public slug: string;
  public description: string;

  public widgetConfigsLink: string;

  public widgetConfigs: WidgetConfig[];

  public constructor(id: number, name: string, slug: string, description: string, widgetsLink: string) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.description = description;
    this.widgetConfigsLink = widgetsLink;
    this.widgetConfigs = [];
  }
}
