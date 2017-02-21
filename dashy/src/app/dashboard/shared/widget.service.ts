import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {WidgetConfig} from './widget-config';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
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

  public getWidgetData(widgetConfig: WidgetConfig): Observable<any> {
    return this.http.get(widgetConfig.dataLink)
      .map(WidgetService.handleWidgetData)
      .catch(this.handleError);
  }

  private static handleWidgetData(res: Response): any {
    return (res.json() || {}).sourceData;
  }

  private handleError(error: any) {
    const errMsg = ((error.body || {}).message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg);
    console.error(error);
    return Observable.throw(errMsg);
  }

  public getWidgets(dashboard: Dashboard): Observable<WidgetConfig[]> {
    return this.http.get(dashboard.widgetConfigsLink)
      .map(WidgetService.extractWidgetConfigList)
      .catch(this.handleError);
  }

  private static extractWidgetConfigList(res: Response): WidgetConfig[] {
    const haljson: any = res.json();
    const widgetConfigs: any = ((haljson._embedded || {}).widgetConfigResourceList || {});
    const result: any[] = [];

    for (const item of widgetConfigs) {
      const widgetConfig = WidgetService.createWidgetConfig(item);
      result.push(widgetConfig);
    }

    return result;
  }

  private static createWidgetConfig(haljson: any): WidgetConfig {
    const widgetConfig = new WidgetConfig(haljson.type, haljson.title, haljson._links.data.href);

    if (haljson.titleUrl) {
      widgetConfig.titleUrl = haljson.titleUrl;
    }

    if (haljson.description) {
      widgetConfig.description = haljson.description;
    }

    return widgetConfig;
  }
}
