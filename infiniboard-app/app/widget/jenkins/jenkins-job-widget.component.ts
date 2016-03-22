import {Component, OnInit} from "angular2/core";
import {JenkinsService} from "./jenkins.service";
import {JenkinsJob} from "./jenkins-job";

@Component({
  selector: "jenkins-job-widget",
  templateUrl: "app/widget/jenkins/jenkins-job-widget.component.html",
})

export class JenkinsJobWidgetComponent implements OnInit {

  job:JenkinsJob;

  constructor(private _jenkinsService:JenkinsService) {

  }

  ngOnInit() {
    this._jenkinsService.getJob("Infiniboard").then(
      job => this.job = job
    );
  }

}
