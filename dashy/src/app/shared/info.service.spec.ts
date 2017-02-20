import {TestBed, inject} from '@angular/core/testing';
import {InfoService} from './info.service';
import {Info} from './info';
import {Observable} from 'rxjs/Observable';
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

  it('should create an instance', inject([InfoService], (service: InfoService) => {
    expect(service).toBeTruthy();

    it('and getDashboards() returns a list of dashboards', () => {
      service.getServerInfo().subscribe(info => {
        expect(info.version).toBe('1.2.3.123');
      });
    });
  }));
});
