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

  public updateData(data: any) {
    // TODO read data from response
    // this.version = data.version;
    // this.status = data.status;

    console.log('[PlatformStatusWidgetComponent] updateData(): interval ' + this.getUpdateInterval());
  }
}
