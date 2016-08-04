import {Injectable} from 'angular2/core';
import {Http, Headers, Response} from 'angular2/http';
import {Observable} from 'rxjs/Observable';
import {Dashboard} from './dashboard';
import '../rxjs-operators';

@Injectable()
export class DashboardService {

  private actionUrl: string;
  private http: Http;
  private headers: Headers;

  private static extractDashboardList(haljson: any): Dashboard[] {

    let result: any[] = [];
    let dashboards = (haljson._embedded || {}).dashboardResourceList || {};

    for (let item of dashboards) {
      let dashboard = DashboardService.createDashboard(item);
      result.push(dashboard);
    }
    return result;
  }

  private static createDashboard(haljson: any): Dashboard {
    return new Dashboard(haljson.number, haljson.name, haljson.description, haljson._links.widgets.href);
  }


  private static handleDashboard(res: Response): Dashboard {
    let haljson = res.json();
    return DashboardService.createDashboard(haljson);
  }

  private static handleDashboardList(res: Response): Dashboard[] {
    let haljson = res.json();
    return DashboardService.extractDashboardList(haljson);
  }

  constructor(http: Http) {
    this.http = http;
    this.actionUrl = '/api/dashboards';

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
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Observable.throw(errMsg);
  }

}
