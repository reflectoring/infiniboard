import {JenkinsBuildStatusIconPipe} from './jenkins-build-status-icon.pipe';
import {BuildStatus} from './build-status.enum';
import {inject} from '@angular/core/testing';

describe('Pipe: BuildStatusImage', () => {
  it('create an instance', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe).toBeTruthy();

    it('transforms NOT_BUILT', () => {
      expect(pipe.transform(BuildStatus.NOT_BUILT)).toEqual('fa-question');
    });

    it('transforms DISABLED', () => {
      expect(pipe.transform(BuildStatus.DISABLED)).toEqual('fa-pause');
    });

    it('transforms BLUE', () => {
      expect(pipe.transform(BuildStatus.BLUE)).toEqual('fa-thumbs-o-up');
    });

    it('transforms YELLOW', () => {
      expect(pipe.transform(BuildStatus.YELLOW)).toEqual('fa-warning');
    });

    it('transforms RED', () => {
      expect(pipe.transform(BuildStatus.RED)).toEqual('fa-remove');
    });

    it('transforms ABORTED', () => {
      expect(pipe.transform(BuildStatus.ABORTED)).toEqual('fa-question');
    });

    it('transforms UNKNOWN', () => {
      expect(pipe.transform(BuildStatus.UNKNOWN)).toEqual('fa-bomb');
    });
  }));
});
