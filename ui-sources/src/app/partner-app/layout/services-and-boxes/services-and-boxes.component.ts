import { Component } from '@angular/core';
import {PartnerHeaderComponent} from "../../components/partner-header/partner-header.component";
import {PartnerFooterComponent} from "../../components/partner-footer/partner-footer.component";
import {WashService} from "../../../models/wash-service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-services-and-boxes',
  standalone: true,
  imports: [
    PartnerHeaderComponent,
    PartnerFooterComponent,
    NgForOf
  ],
  templateUrl: './services-and-boxes.component.html',
  styleUrl: './services-and-boxes.component.css'
})
export class ServicesAndBoxesComponent {
  services: WashService[] = [
    { id: 1, name: 'Услуга 1', duration: 30, price: 500 },
    { id: 2, name: 'Услуга 2', duration: 60, price: 1000 },
    { id: 3, name: 'Услуга 3', duration: 90, price: 1500 },
    { id: 4, name: 'Услуга 4', duration: 120, price: 2000 },
    { id: 5, name: 'Услуга 5', duration: 150, price: 2500 }
  ];
}
