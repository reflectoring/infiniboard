import {Component} from "angular2/core";
import {JenkinsJobWidgetComponent} from "./widget/jenkins/jenkins-job-widget.component";
import {JenkinsService} from "./widget/jenkins/jenkins.service";

@Component({
  selector: "infiniboard",
  directives: [JenkinsJobWidgetComponent],
  styleUrls: ["app/app.component.css"],
  templateUrl: "app/app.component.html",
  providers: [
    JenkinsService
  ]
})

export class AppComponent {
}
