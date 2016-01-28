import {Component, View, bootstrap} from 'angular2/angular2';
import {Client} from 'client';

@Component({
  selector: 'main'
})

@View({
  directives: [Client],
  template: `
    <client></client>
  `
})

class Main {

}

bootstrap(Main);
