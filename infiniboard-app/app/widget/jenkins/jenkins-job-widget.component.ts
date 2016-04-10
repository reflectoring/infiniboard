import {Component} from 'angular2/core';
import {WidgetComponent} from '../widget-component';
import {WidgetService} from '../widget.service';

@Component({
  selector: 'jenkins-job-widget',
  templateUrl: 'app/widget/jenkins/jenkins-job-widget.component.html',
})
export class JenkinsJobWidgetComponent extends WidgetComponent {

  public name: string;
  public status: string;

  public constructor(widgetService: WidgetService) {
    super(widgetService);
  }

  public updateData(data: any) {
    this.name = data.name;
    this.status = data.status;
  }

  public isSuccessfull() {
    return this.status === 'Success';
  }

  public isUnstable() {
    return this.status === 'Unstable';
  }

  public isFailing() {
    return this.status === 'Failure';
  }


}
