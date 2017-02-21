import {StatusColorPipe} from './status-color.pipe';
import {Status} from './status.enum';

describe('Pipe: StatusColor', () => {

  it('create an instance', () => {
    const pipe = new StatusColorPipe();
    expect(pipe).toBeTruthy();
  });

  it('transforms UP', () => {
    const pipe = new StatusColorPipe();
    expect(pipe.transform(Status.UP)).toEqual('bg-green');
  });

  it('transforms MAINTENANCE', () => {
    const pipe = new StatusColorPipe();
    expect(pipe.transform(Status.MAINTENANCE)).toEqual('bg-yellow');
  });

  it('transforms UNKNOWN', () => {
    const pipe = new StatusColorPipe();
    expect(pipe.transform(Status.UNKNOWN)).toEqual('bg-red');
  });

  it('transforms DOWN', () => {
    const pipe = new StatusColorPipe();
    expect(pipe.transform(Status.DOWN)).toEqual('bg-red');
  });
});
