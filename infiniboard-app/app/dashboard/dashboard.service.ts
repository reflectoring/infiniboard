import {Injectable} from 'angular2/core';
import {Http, Headers} from 'angular2/http';
import {Observable} from 'rxjs/Observable';
import {Dashboard} from './dashboard';
import '../rxjs-operators';

@Injectable()
export class DashboardService {

  private actionUrl: string;
  private http: Http;
  private headers: Headers;

  constructor(http: Http) {
    this.http = http;
    this.actionUrl = 'http://localhost:8080/api/dashboards';

    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
    this.headers.append('Accept', 'application/json');
  }

  private static extractDashboardList(haljson: any): Dashboard[] {

    let result: any[] = [];
    for (let item of haljson) {
      let dashboard = DashboardService.createDashboard(item);
      result.push(dashboard);
    }
    return result;
  }

  private static createDashboard(haljson: any): Dashboard {
    return new Dashboard(haljson.id, haljson.name, haljson.description, []);
  }

  public getDashboard(id: number): Observable<Dashboard> {
    return this.http.get(this.actionUrl + '/' + id)
      .map(res => DashboardService.createDashboard(res.json() || {}))
      .catch(this.handleError);
  }

  public getDashboards(): Observable<Dashboard[]> {
    return this.http.get(this.actionUrl)
      .map(res => DashboardService.extractDashboardList(res.json() || {}))
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
