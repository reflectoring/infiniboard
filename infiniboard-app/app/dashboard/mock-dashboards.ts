import {Dashboard} from './dashboard';
import {WidgetConfig} from './widget-config';

export var DASHBOARDS: Dashboard[] = [
  new Dashboard(1, 'Development', 'source metrics', false, [new WidgetConfig('dev', 'platform-status')]),
  new Dashboard(2, 'Support', 'call center metrics', false, [
    new WidgetConfig('harvester', 'jenkins-job'),
    new WidgetConfig('quartermaster', 'jenkins-job'),
    new WidgetConfig('infiniboard', 'jenkins-job')
  ]),
];
