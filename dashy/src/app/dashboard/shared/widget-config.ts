export class WidgetConfig {
  public type: string;
  public title: string;
  public titleUrl: string;
  public description: string;

  public dataLink: string;

  public constructor(type: string, title: string, dataLink: string, titleUrl: string, description: string) {
    this.type = type;
    this.title = title;
    this.titleUrl = titleUrl;
    this.description = description;

    this.dataLink = dataLink;
  }
}
