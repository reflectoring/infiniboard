import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Dashboard} from './dashboard';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {environment} from '../../../environments/environment';

@Injectable()
export class DashboardService {

  private actionUrl: string;
  private headers: Headers;

  private static extractDashboardList(haljson: any): Dashboard[] {

    const result: any[] = [];
    const dashboards = (haljson._embedded || {}).dashboardResourceList || {};

    for (const item of dashboards) {
      const dashboard = DashboardService.createDashboard(item);
      result.push(dashboard);
    }
    return result;
  }

  private static createDashboard(haljson: any): Dashboard {
    return new Dashboard(haljson.number, haljson.name, haljson.description, haljson._links['all-widgets'].href);
  }

  private static handleDashboard(res: Response): Dashboard {
    const haljson = res.json();
    return DashboardService.createDashboard(haljson);
  }

  private static handleDashboardList(res: Response): Dashboard[] {
    const haljson = res.json();
    return DashboardService.extractDashboardList(haljson);
  }

  constructor(private http: Http) {
    this.actionUrl = environment.baseUrl + 'api/dashboards';

    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
    this.headers.append('Accept', 'application/json');
  }

  public getDashboard(id: number): Observable<Dashboard> {
    return this.http.get(this.actionUrl + '/' + id)
      .map(DashboardService.handleDashboard)
      .catch(this.handleError);
  }

  public getDashboards(): Observable<Dashboard[]> {
    return this.http.get(this.actionUrl)
      .map(DashboardService.handleDashboardList)
      .catch(this.handleError);
  }

  private handleError(error: any) {
    const errMsg = ((error.body || {}).message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg);
    console.error(error);
    return Observable.throw(errMsg);
  }
}
