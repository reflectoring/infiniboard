import {WidgetData} from './widget-data';

export var WIDGET_DATA: WidgetData[] = [
  new WidgetData('dev', {
    name: 'Development',
    version: '0.1.0',
    status: 'ok'
  }),
  new WidgetData('harvester', {
    name: 'harvester',
    duration: '1:23 min',
    status: 'Failure',
    url: 'http://localhost/jenkins/harvester',
    buildUrl: 'http://localhost/jenkins/harvester/123'
  }),
  new WidgetData('quartermaster', {
    name: 'quartermaster',
    duration: '4:15 min',
    status: 'Unstable',
    url: 'http://localhost/jenkins/quartermaster',
    buildUrl: 'http://localhost/jenkins/quartermaster/123'
  }),
  new WidgetData('infiniboard', {
    name: 'infiniboard',
    duration: '2:21 min',
    status: 'Success',
    url: 'http://localhost/jenkins/infiniboard',
    buildUrl: 'http://localhost/jenkins/infiniboard/123'
  }),
];
