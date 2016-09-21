import {StatusIconPipe} from './status-icon.pipe';
import {Status} from './status.enum';

describe('Pipe: StatusIcon', () => {

  it('create an instance', () => {
    let pipe = new StatusIconPipe();
    expect(pipe).toBeTruthy();
  });

  it('transforms UP', () => {
    let pipe = new StatusIconPipe();
    expect(pipe.transform(Status.UP)).toEqual('fa-thumbs-o-up');
  });

  it('transforms MAINTENANCE', () => {
    let pipe = new StatusIconPipe();
    expect(pipe.transform(Status.MAINTENANCE)).toEqual('fa-lock');
  });

  it('transforms UNKNOWN', () => {
    let pipe = new StatusIconPipe();
    expect(pipe.transform(Status.UNKNOWN)).toEqual('fa-question');
  });

  it('transforms DOWN', () => {
    let pipe = new StatusIconPipe();
    expect(pipe.transform(Status.DOWN)).toEqual('fa-remove');
  });

});
