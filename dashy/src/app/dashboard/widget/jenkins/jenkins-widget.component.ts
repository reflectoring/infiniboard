import {Component} from '@angular/core';
import {Widget} from '../widget';
import {WidgetService} from '../../shared/widget.service';
import {Color} from './color.enum';
import {JobType} from './job-type.enum';

@Component({
  selector: 'jenkins-widget',
  templateUrl: './jenkins-widget.component.html',
  styleUrls: ['./jenkins-widget.component.css']
})
export class JenkinsWidgetComponent extends Widget {

  name: string = 'N/A';
  status: string = '';
  color: Color = Color.BLUE;
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
        this.type = this.getJobType(json._class);


        if (this.type == JobType.WORKFLOW) {
          this.color = this.getStatusColor(json.color || {});


          if (json.lastBuild) {
            this.lastBuildNo = '#' + json.lastBuild.number;
          } else {
            this.lastBuildNo = '';
          }
        }


        this.status = json.healthReport[0].score;

      } else {
        console.error('Could not find jenkins data!');
      }
    }
  }

  private getJobType(jobCode: string): JobType {
    switch (jobCode) {

      case 'org.jenkinsci.plugins.workflow.job.WorkflowJob' :
        return JobType.WORKFLOW;

      case 'org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject':
        return JobType.WORKFLOW_MULTIBRANCH;

      case 'jenkins.branch.OrganizationFolder':
        return JobType.ORGANISATIONAL_FOLDER;

      case 'com.cloudbees.hudson.plugins.folder.Folder':
        return JobType.CLOUDBEES_FOLDER;

      default:
        return JobType.UNKNOWN;
    }
  }

  private  getStatusColor(colorCode: string): Color {
    switch (colorCode) {
      case 'Red':
        return Color.RED;
      case 'Blue':
        return Color.BLUE;

      default:
        return Color.UNKNOWN;
    }
  }

}
