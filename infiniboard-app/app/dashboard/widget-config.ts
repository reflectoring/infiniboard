export class WidgetConfig {
  public id: string;
  public type: string;
  public title: string;

  public constructor(id: string, type: string, title: string) {
    this.id = id;
    this.type = type;
    this.title = title;
  }
}
