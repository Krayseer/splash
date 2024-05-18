import { Component } from '@angular/core';
import {PartnerHeaderComponent} from "../../components/partner-header/partner-header.component";
import {PartnerSettingsComponent} from "../../components/partner-settings/partner-settings.component";
import {PartnerFooterComponent} from "../../components/partner-footer/partner-footer.component";
import {NgForOf, NgIf} from "@angular/common";

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

  setActiveTab(tab: string) {
    this.activeTab = tab;
  }
}
