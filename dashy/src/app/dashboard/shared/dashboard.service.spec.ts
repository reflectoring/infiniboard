import {TestBed, inject} from '@angular/core/testing';
import {DashboardService} from './dashboard.service';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {Dashboard} from './dashboard';


class FakeDashboardHttp {

  public getDashboards(): Observable<Dashboard[]> {
    return Observable.of([
      new Dashboard(1, 'Development', 'development', 'dev stuff', '/mock/widgets/1'),
      new Dashboard(3, 'Testing', 'test','test reports', '/mock/widgets/2')]);
  }

}

describe('Service: Dashboard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DashboardService, {provide: Http, useValue: FakeDashboardHttp}]
    });
  });

  it('should create an instance', inject([DashboardService], (service: DashboardService) => {
    expect(service).toBeTruthy();

    it('and getDashboards() returns a list of dashboards', () => {
      const dashboards = service.getDashboards();
      expect(dashboards).toBeTruthy();
    });

    it('and getDashboards() returns a list of two dashboards', () => {
      service.getDashboards().subscribe(dashboards => {
        expect(dashboards.length).toEqual(2);
      });

    });
  }));
});
