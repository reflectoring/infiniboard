import {Injectable} from 'angular2/core';
import {WIDGET_DATA} from './mock-widget-data';


@Injectable()
export class WidgetService {

  public getWidgetData(widgetId: string) {
    return Promise.resolve(WIDGET_DATA).then(
      widgets => widgets.filter(widget => widget.widgetId === widgetId)[0]
    );

  }
}
