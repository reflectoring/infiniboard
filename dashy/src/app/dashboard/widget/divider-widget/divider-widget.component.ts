import {Component} from '@angular/core';
import {Widget} from '../widget';
import {WidgetService} from '../../shared/widget.service';

@Component({
  selector: 'divider-widget',
  templateUrl: './divider-widget.component.html',
  styleUrls: ['./divider-widget.component.css']
})
export class DividerWidgetComponent extends Widget {

  constructor(private wS: WidgetService) {
    super(wS);
  }

  updateData(data: any[]) {

  }

  ngOnInit() {
  }

}
