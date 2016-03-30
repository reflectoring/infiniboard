import {Injectable} from 'angular2/core';
import {JOBS} from './mock-jobs';

@Injectable()
export class JenkinsService {

  public getJobs() {
    return Promise.resolve(JOBS);
  }

  public getJob(name: string) {
    return Promise.resolve(JOBS).then(
      jobs => jobs.filter(job => job.name === name)[0]
    );
  }
}
