import {WidgetData} from './widget-data';

export var WIDGET_DATA: WidgetData[] = [
  new WidgetData('dev', {
    name: 'Development',
    version: '0.1.0',
    status: 'ok'
  }),
  new WidgetData('job1', {
    name: 'infiniboard',
    status: 'Failure',
    url: 'http://localhost/jenkins/infiniboard',
    buildUrl: 'http://localhost/jenkins/infiniboard/123'
  }),
];
