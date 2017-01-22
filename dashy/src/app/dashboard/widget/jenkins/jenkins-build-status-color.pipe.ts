import {Pipe, PipeTransform} from '@angular/core';
import {BuildStatus} from './build-status.enum';

@Pipe({
  name: 'jenkinsStatusColor'
})
export class JenkinsBuildStatusColorPipe implements PipeTransform {

  transform(buildStatus: BuildStatus): string {

    switch (buildStatus) {

      case BuildStatus.NOT_BUILT:
      case BuildStatus.DISABLED:
      case BuildStatus.ABORTED:
        return 'bg-gray';

      case BuildStatus.BLUE:
        return 'bg-green';

      case BuildStatus.YELLOW:
        return 'bg-yellow';

      case BuildStatus.RED:
        return 'bg-red';

      default:
        return 'bg-red';
    }
  }

}
