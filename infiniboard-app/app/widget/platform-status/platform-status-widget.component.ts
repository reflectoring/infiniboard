import {Component} from 'angular2/core';
import {WidgetComponent} from '../widget-component';
import {WidgetService} from '../widget.service';
import {StatusColorPipe} from '../status-color.pipe';
import {StatusIconPipe} from '../status-icon.pipe';
import {StatusService} from './status.service';
import {Status} from './status';

@Component({
  pipes: [StatusColorPipe, StatusIconPipe],
  selector: 'platform-status-widget',
  templateUrl: 'app/widget/platform-status/platform-status-widget.component.html'
})
export class PlatformStatusWidgetComponent extends WidgetComponent {
  version: string = 'N/A';
  status: Status = Status.DOWN;

  private statusService: StatusService;

  public constructor(widgetService: WidgetService, statusService: StatusService) {
    super(widgetService);
    PlatformStatusWidgetComponent.WIDGET_TYPE = 'platform-status';
    this.statusService = statusService;
  }

  public updateData(data: any[]) {
    for (let sourceData of data) {

      if (sourceData.sourceId === 'version') {
        let versionContent = sourceData.data.content;
        this.setVersionByPlatformStatus(this.status, versionContent);

      }
      if (sourceData.sourceId === 'status') {
        this.status = this.statusService.getPlatformStatus(sourceData.data.status);
      }
    }
  }

  private setVersionByPlatformStatus(platformStatus: Status, versionContent: string) {
    switch (platformStatus) {
      case Status.UP:
      case Status.MAINTENANCE:
        this.version = this.trucateVersion(versionContent);
        break;

      case Status.UNKNOWN:
      case Status.DOWN:
        this.version = 'N/A';
    }
  }

  private trucateVersion(versionContent: string) {
    if (versionContent.length > 26) {
      return versionContent.substr(0, 26) + '...';
    }
    return versionContent;
  }
}
