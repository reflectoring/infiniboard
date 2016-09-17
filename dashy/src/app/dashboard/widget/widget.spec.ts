/* tslint:disable:no-unused-variable */

import {Widget} from './widget';
import {TestBed} from '@angular/core/testing';
import {WidgetService} from '../shared/widget.service';
import {WidgetConfig} from '../shared/widget-config';
import {Observable} from 'rxjs';

class FakeWidgetService {
  public fetchWidgetData(widgetConfig: WidgetConfig): Observable<any> {
    return Observable.of({});
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
