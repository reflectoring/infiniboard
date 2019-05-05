import {Dashboard} from './dashboard';

describe('Dashboard', () => {
  it('should create an instance', () => {
    expect(new Dashboard(1, 'Testing Dashboard', 'test', 'Dashboard for testers', '/api/dashboards/test')).toBeTruthy();
  });

  it('constructor() should set dashboard.id', () => {
    const dashboard = new Dashboard(1, 'Testing Dashboard', 'test','Dashboard for testers', '/api/dashboards/test');
    expect(dashboard.id).toEqual(1);
  });

  it('constructor() should set dashboard.name', () => {
    const name = 'Testing Dashboard';
    const dashboard = new Dashboard(1, name, 'test','Dashboard for testers', '/api/dashboards/test');
    expect(dashboard.name).toEqual(name);
  });

  it('constructor() should set dashboard.description', () => {
    const description = 'Dashboard for testers';
    const dashboard = new Dashboard(1, 'Testing Dashboard', 'test', description, '/api/dashboards/test');
    expect(dashboard.description).toEqual(description);
  });

  it('constructor() should set dashboard.widgetConfigLink', () => {
    const link = '/api/dashboards/test';
    const dashboard = new Dashboard(1, 'Testing Dashboard', 'test', 'Dashboard for testers', link);
    expect(dashboard.widgetConfigsLink).toEqual(link);
  });

  it('constructor() should initialize dashboard.widgetConfigs', () => {
    const dashboard = new Dashboard(1, 'Testing Dashboard', 'test','Dashboard for testers', '/api/dashboards/test');
    expect(dashboard.widgetConfigs).toEqual([]);
  });
});
