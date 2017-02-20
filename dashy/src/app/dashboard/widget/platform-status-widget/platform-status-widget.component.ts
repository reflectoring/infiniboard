import {Component} from '@angular/core';
import {Widget} from '../widget';
import {Status} from './status.enum';
import {WidgetService} from '../../shared/widget.service';

@Component({
  selector: 'platform-status-widget',
  templateUrl: './platform-status-widget.component.html',
  styleUrls: ['./platform-status-widget.component.css']
})
export class PlatformStatusWidgetComponent extends Widget {

  version: string = 'N/A';
  status: Status = Status.DOWN;

  public constructor(private wS: WidgetService) {
    super(wS);
  }

  public updateData(data: any[]) {
    for (const sourceData of data) {

      if (sourceData.sourceId === 'version') {
        const versionContent = sourceData.data.content;
        this.setVersionByPlatformStatus(this.status, versionContent);
      }
      if (sourceData.sourceId === 'status') {
        this.status = this.getPlatformStatus(sourceData.data.status);
      }
    }
  }

  private getPlatformStatus(status: number): Status {

    const stringValue: string = String(status);

    // 2xx: platform available
    if (stringValue[0] === '2') {
      return Status.UP;
    }

    // 503: platform locked / in maintenance mode
    if (status === 503) {
      return Status.MAINTENANCE;
    }

    // unknown state
    return Status.UNKNOWN;
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
