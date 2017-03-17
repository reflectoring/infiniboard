# create a static widget
Lets assume we want to create the simple divider widget already included in infiniboard.

First create a new component skeleton:
```
$ ng generate component dashboard/widget/divider-widget

```

Now switch to the IDE and open up `src/app/dashboard/widget/divider-widget/divider-widget.ts` and remove the `app-` prefix from the selector within the `@Component` section.
```typescript

@Component({
  selector: 'divider-widget',
  templateUrl: './divider-widget.component.html',
  styleUrls: ['./divider-widget.component.css']
})
export class DividerWidgetComponent implements OnInit { ... }
```

Now let's extends the base widget class and fix the constructor
```typescript
import {Component} from '@angular/core';
import {Widget} from '../../widget';
import {WidgetService} from '../../../shared/widget.service';

@Component({
  selector: 'divider-widget',
  templateUrl: './divider-widget.component.html',
  styleUrls: ['./divider-widget.component.css']
})
export class DividerWidgetComponent extends Widget {

  public constructor(private wS: WidgetService) {
    super(wS);
  }

}
```


To be able to add the new widget to the dashboard we must tell infiniboard the configuration type of this widget.
Therefore we switch to `dashboard-detail.component.ts` and add the new widget configuration type to the switch inside
the method `getWidgetComponentByType()` like shown below. Also be sure to import the used component.
```typescript
import {DividerWidgetComponent} from '../widget/divider-widget/divider-widget.component';

...

  private getWidgetComponentByType(widgetType: string): Type<any> {
    switch (widgetType) {
      case 'platform-status':
        return PlatformStatusWidgetComponent;

      case 'jenkins':
        return JenkinsWidgetComponent;

      case 'divider':
        return DividerWidgetComponent;

      default:
        throw new Error('unknown widget type \'' + widgetType + '\'');
    }
  }
```

Also we must register the new component as a child component:
```typescript
@Component({
  selector: 'dashboard-detail',
  entryComponents: [
    JenkinsWidgetComponent,
    PlatformStatusWidgetComponent,
    DividerWidgetComponent
  ],
  templateUrl: './dashboard-detail.component.html',
  styleUrls: ['./dashboard-detail.component.css']
})
export class DashboardDetailComponent implements OnInit { ... }
```

Now all we have to do is to change the generated HTML and add some CSS to get a nice looking static widget:
```html
<div class="col-md-12">
 <div class="divider-widget"></div>
</div>
```

```css
.divider-widget {
  height: 2px;
  background-color: black;
}
```

Now we're ready to add our custom widget to the dashboard.
Therefore use your favorite REST client (e.g. Chrome plugin DHC) and post this JSON config to quartermaster:
Therefore save this JSON snippet as `divider-widget.json`:
```json
{
  "title": "My Divider",
  "type": "divider"
}
```

Now switch to the containing directory and post the config to quartermaster using curl:
```http
curl -X POST -d @divider-widget.json http://localhost:8080/api/dashboards/1/widgets --header "Content-Type:application/json" 
```

