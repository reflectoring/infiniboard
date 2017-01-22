import {JenkinsBuildStatusColorPipe} from './jenkins-build-status-color.pipe';
import {BuildStatus} from './build-status.enum';
import {inject} from '@angular/core/testing';

describe('Pipe: BuildStatus', () => {
  it('create an instance', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe).toBeTruthy();

    it('transforms NOT_BUILT', () => {
      expect(pipe.transform(BuildStatus.NOT_BUILT)).toEqual('bg-gray');
    });

    it('transforms DISABLED', () => {
      expect(pipe.transform(BuildStatus.DISABLED)).toEqual('bg-gray');
    });

    it('transforms BLUE', () => {
      expect(pipe.transform(BuildStatus.BLUE)).toEqual('bg-green');
    });

    it('transforms YELLOW', () => {
      expect(pipe.transform(BuildStatus.YELLOW)).toEqual('bg-yellow');
    });

    it('transforms RED', () => {
      expect(pipe.transform(BuildStatus.RED)).toEqual('bg-red');
    });

    it('transforms ABORTED', () => {
      expect(pipe.transform(BuildStatus.ABORTED)).toEqual('bg-gray');
    });

    it('transforms UNKNOWN', () => {
      expect(pipe.transform(BuildStatus.UNKNOWN)).toEqual('bg-red');
    });
  }));
});
