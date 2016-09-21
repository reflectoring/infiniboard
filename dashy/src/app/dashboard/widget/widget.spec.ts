import {Widget} from './widget';
import {TestBed} from '@angular/core/testing';
import {WidgetService} from '../shared/widget.service';
import {WidgetConfig} from '../shared/widget-config';
import {Observable} from 'rxjs';

class FakeWidgetService {
  public getWidgetData(widgetConfig: WidgetConfig): Observable<any> {
    return Observable.of({
      'sourceData': [{
        'id': '57dd991e6690b0f6fdc47cb3',
        'widgetId': '57dd98a27e21e57c76718bed',
        'sourceId': 'status',
        'data': {'content': 'operable', 'status': 200}
      }, {
        'id': '57dd99216690b0f6fdc47cbf',
        'widgetId': '57dd98a27e21e57c76718bed',
        'sourceId': 'version',
        'data': {'content': '1.0.3', 'status': 200}
      }], '_links': {'widget': {'href': 'http://localhost:8080/api/dashboards/1/widgets/57dd98a27e21e57c76718bed'}}
    });
  }
}

describe('Widget', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      // declarations: [Widget],
      providers: [{provide: WidgetService, useValue: FakeWidgetService}]
    });
  });

  it('should create an instance', () => {
    let widgetService = TestBed.get(WidgetService);
    expect(new Widget(widgetService)).toBeTruthy();
  });
});
