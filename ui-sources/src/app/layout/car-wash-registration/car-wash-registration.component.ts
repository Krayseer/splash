import { Component } from '@angular/core';
import {FormsModule, NgForm} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {NgClass, NgIf} from "@angular/common";

export interface ConfigurationRegisterRequest {
  tin: string;
  typeOrganization: string; // Assuming TypeOrganization is a string, otherwise import and use the correct type
  email: string;
}

@Component({
  selector: 'app-car-wash-registration',
  standalone: true,
  imports: [
    FormsModule,
    NgClass,
    NgIf
  ],
  templateUrl: './car-wash-registration.component.html',
  styleUrl: './car-wash-registration.component.css'
})
export class CarWashRegistrationComponent {

  constructor(private http: HttpClient) {}

  onSubmit(form: NgForm) {
    if (form.valid) {
      const registrationData: ConfigurationRegisterRequest = {
        tin: form.value.tin,
        typeOrganization: form.value.typeOrganization,
        email: form.value.email
      };

      this.http.post<any>(`api/car-wash/configuration`, registrationData).subscribe(
        response => {
          console.log('Registration successful:', response);
        },
        error => {
          console.error('Registration error:', error);
        }
      );
    }
  }

}
