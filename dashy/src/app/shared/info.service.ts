import {Injectable} from '@angular/core';
import {Headers, Http, Response} from "@angular/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Info} from "./info";

@Injectable()
export class InfoService {

  private actionUrl: string;
  private headers: Headers;

  private static createInfo(haljson: any): Info {
    return new Info(haljson.version);
  }

  private static handleInfos(res: Response): Info {
    let haljson = res.json();
    return InfoService.createInfo(haljson);
  }

  constructor(private http: Http) {
    this.actionUrl = environment.baseUrl + 'api/info';

    this.headers = new Headers();
    this.headers.append('Content-Type', 'application/json');
    this.headers.append('Accept', 'application/json');
  }

  public getServerInfo(): Observable<Info> {
    return this.http.get(this.actionUrl)
      .map(InfoService.handleInfos)
      .catch(this.handleError);
  }

  private handleError(error: any) {
    let errMsg = ((error.body || {}).message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg);
    console.error(error);
    return Observable.throw(errMsg);
  }

}
