export class JenkinsJob {
  public name: string;
  public result: string;

  constructor(name: string, result: string) {
    this.name = name;
    this.result = result;
  }

  public isSuccessfull() {
    return this.result === "Success";
  }

  public isUnstable() {
    return this.result === "Unstable";
  }

  public isFailing() {
    return this.result === "Failure";
  }
}
