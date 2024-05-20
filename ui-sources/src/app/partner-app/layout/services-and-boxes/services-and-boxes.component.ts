import { Component } from '@angular/core';
import {PartnerHeaderComponent} from "../../components/partner-header/partner-header.component";
import {PartnerFooterComponent} from "../../components/partner-footer/partner-footer.component";
import {WashService} from "../../../models/wash-service";
import {NgForOf, NgIf} from "@angular/common";
import {Box} from "../../../models/wash-box";
import {InvitationModalComponent} from "../../modals/invitation-modal/invitation-modal.component";
import {MatDialog} from "@angular/material/dialog";
import {AddServiceModalComponent} from "../../modals/add-service-modal/add-service-modal.component";
import {AddBoxModalComponent} from "../../modals/add-box-modal/add-box-modal.component";

@Component({
  selector: 'app-services-and-boxes',
  standalone: true,
  imports: [
    PartnerHeaderComponent,
    PartnerFooterComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './services-and-boxes.component.html',
  styleUrl: './services-and-boxes.component.css'
})
export class ServicesAndBoxesComponent {

  activeTab: string = 'tab1';
  services: WashService[] = [
    { id: 1, name: 'Услуга 1', duration: 30, price: 500 },
    { id: 2, name: 'Услуга 2', duration: 60, price: 1000 },
    { id: 3, name: 'Услуга 3', duration: 90, price: 1500 },
    { id: 4, name: 'Услуга 4', duration: 120, price: 2000 },
    { id: 5, name: 'Услуга 5', duration: 150, price: 2500 }
  ];
  boxes: Box[] = [
    {id: 1, name: 'Бокс 1', carWashId: 1},
    {id: 2, name: 'Бокс 2', carWashId: 1},
    {id: 3, name: 'Бокс 3', carWashId: 1}
  ];

  constructor(public dialog: MatDialog) {}

  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  get servicesCount(): number {
    return this.services.length;
  }

  get boxesCount(): number {
    return this.boxes.length;
  }

  openDialogService(): void {
    const dialogRef = this.dialog.open(AddServiceModalComponent, {
      panelClass: 'invitation-modal'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Модальное окно закрыто');
      // Логика после закрытия модального окна, если необходимо
    });
  }

  openDialogBox(): void {
    const dialogRef = this.dialog.open(AddBoxModalComponent, {
      panelClass: 'invitation-modal'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Модальное окно закрыто');
      // Логика после закрытия модального окна, если необходимо
    });
  }

  onAddButtonClick() {
    if (this.activeTab === 'tab1') {
      this.openDialogService();
    } else if (this.activeTab === 'tab2') {
      this.openDialogBox();
    }
  }
}
