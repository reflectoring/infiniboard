import {Pipe, PipeTransform} from '@angular/core';
import {BuildStatus} from './build-status.enum';

@Pipe({
  name: 'jenkinsStatusColor'
})
export class JenkinsBuildStatusColorPipe implements PipeTransform {

  transform(buildStatus: BuildStatus): string {

    switch (buildStatus) {

      case BuildStatus.NOT_BUILT:
      case BuildStatus.NOT_BUILT_BUILDING:
      case BuildStatus.DISABLED:
      case BuildStatus.ABORTED:
      case BuildStatus.ABORTED_BUILDING:
        return 'bg-gray';

      case BuildStatus.BLUE:
      case BuildStatus.BLUE_BUILDING:
        return 'bg-green';

      case BuildStatus.YELLOW:
      case BuildStatus.YELLOW_BUILDING:
        return 'bg-yellow';

      case BuildStatus.RED:
      case BuildStatus.RED_BUILDING:
        return 'bg-red';

      default:
        return 'bg-red';
    }
  }

}
