import {Injectable} from 'angular2/core';
import {Status} from './status';

@Injectable()
export class StatusService {

  public getPlatformStatus(status: number): Status {

    let stringValue: string = String(status);

    // 2xx: platform available
    if (stringValue[0] === '2') {
      return Status.UP;
    }

    // 503: platform locked / in maintenance mode
    if (status === 503) {
      return Status.MAINTENANCE;
    }

    // unknown state
    return Status.UNKNOWN;
  }
}
