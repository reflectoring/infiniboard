import {TestBed, inject} from '@angular/core/testing';
import {JenkinsWidgetComponent} from './jenkins-widget.component';
import {WidgetService} from '../../shared/widget.service';
import {WidgetConfig} from '../../shared/widget-config';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';

class FakeWidgetService {

  public getWidgetData(widgetConfig: WidgetConfig): Observable<any> {
    return Observable.of([
      {
        sourceId: 'jenkins',
        content: {
          displayName: 'test job',
          url: 'http://example.com',
          healthReport: [
            {
              score: 100
            }
          ]
        }
      }]);
  }
}

describe('Component: Jenkins', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{provide: WidgetService, useClass: FakeWidgetService}]
    });
  });

  it('should create an instance', inject([WidgetService], (service: WidgetService) => {
    const component = new JenkinsWidgetComponent(service);
    expect(component).toBeTruthy();
  }));
});
