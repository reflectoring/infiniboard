import {WidgetConfig} from './widget-config';

describe('WidgetConfig', () => {
  it('should create an instance', () => {
    const widgetConfig = new WidgetConfig('platform-status', 'Test Platform', '/mock/data');
    widgetConfig.titleUrl = 'http://www.foo.bar';
    widgetConfig.description = 'This is our Testplatform';
    expect(widgetConfig).toBeTruthy();
  });

  it('constructor() should set widgetConfig.type', () => {
    const type = 'platform-status';
    const widgetConfig = new WidgetConfig(type, 'Test Platform', '/mock/data');
    expect(widgetConfig.type).toEqual(type);
  });

  it('constructor() should set widgetConfig.title', () => {
    const title = 'Test Platform';
    const widgetConfig = new WidgetConfig('platform-status', title, '/mock/data');
    expect(widgetConfig.title).toEqual(title);
  });

  it('constructor() should set widgetConfig.dataLink', () => {
    const link = '/mock/data';
    const widgetConfig = new WidgetConfig('platform-status', 'Test Platform', link);
    expect(widgetConfig.dataLink).toEqual(link);
  });
});
