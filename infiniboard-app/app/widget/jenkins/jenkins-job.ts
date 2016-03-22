export class JenkinsJob {
  name:string;
  result:string;

  constructor(name:string, result:string) {
    this.name = name;
    this.result = result;
  }

  isSuccessfull() {
    return this.result == "Success"
  }

  isUnstable() {
    return this.result == "Unstable"
  }

  isFailing() {
    return this.result == "Failure"
  }
}
