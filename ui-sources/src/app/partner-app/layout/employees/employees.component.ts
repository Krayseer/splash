import { Component } from '@angular/core';
import {PartnerHeaderComponent} from "../../components/partner-header/partner-header.component";
import {PartnerSettingsComponent} from "../../components/partner-settings/partner-settings.component";
import {PartnerFooterComponent} from "../../components/partner-footer/partner-footer.component";
import {NgForOf, NgIf} from "@angular/common";
import {MatDialog} from "@angular/material/dialog";
import {InvitationModalComponent} from "../../modals/invitation-modal/invitation-modal.component";

interface Employee {
  name: string;
  status: string;
  roles: string[];
  email: string;
}

@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [
    PartnerHeaderComponent,
    PartnerSettingsComponent,
    PartnerFooterComponent,
    NgIf,
    NgForOf
  ],
  templateUrl: './employees.component.html',
  styleUrl: './employees.component.css'
})
export class EmployeesComponent {

  activeTab: string = 'tab1';
  employees: Employee[] = [
    { name: 'Иван Иванов', status: 'Активный', roles: ['Разработчик', 'Аналитик'], email: 'ivanov@example.com' },
    { name: 'Петр Петров', status: 'Неактивный', roles: ['Тестировщик'], email: 'petrov@example.com' },
    { name: 'Сергей Сергеев', status: 'Активный', roles: ['Менеджер', 'Системный администратор'], email: 'sergeev@example.com' },
  ];

  constructor(public dialog: MatDialog) {}

  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(InvitationModalComponent, {
      panelClass: 'invitation-modal'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Модальное окно закрыто');
      // Логика после закрытия модального окна, если необходимо
    });
  }
}
