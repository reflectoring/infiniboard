import {Dashboard} from './dashboard';

describe('Dashboard', () => {
  it('should create an instance', () => {
    expect(new Dashboard(1, 'Testing Dashboard', 'Dashboard for testers', '/api/dashboards/1')).toBeTruthy();
  });

  it('constructor() should set dashboard.id', () => {
    const dashboard = new Dashboard(1, 'Testing Dashboard', 'Dashboard for testers', '/api/dashboards/1');
    expect(dashboard.id).toEqual(1);
  });

  it('constructor() should set dashboard.name', () => {
    const name = 'Testing Dashboard';
    const dashboard = new Dashboard(1, name, 'Dashboard for testers', '/api/dashboards/1');
    expect(dashboard.name).toEqual(name);
  });

  it('constructor() should set dashboard.description', () => {
    const description = 'Dashboard for testers';
    const dashboard = new Dashboard(1, 'Testing Dashboard', description, '/api/dashboards/1');
    expect(dashboard.description).toEqual(description);
  });

  it('constructor() should set dashboard.widgetConfigLink', () => {
    const link = '/api/dashboards/1';
    const dashboard = new Dashboard(1, 'Testing Dashboard', 'Dashboard for testers', link);
    expect(dashboard.widgetConfigsLink).toEqual(link);
  });

  it('constructor() should initialize dashboard.widgetConfigs', () => {
    const dashboard = new Dashboard(1, 'Testing Dashboard', 'Dashboard for testers', '/api/dashboards/1');
    expect(dashboard.widgetConfigs).toEqual([]);
  });
});
