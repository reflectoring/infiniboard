import {Dashboard} from './dashboard';

describe('Dashboard', () => {
  it('should create an instance', () => {
    expect(new Dashboard(1, 'Testing Dashboard', 'Dashboard for testers', '/api/dashboards/1')).toBeTruthy();
  });

  it('constructor() should set dashboard.id', () => {
    let dashboard = new Dashboard(1, 'Testing Dashboard', 'Dashboard for testers', '/api/dashboards/1');
    expect(dashboard.id).toBe(1);
  });

  it('constructor() should set dashboard.name', () => {
    let name = 'Testing Dashboard';
    let dashboard = new Dashboard(1, name, 'Dashboard for testers', '/api/dashboards/1');
    expect(dashboard.name).toBe(name);
  });

  it('constructor() should set dashboard.description', () => {
    let description = 'Dashboard for testers';
    let dashboard = new Dashboard(1, 'Testing Dashboard', description, '/api/dashboards/1');
    expect(dashboard.description).toBe(description);
  });

  it('constructor() should set dashboard.widgetConfigLink', () => {
    let link = '/api/dashboards/1';
    let dashboard = new Dashboard(1, 'Testing Dashboard', 'Dashboard for testers', link);
    expect(dashboard.widgetConfigsLink).toBe(link);
  });

  it('constructor() should initialize dashboard.widgetConfigs', () => {
    let dashboard = new Dashboard(1, 'Testing Dashboard', 'Dashboard for testers', '/api/dashboards/1');
    expect(dashboard.widgetConfigs).toEqual([]);
  });
});
