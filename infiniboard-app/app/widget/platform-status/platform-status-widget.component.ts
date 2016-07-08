import {Component} from 'angular2/core';
import {WidgetComponent} from '../widget-component';
import {WidgetService} from '../widget.service';

@Component({
  selector: 'platform-status-widget',
  templateUrl: 'app/widget/platform-status/platform-status-widget.component.html'
})
export class PlatformStatusWidgetComponent extends WidgetComponent {

  version: string = 'N/A';
  status: string;

  public constructor(widgetService: WidgetService) {
    super(widgetService);
  }

  public updateData(data: any[]) {
    for (let sourceData of data) {
      if (sourceData.sourceId === 'version') {
        this.version = sourceData.data.content;
      }
      if (sourceData.sourceId === 'status') {
        this.status = this.transformHttpStatusCode(sourceData.data.status);
      }
    }
  }

  private transformHttpStatusCode(status: number) {
    if (status === 200) {
      return 'green';
    }
    return 'yellow';
  }
}
