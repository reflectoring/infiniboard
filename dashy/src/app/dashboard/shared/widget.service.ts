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
    console.error(error);
    return Observable.throw(errMsg);
  }

  public getWidgets(dashboard: Dashboard): Observable<WidgetConfig[]> {
    return this.getWidgetsFromUrl(dashboard.widgetConfigsLink)
  }

  private getWidgetsFromUrl(url: string): Observable<WidgetConfig[]> {
    console.log('getting page: ' + url);
    return this.http.get(url)
      .map(res => {
        let results = this.extractWidgetConfigList(this, res);

        console.log(results);

        if (((res.json()._links || {}).next || {}).href || {}) {
          // more pages to come
          console.log('found next page: ' + res.json()._links.next.href)
          let nextPageResults = this.getWidgetsFromUrl(res.json()._links.next.href);
          nextPageResults.forEach(next => {
            console.log("results of next page");
            console.log(next);
            results.concat(next);});
        }

        console.log("all results");
        console.log(results);

        return results;

      })
      .catch(this.handleError);
  }

  private extractWidgetConfigList(context: any, res: Response): WidgetConfig[] {
    let haljson: any = res.json();
    let widgetConfigs: any = ((haljson._embedded || {}).widgetConfigResourceList || {});
    let result: any[] = [];

    for (let item of widgetConfigs) {
      let widgetConfig = WidgetService.createWidgetConfig(item);
      console.log(widgetConfig);
      result.push(widgetConfig);
    }

    return result;
  }

  private static createWidgetConfig(haljson: any): WidgetConfig {
    return new WidgetConfig(haljson.type, haljson.title, haljson._links.data.href);
  }
}
