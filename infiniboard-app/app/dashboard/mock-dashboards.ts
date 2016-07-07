import {Dashboard} from './dashboard';
import {WidgetConfig} from './widget-config';

export var DASHBOARDS: Dashboard[] = [
  new Dashboard(1, 'Development', 'source metrics', [new WidgetConfig('4', 'platform-status', 'dev')]),
  new Dashboard(2, 'Support', 'call center metrics', [
    new WidgetConfig('1', 'jenkins-job', 'infiniboard'),
    new WidgetConfig('2', 'jenkins-job', 'infiniboard'),
    new WidgetConfig('3', 'jenkins-job', 'infiniboard')
  ]),
];
