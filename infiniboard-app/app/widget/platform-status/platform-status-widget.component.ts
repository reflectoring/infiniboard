import {Component} from 'angular2/core';
import {WidgetComponent} from '../widget-component';
import {WidgetService} from '../widget.service';

@Component({
  selector: 'platform-status-widget',
  templateUrl: 'app/widget/platform-status/platform-status-widget.component.html'
})
export class PlatformStatusWidgetComponent extends WidgetComponent {

  name: string;
  version: string;
  status: string;

  public constructor(widgetService: WidgetService) {
    super(widgetService);
  }

  public updateData(data: any) {
    this.name = data.name;
    this.version = data.version;
    this.status = data.status;
  }
}
