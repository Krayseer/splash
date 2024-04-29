import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    FormsModule
  ],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css',
})
export class RegistrationComponent {

  registrationForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.registrationForm = this.fb.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.registrationForm.valid) {
      const userData = this.registrationForm.value;
      this.http.post<any>('api/user/register', userData).subscribe(
        response => {
          console.log('Регистрация успешна!', response);
          // Дополнительные действия после успешной регистрации, например, перенаправление на другую страницу
        },
        error => {
          console.error('Ошибка регистрации:', error);
          // Дополнительные действия в случае ошибки регистрации
        }
      );
    } else {
      console.log('Форма невалидна, данные не отправлены');
    }
  }
}
