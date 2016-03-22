import {JenkinsJob} from "./jenkins-job";

export var JOBS:JenkinsJob[] = [
  new JenkinsJob("Harevster", "Success"),
  new JenkinsJob("Infiniboard", "Unstable"),
  new JenkinsJob("Quartermaster", "Failure")
];

