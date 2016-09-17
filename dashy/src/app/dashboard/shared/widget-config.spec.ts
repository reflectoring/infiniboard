import {WidgetConfig} from './widget-config';

describe('WidgetConfig', () => {
  it('should create an instance', () => {
    expect(new WidgetConfig('platform-status', 'Test Board', '/mock/data')).toBeTruthy();
  });

  it('constructor() should set widgetConfig.type', () => {
    let type = 'platform-status';
    let widgetConfig = new WidgetConfig(type, 'Test Board', '/mock/data');
    expect(widgetConfig.type).toEqual(type);
  });

  it('constructor() should set widgetConfig.title', () => {
    let title = 'Test Board';
    let widgetConfig = new WidgetConfig('platform-status', title, '/mock/data');
    expect(widgetConfig.title).toEqual(title);
  });

  it('constructor() should set widgetConfig.dataLink', () => {
    let link = '/mock/data';
    let widgetConfig = new WidgetConfig('platform-status', 'Test Board', link);
    expect(widgetConfig.dataLink).toEqual(link);
  });
});
