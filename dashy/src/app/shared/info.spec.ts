import {Info} from './info';

describe('Info', () => {
  it('should create an instance', () => {
    expect(new Info('1.2.3.123')).toBeTruthy();
  });

  it('should set the server version', () => {
    expect(new Info('1.2.3.123').version).toBe('1.2.3.123');
  });
});
