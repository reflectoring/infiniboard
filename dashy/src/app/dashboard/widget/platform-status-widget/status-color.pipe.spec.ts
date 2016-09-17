import {StatusColorPipe} from './status-color.pipe';
import {Status} from './status.enum';

describe('Pipe: StatusColor', () => {

  it('create an instance', () => {
    let pipe = new StatusColorPipe();
    expect(pipe).toBeTruthy();
  });

  it('transforms UP', () => {
    let pipe = new StatusColorPipe();
    expect(pipe.transform(Status.UP)).toEqual('bg-green');
  });

  it('transforms MAINTENANCE', () => {
    let pipe = new StatusColorPipe();
    expect(pipe.transform(Status.MAINTENANCE)).toEqual('bg-yellow');
  });

  it('transforms UNKNOWN', () => {
    let pipe = new StatusColorPipe();
    expect(pipe.transform(Status.UNKNOWN)).toEqual('bg-red');
  });

  it('transforms DOWN', () => {
    let pipe = new StatusColorPipe();
    expect(pipe.transform(Status.DOWN)).toEqual('bg-red');
  });
});
