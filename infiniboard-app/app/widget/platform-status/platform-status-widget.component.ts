import {Component} from 'angular2/core';
import {WidgetComponent} from '../widget-component';
import {WidgetService} from '../widget.service';
import {StatusColorPipe} from '../status-color.pipe';
import {StatusIconPipe} from '../status-icon.pipe';

@Component({
  pipes: [StatusColorPipe, StatusIconPipe],
  selector: 'platform-status-widget',
  templateUrl: 'app/widget/platform-status/platform-status-widget.component.html'
})
export class PlatformStatusWidgetComponent extends WidgetComponent {

  version: string = 'N/A';
  status: number;

  public constructor(widgetService: WidgetService) {
    super(widgetService);
  }

  public updateData(data: any[]) {
    for (let sourceData of data) {
      if (sourceData.sourceId === 'version') {
        this.version = sourceData.data.content;
      }
      if (sourceData.sourceId === 'status') {
        this.status = sourceData.data.status;
      }
    }
  }
}
