export class SendOrder {
  carWashId: number;
  servicesId: number[];

  constructor(carWashId: number, servicesId: number[]) {
    this.carWashId = carWashId;
    this.servicesId = servicesId;
  }
}
