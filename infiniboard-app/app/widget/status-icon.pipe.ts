import {Pipe, PipeTransform} from 'angular2/core';

@Pipe({name: 'statusIcon'})
export class StatusIconPipe implements PipeTransform {

  transform(value: number): string {
    if (value === 200) {
      return 'fa-thumbs-o-up';
    }
    return 'fa-lock';
  }
}
