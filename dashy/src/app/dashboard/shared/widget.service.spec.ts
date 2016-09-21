import {inject, TestBed} from '@angular/core/testing';
import {WidgetService} from './widget.service';
import {Response, ResponseOptions, ResponseType, Http} from '@angular/http';
import {Dashboard} from './dashboard';
import {Observable} from 'rxjs';
import {WidgetConfig} from './widget-config';


class FakeWidgetHttp {

  public get(url: string): Observable<Response> {

    console.log(url);

    switch (url) {
      case '/mock/widgets':
        return this.getWidgets();
      case '/mock/widgets/error':
        return this.getWidgetsWithError();

      case '/mock/data':
        return this.getWidgetData();
      case '/mock/data/error':
        return this.getWidgetDataWithError();

      default:
        throw new Error('unmocked url: ' + url);
    }
  }

  private getWidgets() {
    let body = {
      '_embedded': {
        'widgetConfigResourceList': [{
          'title': 'dev',
          'type': 'platform-status',
          'lastModified': 1474140249062,
          'sourceConfigs': [{
            'id': 'version',
            'type': 'urlSource',
            'interval': 10000,
            'configData': {'url': 'https://putsreq.com/eT3fqDqHnnNKureYOuOf'}
          }, {
            'id': 'status',
            'type': 'urlSource',
            'interval': 10000,
            'configData': {'url': 'https://putsreq.com/eT3fqDqHnnNKureYOuOf'}
          }],
          '_links': {
            'self': {'href': '/mock/api/dashboards/1/widgets/57dd98597e21e57c76718be6'},
            'dashboard': {'href': '/mock/api/dashboards/1'},
            'data': {'href': '/mock/api/dashboards/1/widgets/57dd98597e21e57c76718be6/data'}
          }
        }, {
          'title': 'test',
          'type': 'platform-status',
          'lastModified': 1474140249140,
          'sourceConfigs': [{
            'id': 'version',
            'type': 'urlSource',
            'interval': 10000,
            'configData': {'url': 'https://putsreq.com/uh7LA812il8FBDXBjTaB'}
          }, {
            'id': 'status',
            'type': 'urlSource',
            'interval': 10000,
            'configData': {'url': 'https://putsreq.com/uh7LA812il8FBDXBjTaB'}
          }],
          '_links': {
            'self': {'href': '/mock/api/dashboards/1/widgets/57dd98597e21e57c76718be7'},
            'dashboard': {'href': '/mock/api/dashboards/1'},
            'data': {'href': '/mock/api/dashboards/1/widgets/57dd98597e21e57c76718be7/data'}
          }
        }]
      },
      '_links': {
        'first': {'href': '/mock/api/dashboards/1/widgets?page=0&size=10'},
        'self': {'href': '/mock/api/dashboards/1/widgets'},
        'last': {'href': '/mock/api/dashboards/1/widgets?page=0&size=10'}
      },
      'page': {'size': 10, 'totalElements': 2, 'totalPages': 1, 'number': 0}
    };
    return this.createFakeResponse('/mock/widgets', body);
  }

  private createFakeResponse(url: string, body: any, status = 200): Observable<Response> {
    let responseOptionsArgs = {
      body: body,
      status: status,
      statusText: 'OK',
      url: url,
      type: ResponseType.Basic
    };

    return Observable.of(new Response(new ResponseOptions(responseOptionsArgs)));
  }

  public getWidgetsWithError(): Observable<Response> {
    return this.createFakeResponse('/mock/widgets/error', {}, 500);
  }

  public getWidgetData(): Observable<Response> {
    let body: any = {
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
      }], '_links': {'widget': {'href': '/mock/api/dashboards/1/widgets/57dd98a27e21e57c76718bed'}}
    };
    return this.createFakeResponse('/mock/widgets/data', body);
  }

  public getWidgetDataWithError(): Observable<Response> {
    return this.createFakeResponse('/mock/data/error', {}, 500);
  }
}

let dashboard = new Dashboard(3, 'Testing', 'test reports', '/mock/widgets');

describe('Service: Widget', () => {
  beforeEach(() => {
      TestBed.configureTestingModule({
        providers: [
          {provide: Http, useClass: FakeWidgetHttp},
          WidgetService
        ]
      });
    }
  );

  it('should create an instance', inject([WidgetService], (service: WidgetService) => {
    expect(service).toBeTruthy();
  }));

  it('getWidgets() returns a list of widget configs', inject([WidgetService], (service: WidgetService) => {
    let widgetConfigs = service.getWidgets(dashboard);
    expect(widgetConfigs).toBeTruthy();
  }));

  it('getWidgets() returns a list of two widget configs', inject([WidgetService], (service: WidgetService) => {
    service.getWidgets(dashboard).subscribe(widgetConfigs => {
      expect(widgetConfigs.length).toEqual(2);
    });
  }));

  it('getWidgets()[0] returns the first widget config', inject([WidgetService], (service: WidgetService) => {
    service.getWidgets(dashboard).subscribe(widgetConfigs => {
      expect(widgetConfigs[0].title).toEqual('dev');
      expect(widgetConfigs[0].type).toEqual('platform-status');
      expect(widgetConfigs[0].dataLink).toEqual('/mock/api/dashboards/1/widgets/57dd98597e21e57c76718be6/data');
    });
  }));

  it('getWidgetData() returns an untyped object containing two widget data objects',
    inject([WidgetService], (service: WidgetService) => {

      let widgetConfig = new WidgetConfig('platform-status', 'dev', '/mock/data');
      service.getWidgetData(widgetConfig).subscribe(data => {
        expect(data.length).toEqual(2);
      });
    }));


  it('getWidgetData() returns an untyped object containing widget display data',
    inject([WidgetService], (service: WidgetService) => {

      let widgetConfig = new WidgetConfig('platform-status', 'dev', '/mock/data');
      service.getWidgetData(widgetConfig).subscribe(data => {
        expect(data[0].sourceId).toEqual('status');
        expect(data[1].sourceId).toEqual('version');
      });
    }));
});

