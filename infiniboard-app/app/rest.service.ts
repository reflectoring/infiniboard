import {Injectable} from 'angular2/core';
import {Http, Response, Headers} from 'angular2/http';
import {Observable} from 'rxjs/Observable';
import {Dashboard} from './dashboard/dashboard';
import 'rxjs/add/operator/map';

@Injectable()
export class RestService {

  private http: Http;

  private actionUrl: string;
  private headers: Headers;

  constructor(http: Http) {

    this.http = http;
    this.actionUrl = 'http://localhost:8080/api/dashboards';

    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
    this.headers.append('Accept', 'application/json');
  }

  public GetAll = (): Observable<Response> => {
    return this.http
      .get(this.actionUrl)
      .map(res => res.json());
  };

  public GetSingle = (id: number): Observable<Response> => {
    return this.http
      .get(this.actionUrl + '/' + id)
      .map(res => res.json());
  };

  public Add = (itemName: string): Observable<Response> => {
    let toAdd = JSON.stringify({ItemName: itemName});

    return this.http
      .post(this.actionUrl, toAdd, {headers: this.headers})
      .map(res => res.json());
  };

  public Update = (id: number, itemToUpdate: Dashboard): Observable<Response> => {
    return this.http.put(this.actionUrl + id, JSON.stringify(itemToUpdate), {headers: this.headers}).map(res => res.json());
  };

  public Delete = (id: number): Observable<Response> => {
    return this.http.delete(this.actionUrl + id);
  };
}
