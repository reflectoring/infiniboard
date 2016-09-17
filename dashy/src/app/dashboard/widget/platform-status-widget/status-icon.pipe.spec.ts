import {StatusIconPipe} from './status-icon.pipe';
import {Status} from './status.enum';

describe('Pipe: StatusIcon', () => {

  it('create an instance', () => {
    let pipe = new StatusIconPipe();
    expect(pipe).toBeTruthy();
  });

  it('transforms UP', () => {
    let pipe = new StatusIconPipe();
    expect(pipe.transform(Status.UP)).toBe('fa-thumbs-o-up');
  });

  it('transforms MAINTENANCE', () => {
    let pipe = new StatusIconPipe();
    expect(pipe.transform(Status.MAINTENANCE)).toBe('fa-lock');
  });

  it('transforms UNKNOWN', () => {
    let pipe = new StatusIconPipe();
    expect(pipe.transform(Status.UNKNOWN)).toBe('fa-question');
  });

  it('transforms DOWN', () => {
    let pipe = new StatusIconPipe();
    expect(pipe.transform(Status.DOWN)).toBe('fa-remove');
  });

});
