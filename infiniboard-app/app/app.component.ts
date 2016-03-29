import {Component} from "angular2/core";
import {JenkinsJobWidgetComponent} from "./widget/jenkins/jenkins-job-widget.component";
import {JenkinsService} from "./widget/jenkins/jenkins.service";

@Component({

  directives: [JenkinsJobWidgetComponent],
  providers: [
    JenkinsService,
  ],
  selector: "infiniboard",
  styleUrls: ["app/app.component.css"],
  templateUrl: "app/app.component.html",
})

export class AppComponent {
}
