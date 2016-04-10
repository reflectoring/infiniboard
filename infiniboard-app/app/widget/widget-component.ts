import {WidgetService} from './widget.service';

export class WidgetComponent {

  private id: string;
  private widgetService: WidgetService;

  public constructor(widgetService: WidgetService) {
    this.widgetService = widgetService;
  }

  public setId(id: string) {
    this.id = id;
  }

  public getId() {
    return this.id;
  }

  public updateWidgetData() {
    let widgetUpdate = this.widgetService.getWidgetData(this.getId());
    Promise.resolve(widgetUpdate).then(
      update => this.updateData(update.data)
    );
  }

  public updateData(data: any) {
    // implemented by widgets
  }
}
