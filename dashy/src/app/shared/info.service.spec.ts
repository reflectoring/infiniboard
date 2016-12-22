import {TestBed, inject} from '@angular/core/testing';
import {InfoService} from './info.service';
import {Info} from './info';
import {Observable} from 'rxjs';
import {Http} from '@angular/http';


class FakeInfoHttp {

  public getDashboards(): Observable<Info> {
    return Observable.of(new Info('1.2.3.123'));
  }

}

describe('Service: Info', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [InfoService, {provide: Http, useValue: FakeInfoHttp}]
    });
  });

  it('should ...', inject([InfoService], (service: InfoService) => {
    expect(service).toBeTruthy();
  }));
});
