export class SendOrder {
  carWashId: number;
  serviceIds: number[];

  constructor(carWashId: number, serviceIds: number[]) {
    this.carWashId = carWashId;
    this.serviceIds = serviceIds;
  }
}
