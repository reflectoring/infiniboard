export class WidgetConfig {
  public id: string;
  public type: string;
  public interval: number;

  public constructor(id: string, type: string, interval: number) {
    this.id = id;
    this.type = type;
    this.interval = interval;
  }
}
