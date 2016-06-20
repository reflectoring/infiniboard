import {Injectable} from 'angular2/core';
import {DASHBOARDS} from './mock-dashboards';

@Injectable()
export class DashboardService {

  public getDashboards() {
    return Promise.resolve(DASHBOARDS);
  }

  public getDashboard(_id: number) {
    return Promise.resolve(DASHBOARDS).then(
      dashboards => dashboards.filter(dashboard => dashboard.id === _id)[0]
    );
  }
}
