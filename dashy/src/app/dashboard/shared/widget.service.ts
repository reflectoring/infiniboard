import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {WidgetConfig} from './widget-config';
import {Observable} from 'rxjs';
import {Dashboard} from './dashboard';

@Injectable()
export class WidgetService {

  private headers: Headers;

  constructor(private http: Http) {
    this.http = http;

    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
    this.headers.append('Accept', 'application/json');
  }

  public fetchWidgetData(widgetConfig: WidgetConfig): Observable<any> {
    return this.http.get(widgetConfig.dataLink)
      .map(WidgetService.handleWidgetData)
      .catch(this.handleError);
  }

  private static handleWidgetData(res: Response): any {
    return (res.json() || {}).sourceData;
  }

  private handleError(error: any) {
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Observable.throw(errMsg);
  }

  public fetchWidgets(dashboard: Dashboard): Observable<WidgetConfig[]> {
    return this.http.get(dashboard.widgetConfigsLink)
      .map(WidgetService.extractWidgetConfigList)
      .catch(this.handleError);
  }

  private static extractWidgetConfigList(res: Response): WidgetConfig[] {
    let haljson: any = res.json();
    let widgetConfigs: any = ((haljson._embedded || {}).widgetConfigResourceList || {});
    let result: any[] = [];

    for (let item of widgetConfigs) {
      let widgetConfig = WidgetService.createWidgetConfig(item);
      result.push(widgetConfig);
    }

    return result;
  }

  private static createWidgetConfig(haljson: any): WidgetConfig {
    return new WidgetConfig('platform-status', haljson.title, haljson._links.data.href);
  }
}
