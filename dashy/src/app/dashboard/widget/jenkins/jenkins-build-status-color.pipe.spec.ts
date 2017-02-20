import {JenkinsBuildStatusColorPipe} from './jenkins-build-status-color.pipe';
import {BuildStatus} from './build-status.enum';
import {inject, TestBed} from '@angular/core/testing';

describe('Pipe: BuildStatusColor', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JenkinsBuildStatusColorPipe]
    });
  });

  it('create an instance', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe).toBeTruthy();

    it('transforms NOT_BUILT', () => {
      expect(pipe.transform(BuildStatus.NOT_BUILT)).toEqual('bg-gray');
    });

    it('transforms NOT_BUILT_BUILDING', () => {
      expect(pipe.transform(BuildStatus.NOT_BUILT_BUILDING)).toEqual('bg-gray');
    });

    it('transforms DISABLED', () => {
      expect(pipe.transform(BuildStatus.DISABLED)).toEqual('bg-gray');
    });

    it('transforms BLUE', () => {
      expect(pipe.transform(BuildStatus.BLUE)).toEqual('bg-green');
    });

    it('transforms BLUE_BUILDING', () => {
      expect(pipe.transform(BuildStatus.BLUE_BUILDING)).toEqual('bg-green');
    });

    it('transforms YELLOW', () => {
      expect(pipe.transform(BuildStatus.YELLOW)).toEqual('bg-yellow');
    });

    it('transforms YELLOW_BUILDING', () => {
      expect(pipe.transform(BuildStatus.YELLOW_BUILDING)).toEqual('bg-yellow');
    });

    it('transforms RED', () => {
      expect(pipe.transform(BuildStatus.RED)).toEqual('bg-red');
    });

    it('transforms RED_BUILDING', () => {
      expect(pipe.transform(BuildStatus.RED_BUILDING)).toEqual('bg-red');
    });

    it('transforms ABORTED', () => {
      expect(pipe.transform(BuildStatus.ABORTED)).toEqual('bg-gray');
    });

    it('transforms ABORTED_BUILDING', () => {
      expect(pipe.transform(BuildStatus.ABORTED_BUILDING)).toEqual('bg-gray');
    });

    it('transforms UNKNOWN', () => {
      expect(pipe.transform(BuildStatus.UNKNOWN)).toEqual('bg-red');
    });
  }));
});
