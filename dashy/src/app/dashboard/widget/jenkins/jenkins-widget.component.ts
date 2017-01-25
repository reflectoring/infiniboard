import {Component} from '@angular/core';
import {Widget} from '../widget';
import {WidgetService} from '../../shared/widget.service';
import {BuildStatus} from './build-status.enum';
import {JobType} from './job-type.enum';

@Component({
  selector: 'jenkins-widget',
  templateUrl: './jenkins-widget.component.html',
  styleUrls: ['./jenkins-widget.component.css']
})
export class JenkinsWidgetComponent extends Widget {

  name: string = '';
  status: BuildStatus = BuildStatus.NOT_BUILT;
  url: string = '';
  lastBuildNo: string = '';
  type: JobType = JobType.UNKNOWN;

  public constructor(private wS: WidgetService) {
    super(wS);
  }

  public updateData(data: any[]) {
    for (let sourceData of data) {
      if (sourceData.sourceId === 'jenkins') {
        let json = JSON.parse(sourceData.data.content);

        this.name = json.displayName;
        this.url = json.url;
        this.type = this.getJobType(json);
        console.log('jenkins job "' + this.name + '" with type "' + JobType[this.type] + '"');

        if (this.type === JobType.JOB) {
          this.status = this.getBuildStatus(json.color);
          console.log('jenkins job "' + this.name + '" with status "' + BuildStatus[this.status] + '"');

          if (json.lastBuild) {
            this.lastBuildNo = '#' + json.lastBuild.number;
          } else {
            this.lastBuildNo = '';
          }
        } else {
          console.error('Skipping unsupported jenkins job "' + this.name + '" with type "' + JobType[this.type] + '"');
        }

      } else {
        console.error('Could not find jenkins job data!');
      }
    }
  }

  private getJobType(json: any): JobType {
    if (json.color === undefined) {
      return JobType.UNKNOWN;
    }

    return JobType.JOB;
  }

  private  getBuildStatus(colorCode: string): BuildStatus {
    switch (colorCode) {

      case 'notbuilt':
        return BuildStatus.NOT_BUILT;

      case 'notbuilt_anime':
        return BuildStatus.NOT_BUILT_BUILDING;

      case 'disabled':
        return BuildStatus.DISABLED;

      case 'blue':
        return BuildStatus.BLUE;

      case 'blue_anime':
        return BuildStatus.BLUE_BUILDING;

      case 'yellow':
        return BuildStatus.YELLOW;

      case 'yellow_anime':
        return BuildStatus.YELLOW_BUILDING;

      case 'aborted':
        return BuildStatus.ABORTED;

      case 'red':
        return BuildStatus.RED;

      case 'red_anime':
        return BuildStatus.RED_BUILDING;


      default:
        console.error('Unknown Jenkins job status color: "' + colorCode + '"');
        return BuildStatus.UNKNOWN;
    }
  }

}
