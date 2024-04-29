import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-car-wash-registration',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './car-wash-registration.component.html',
  styleUrl: './car-wash-registration.component.css'
})
export class CarWashRegistrationComponent {
  selectedOrganizationType: number | null = null; // Переменная для хранения выбранного типа организации
  organizationTypes = [ // Тестовые данные о типах организаций
    { id: 1, name: 'Тип 1' },
    { id: 2, name: 'Тип 2' },
    { id: 3, name: 'Тип 3' },
    { id: 4, name: 'Тип 4' },
    { id: 5, name: 'Тип 5' }
  ];
}
