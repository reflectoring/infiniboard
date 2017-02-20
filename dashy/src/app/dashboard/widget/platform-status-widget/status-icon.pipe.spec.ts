import {StatusIconPipe} from './status-icon.pipe';
import {Status} from './status.enum';

describe('Pipe: StatusIcon', () => {

  it('create an instance', () => {
    const pipe = new StatusIconPipe();
    expect(pipe).toBeTruthy();
  });

  it('transforms UP', () => {
    const pipe = new StatusIconPipe();
    expect(pipe.transform(Status.UP)).toEqual('fa-thumbs-o-up');
  });

  it('transforms MAINTENANCE', () => {
    const pipe = new StatusIconPipe();
    expect(pipe.transform(Status.MAINTENANCE)).toEqual('fa-lock');
  });

  it('transforms UNKNOWN', () => {
    const pipe = new StatusIconPipe();
    expect(pipe.transform(Status.UNKNOWN)).toEqual('fa-question');
  });

  it('transforms DOWN', () => {
    const pipe = new StatusIconPipe();
    expect(pipe.transform(Status.DOWN)).toEqual('fa-remove');
  });

});
