import {StatusColorPipe} from './status-color.pipe';
import {Status} from './status.enum';

describe('Pipe: StatusColor', () => {

  it('create an instance', () => {
    let pipe = new StatusColorPipe();
    expect(pipe).toBeTruthy();
  });

  it('transforms UP', () => {
    let pipe = new StatusColorPipe();
    expect(pipe.transform(Status.UP)).toBe('bg-green');
  });

  it('transforms MAINTENANCE', () => {
    let pipe = new StatusColorPipe();
    expect(pipe.transform(Status.MAINTENANCE)).toBe('bg-yellow');
  });

  it('transforms UNKNOWN', () => {
    let pipe = new StatusColorPipe();
    expect(pipe.transform(Status.UNKNOWN)).toBe('bg-red');
  });

  it('transforms DOWN', () => {
    let pipe = new StatusColorPipe();
    expect(pipe.transform(Status.DOWN)).toBe('bg-red');
  });
});
