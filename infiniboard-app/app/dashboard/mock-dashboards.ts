import {Dashboard} from './dashboard';
import {WidgetConfig} from './widget-config';

export var DASHBOARDS: Dashboard[] = [
  new Dashboard(1, 'Development', 'source metrics', [new WidgetConfig('dev', 'platform-status', 20000)]),
  new Dashboard(2, 'Support', 'call center metrics', [
    new WidgetConfig('harvester', 'jenkins-job', 3000),
    new WidgetConfig('quartermaster', 'jenkins-job', 5000),
    new WidgetConfig('infiniboard', 'jenkins-job', 10000)
  ]),
];
