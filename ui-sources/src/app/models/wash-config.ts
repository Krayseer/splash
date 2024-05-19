import {Box} from "./wash-box";
import {WashService} from "./wash-service";

export interface Configuration {
  id: number;
  username: string;
  tin: string;
  typeOrganization: string;
  email: string;
  name: string;
  description: string;
  phoneNumber: string;
  address: string;
  openTime: string;
  closeTime: string;
  services: WashService[];
  boxes: Box[];
  createdAt: string;
}
