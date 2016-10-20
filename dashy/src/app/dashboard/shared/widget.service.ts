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

  public getWidgetData(widgetConfig: WidgetConfig): Observable<any> {
    return this.http.get(widgetConfig.dataLink)
      .map(WidgetService.handleWidgetData)
      .catch(this.handleError);
  }

  private static handleWidgetData(res: Response): any {
    return (res.json() || {}).sourceData;
  }

  private handleError(error: any) {
    let errMsg = ((error.body || {}).message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg);
    return Observable.throw(errMsg);
  }

  public getWidgets(dashboard: Dashboard): Observable<WidgetConfig[]> {
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
    let widgetConfig = new WidgetConfig(haljson.type, haljson.title, haljson._links.data.href);

    if (haljson.titleUrl) {
      widgetConfig.titleUrl = haljson.titleUrl;
    }

    if (haljson.description) {
      widgetConfig.description = haljson.description;
    }

    return widgetConfig;
  }
}
