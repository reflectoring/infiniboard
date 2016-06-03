import {Injectable} from 'angular2/core';
import {DASHBOARDS} from './mock-dashboards';
import {RestService} from '../rest.service';

@Injectable()
export class DashboardService {

  private rest: RestService;

  constructor(rest: RestService) {
    this.rest = rest;
  }

  public getDashboards() {
    console.log('getting data');
    this.rest.GetAll()
      .subscribe(data => console.log(data),
        error => console.log(error),
        () => console.log('Get all Items complete'));
  }

  public getDashboard(_id: number) {
    return Promise.resolve(DASHBOARDS).then(
      dashboards => dashboards.filter(dashboard => dashboard.id === _id)[0]
    );
  }
}
