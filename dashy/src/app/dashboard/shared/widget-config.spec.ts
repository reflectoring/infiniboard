import {WidgetConfig} from './widget-config';

describe('WidgetConfig', () => {
  it('should create an instance', () => {
    let widgetConfig = new WidgetConfig('platform-status', 'Test Platform', '/mock/data');
    widgetConfig.titleUrl = 'http://www.foo.bar';
    widgetConfig.description = 'This is our Testplatform';
    expect(widgetConfig).toBeTruthy();
  });

  it('constructor() should set widgetConfig.type', () => {
    let type = 'platform-status';
    let widgetConfig = new WidgetConfig(type, 'Test Platform', '/mock/data');
    expect(widgetConfig.type).toEqual(type);
  });

  it('constructor() should set widgetConfig.title', () => {
    let title = 'Test Platform';
    let widgetConfig = new WidgetConfig('platform-status', title, '/mock/data');
    expect(widgetConfig.title).toEqual(title);
  });

  it('constructor() should set widgetConfig.dataLink', () => {
    let link = '/mock/data';
    let widgetConfig = new WidgetConfig('platform-status', 'Test Platform', link);
    expect(widgetConfig.dataLink).toEqual(link);
  });
});
