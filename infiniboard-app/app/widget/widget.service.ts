import {Injectable} from 'angular2/core';
import {Headers, Http, Response} from 'angular2/http';
import {Observable} from 'rxjs/Observable';
import '../rxjs-operators';

@Injectable()
export class WidgetService {

  private actionUrl: string;
  private http: Http;
  private headers: Headers;

  constructor(http: Http) {
    this.http = http;
    this.actionUrl = 'http://localhost:8080/api/widgets';

    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
    this.headers.append('Accept', 'application/json');
  }

  private static handleWidgetData(res: Response): any {
    return res.json();
  }

  public getWidgetData(id: string): Observable<any> {
    return this.http.get(this.actionUrl + '/' + id + '/data')
      .map(WidgetService.handleWidgetData)
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
