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
  }));

  it('transforms NOT_BUILT', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.NOT_BUILT)).toEqual('bg-gray');
  }));

  it('transforms NOT_BUILT_BUILDING', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.NOT_BUILT_BUILDING)).toEqual('bg-gray');
  }));

  it('transforms DISABLED', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.DISABLED)).toEqual('bg-gray');
  }));

  it('transforms DISABLED_BUILDING', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.DISABLED_BUILDING)).toEqual('bg-gray');
  }));

  it('transforms BLUE', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.BLUE)).toEqual('bg-green');
  }));

  it('transforms BLUE_BUILDING', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.BLUE_BUILDING)).toEqual('bg-green');
  }));

  it('transforms YELLOW', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.YELLOW)).toEqual('bg-yellow');
  }));

  it('transforms YELLOW_BUILDING', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.YELLOW_BUILDING)).toEqual('bg-yellow');
  }));

  it('transforms RED', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.RED)).toEqual('bg-red');
  }));

  it('transforms RED_BUILDING', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.RED_BUILDING)).toEqual('bg-red');
  }));

  it('transforms ABORTED', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.ABORTED)).toEqual('bg-gray');
  }));

  it('transforms ABORTED_BUILDING', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.ABORTED_BUILDING)).toEqual('bg-gray');
  }));

  it('transforms UNKNOWN', inject([JenkinsBuildStatusColorPipe], (pipe: JenkinsBuildStatusColorPipe) => {
    expect(pipe.transform(BuildStatus.UNKNOWN)).toEqual('bg-red');
  }));
});
