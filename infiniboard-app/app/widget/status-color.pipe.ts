import {Pipe, PipeTransform} from 'angular2/core';

@Pipe({name: 'statusColor'})
export class StatusColorPipe implements PipeTransform {

  transform(value: number): string {
    if (value === 200) {
      return 'bg-green';
    }
    return 'bg-yellow';
  }
}
