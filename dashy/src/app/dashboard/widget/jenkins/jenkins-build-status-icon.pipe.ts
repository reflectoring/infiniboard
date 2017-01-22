import {Pipe, PipeTransform} from '@angular/core';
import {BuildStatus} from './build-status.enum';

@Pipe({
  name: 'jenkinsStatusIcon'
})
export class JenkinsBuildStatusIconPipe implements PipeTransform {

  transform(buildStatus: BuildStatus): string {

    switch (buildStatus) {

      case BuildStatus.NOT_BUILT:
        return 'fa-question';

      case BuildStatus.DISABLED:
        return 'fa-pause';

      case BuildStatus.BLUE:
        return 'fa-thumbs-o-up';

      case BuildStatus.YELLOW:
        return 'fa-warning';

      case BuildStatus.RED:
        return 'fa-remove';

      case BuildStatus.ABORTED:
        return 'fa-question';

      default:
        return 'fa-bomb';
    }
  }

}
