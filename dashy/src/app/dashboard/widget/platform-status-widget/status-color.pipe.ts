import {Pipe, PipeTransform} from '@angular/core';
import {Status} from './status.enum';

@Pipe({
  name: 'statusColor'
})
export class StatusColorPipe implements PipeTransform {

  transform(status: Status): string {

    switch (status) {
      case Status.UP:
        return 'bg-green';

      case Status.MAINTENANCE:
        return 'bg-yellow';

      case Status.UNKNOWN:
        return 'bg-red';

      case Status.DOWN:
        return 'bg-red';

    }

  }
}
