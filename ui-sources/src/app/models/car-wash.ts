export class CarWash {
  id: number;
  name: string;
  description: string;
  phoneNumber: string;
  address: string;

  constructor(id: number, name: string, description: string, phoneNumber: string, address: string) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }
}
