import { DashyPage } from './app.po';

describe('dashy App', function() {
  let page: DashyPage;

  beforeEach(() => {
    page = new DashyPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
