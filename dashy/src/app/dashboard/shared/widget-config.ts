export class WidgetConfig {
  type: string;
  title: string;
  titleUrl: string;
  description: string;
  dataLink: string;

  public constructor(type: string, title: string, dataLink: string) {
    this.type = type;
    this.title = title;
    this.dataLink = dataLink;
  }
}
