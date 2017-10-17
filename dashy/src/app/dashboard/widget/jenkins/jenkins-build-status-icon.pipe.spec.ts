import {JenkinsBuildStatusIconPipe} from './jenkins-build-status-icon.pipe';
import {BuildStatus} from './build-status.enum';
import {inject, TestBed} from '@angular/core/testing';

describe('Pipe: BuildStatusIcon', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JenkinsBuildStatusIconPipe]
    });
  });

  it('create an instance', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe).toBeTruthy();
  }));

  it('transforms NOT_BUILT', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.NOT_BUILT)).toEqual('fa-question');
  }));

  it('transforms DISABLED', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.DISABLED)).toEqual('fa-pause');
  }));

  it('transforms BLUE', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.BLUE)).toEqual('fa-thumbs-o-up');
  }));

  it('transforms YELLOW', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.YELLOW)).toEqual('fa-warning');
  }));

  it('transforms RED', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.RED)).toEqual('fa-remove');
  }));

  it('transforms ABORTED', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.ABORTED)).toEqual('fa-question');
  }));

  it('transforms UNKNOWN', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.UNKNOWN)).toEqual('fa-bomb');
  }));

  it('transforms NOT_BUILT_BUILDING', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.NOT_BUILT_BUILDING)).toEqual('fa-refresh');
  }));

  it('transforms DISABLED_BUILDING', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.DISABLED_BUILDING)).toEqual('fa-refresh');
  }));

  it('transforms BLUE_BUILDING', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.BLUE_BUILDING)).toEqual('fa-refresh');
  }));

  it('transforms YELLOW_BUILDING', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.YELLOW_BUILDING)).toEqual('fa-refresh');
  }));

  it('transforms RED_BUILDING', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.RED_BUILDING)).toEqual('fa-refresh');
  }));

  it('transforms ABORTED_BUILDING', inject([JenkinsBuildStatusIconPipe], (pipe: JenkinsBuildStatusIconPipe) => {
    expect(pipe.transform(BuildStatus.ABORTED_BUILDING)).toEqual('fa-refresh');
  }));
});
