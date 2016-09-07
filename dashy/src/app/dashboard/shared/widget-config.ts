export class WidgetConfig {
  public type: string;
  public title: string;

  public dataLink: string;

  public constructor(type: string, title: string, dataLink: string) {
    this.type = type;
    this.title = title;
    this.dataLink = dataLink;
  }
}
