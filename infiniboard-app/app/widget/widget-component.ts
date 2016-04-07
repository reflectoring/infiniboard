export class WidgetComponent {
  private id: string;

  public setId(id: string) {
    this.id = id;
  }

  public getId(id: string) {
    return this.id;
  }
}
