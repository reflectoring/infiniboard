# howto create a static widget
The goal of this tutorial is to show how easy it is to create a new widget for infiniboard.
Therefore we create a simple static widget, to show the basic widget concepts.

## prerequisites
* Node 6.9.5 
* installed node dependencies with `npm install`

## component skeleton
angular-cli (ng) provides a scaffolding tool, to generate skeletons for common use cases. <br />
Use the command below to create a new component.
```sh
$ ng generate component dashboard/widget/static-widget
```

Looking at the output, we can see ng not only generating a component but also a test skeleton
`static-widget.component.spec.ts`, a layout file `static-widget.component.html` and a style definition
`static-widget.component.css` file. Also it updates the `app.module.ts` so the component can be used right away.
```
installing component
  create src\app\dashboard\widget\static-widget\static-widget.component.css
  create src\app\dashboard\widget\static-widget\static-widget.component.html
  create src\app\dashboard\widget\static-widget\static-widget.component.spec.ts
  create src\app\dashboard\widget\static-widget\static-widget.component.ts
  update src\app\app.module.ts
```

### from component to widget
Lets switch to the IDE and open up the generated component
`src\app\dashboard\widget\static-widget\static-widget.component.ts`.

First of all remove the `app-` prefix from the selector within the `@Component` section to get an easier to read
component tag.
```typescript
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'static-widget',
  templateUrl: './static-widget.component.html',
  styleUrls: ['./static-widget.component.css']
})
export class StaticWidgetComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
}
```

To add some common widget functionality the class `StaticWidgetComponent` must extend
the `Widget` base class and inject the `WidgetService` within the constructor.
```typescript
import {Component} from '@angular/core';
import {Widget} from '../widget';
import {WidgetService} from '../../shared/widget.service';

@Component({
  selector: 'app-static-widget',
  templateUrl: './static-widget.component.html',
  styleUrls: ['./static-widget.component.css']
})
export class StaticWidgetComponent extends Widget {

  public constructor(private wS: WidgetService) {
    super(wS);
  }
}
```

Looking at the code some might say "*That looks wrong, why do I define a private variable
within the constuctor arguments?*". Thats an excellent question! Let's see what this actually does:

1. creating a private field `wS` of type `WidgetService`
1. adding a constructor parameter with the same name
1. injecting the `WidgetService` into the constructor
1. assigning the constructor argument to the private field

As this is a very commonly used pattern, Angular 2 provides this shorthand which you will get used
to very soon.

## creating a new widget configuration type

To be able to add the new widget to the dashboard we must tell infiniboard the type used by this
widget within the widget configuration.

Therefore open up the component `dashboard-detail.component.ts`.
Looking at the method `getWidgetComponentByType()` you can see all registered widget configuration types.
Let's add our new configuration type `static` and import the used component.
```typescript
import {StaticWidgetComponent} from '../widget/static-widget/static-widget.component';

...

  private getWidgetComponentByType(widgetType: string): Type<any> {
    switch (widgetType) {
      case 'platform-status':
        return PlatformStatusWidgetComponent;

      case 'jenkins':
        return JenkinsWidgetComponent;

      case 'static':
        return StaticWidgetComponent;

      default:
        throw new Error('unknown widget type \'' + widgetType + '\'');
    }
  }
```

Also we must register the new component as a child component within the same class. Therefore add the component class
below `@Component` to the `entryComponents` array.
```typescript
@Component({
  selector: 'dashboard-detail',
  entryComponents: [
    JenkinsWidgetComponent,
    PlatformStatusWidgetComponent,
    StaticWidgetComponent
  ],
  templateUrl: './dashboard-detail.component.html',
  styleUrls: ['./dashboard-detail.component.css']
})
export class DashboardDetailComponent implements OnInit { ... }
```

## start the development server
Follow the [contribution guide](https://github.com/reflectoring/infiniboard/blob/master/CONTRIBUTING.md) to
start the following components:

* mongo db
* quartermaster
* dashy

## add widget to dashboard
Now we're ready to add our custom widget to the dashboard. As there is currently no support for doing this using the UI
we have to use the quartermaster REST API.

Therefore save the simple widget configuration below to a file called
`static-widget.json`.
```json
{
  "type": "static"
}
```

Afterwards post this config to the REST API using curl:
```http
curl -X POST -d @static-widget.json http://localhost:8080/api/dashboards/1/widgets --header "Content-Type:application/json" 
```

Let's see what happend. Open up a browser and navigate to [http://localhost:4200/#/dashboards/1]().
Wow there it is, your first infiniboard widget!

But wait, this doesn't look very nice. Let's polish it a bit. 

## customizing component layout

As we already saw in the beginning `ng` generated layout and styling files alongside with the component.
Let's open them up and give our widget a nicer look and feel.

First let's start with the layout. Open up the layout file `static-widget.component.html` and replace
the generate content with the one below.
```html
<div class="col-md-3">
  <div class="box box-success">
    <div class="box-header with-border">
      <h3 class="box-title">Static Widget</h3>
      <div class="box-tools pull-right">
        <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
        <button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
      </div><!-- /.box-tools -->
    </div><!-- /.box-header -->
    <div class="box-body">
      Look ma, my <span class="highlight">first widget</span>!
    </div><!-- /.box-body -->
  </div><!-- /.box -->
</div>
```

Now switch to the browser and watch your change. Notice that you don't have to reload the page,
as `ng serve` detects changes made to files by it's own, recompiles the app and notifies the browser
which reloads automatically.


## customizing component style

While it's nice to use predefined UI elements, from time to time you will need to write some custom
CSS to style a component. Angular 2 supports per component styling, meaning you can write CSS which
only applies to a single type of component. This way you can write loosely coupled components layouts
in no time.

Let's try this out by adding some CSS within the style file `static-widget.component.css`.
```css
.highlight {
  border-bottom: 2px dashed #00a65a;
}
```

Now you're ready to create working mockups of your widget ideas. So get creative and stay
tuned on how to add dynamic functionality to your widget. This will be explained in a future guide.
