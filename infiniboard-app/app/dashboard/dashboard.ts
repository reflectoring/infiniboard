export class Dashboard {

  public id: number;
  public name: String;
  public active: boolean;

  constructor(_id: number, _name: String, _active: boolean) {
    this.id = _id;
    this.name = _name;
    this.active = _active;
  }
}
