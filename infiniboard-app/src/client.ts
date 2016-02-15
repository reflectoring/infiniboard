import {ComponentMetadata as Component, ViewMetadata as View} from 'angular2/angular2';

@Component({
  selector: 'client'
})

@View({
  templateUrl: 'client.html'
})

export class Client {

  constructor() {
    console.info('Client Component Mounted Successfully');
  }

}
