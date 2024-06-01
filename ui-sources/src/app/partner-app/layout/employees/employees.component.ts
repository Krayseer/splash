import { Component } from '@angular/core';
import {PartnerHeaderComponent} from "../../components/partner-header/partner-header.component";
import {PartnerSettingsComponent} from "../../components/partner-settings/partner-settings.component";
import {PartnerFooterComponent} from "../../components/partner-footer/partner-footer.component";
import {NgForOf, NgIf} from "@angular/common";
import {MatDialog} from "@angular/material/dialog";
import {InvitationDTO, InvitationModalComponent} from "../../modals/invitation-modal/invitation-modal.component";
import {HttpClient} from "@angular/common/http";
import {Configuration} from "../../../models/wash-config";
import {switchMap} from "rxjs";

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
    { name: 'Иван Иванов', status: 'Активный', roles: ['Мойщик'], email: 'ivanov@example.com' },
    { name: 'Петр Петров', status: 'Активный', roles: ['Мойщик'], email: 'petrov@example.com' },
    { name: 'Сергей Сергеев', status: 'Активный', roles: ['Стажер'], email: 'sergeev@example.com' },
  ];
  carWashId!: number;
  invitations: InvitationDTO[] = [];

  constructor(public dialog: MatDialog, private http: HttpClient) {
    this.http.get<Configuration>("api/car-wash/configuration").pipe(
      switchMap((configuration: Configuration) => {
        this.carWashId = configuration.id;
        // Выполнить второй запрос, используя ID автомойки
        return this.http.get<InvitationDTO[]>("api/car-wash/invitation/" + this.carWashId);
      })
    ).subscribe(
      (invitations: InvitationDTO[]) => {
        this.invitations = invitations;
        // Дополнительные действия при успешном получении приглашений
      },
      error => {
        console.error('Ошибка при получении приглашений', error);
        // Дополнительные действия при ошибке получении приглашений
      }
    );
  }

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
