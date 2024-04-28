import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../../models/order";

@Component({
  selector: 'app-order-registration',
  standalone: true,
  imports: [],
  templateUrl: './order-registration.component.html',
  styleUrl: './order-registration.component.css'
})
export class OrderRegistrationComponent {

  constructor(private http: HttpClient) {
  }

  sendOrder() {
    let order: Order = new Order("nikita", "1", "1", "12-01-2023", "creditCard")
    this.http.post("api/order", order).subscribe();
  }
}
