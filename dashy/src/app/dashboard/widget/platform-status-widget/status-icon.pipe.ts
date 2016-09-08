import {Pipe, PipeTransform} from '@angular/core';
import {Status} from './status.enum';

@Pipe({
  name: 'statusIcon'
})
export class StatusIconPipe implements PipeTransform {

  transform(status: Status): string {

    switch (status) {
      case Status.UP:
        return 'fa-thumbs-o-up';

      case Status.MAINTENANCE:
        return 'fa-lock';

      case Status.UNKNOWN:
        return 'fa-question';

      case Status.DOWN:
        return 'fa-remove';

    }
  }

}
