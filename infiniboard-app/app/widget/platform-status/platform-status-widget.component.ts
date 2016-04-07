import {Component, ElementRef} from 'angular2/core';

@Component({
  selector: 'platform-status-widget',
  templateUrl: 'app/widget/platform-status/platform-status-widget.component.html'
})
export class PlatformStatusWidgetComponent {

  private elementRef: ElementRef;

  constructor(elementRef: ElementRef) {
    this.elementRef = elementRef;
  }
}
