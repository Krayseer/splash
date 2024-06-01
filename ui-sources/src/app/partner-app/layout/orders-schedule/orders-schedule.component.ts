import { Component } from '@angular/core';
import {NgForOf} from "@angular/common";
import {PartnerHeaderComponent} from "../../components/partner-header/partner-header.component";
import {PartnerFooterComponent} from "../../components/partner-footer/partner-footer.component";
import {HttpClient} from "@angular/common/http";
import {switchMap} from "rxjs";
import {Configuration} from "../../../models/wash-config";
import {InvitationDTO} from "../../modals/invitation-modal/invitation-modal.component";

interface Booking {
  boxId: number;
  startTime: string;
  endTime: string;
}

interface Box {
  id: number;
  name: string;
}

export interface OrderDTO {
  id: number;
  username: string;
  carWashId: number;
  boxId: number;
  status: string;
  serviceIds: number[];
  startTime: string;
  endTime: string;
  typePayment: string;
  createdAt: string;
}

@Component({
  selector: 'app-orders-schedule',
  standalone: true,
  imports: [
    NgForOf,
    PartnerHeaderComponent,
    PartnerFooterComponent
  ],
  templateUrl: './orders-schedule.component.html',
  styleUrl: './orders-schedule.component.css'
})
export class OrdersScheduleComponent {

  timeSlots: string[] = [
    '08:00', '09:00', '10:00', '11:00', '12:00',
    '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00'
  ];

  boxes: Box[] = [
    { id: 1, name: 'Бокс 1' },
    { id: 2, name: 'Бокс 2' },
    { id: 3, name: 'Бокс 3' }
  ];

  bookings: Booking[] = [
    { boxId: 1, startTime: '10:00', endTime: '12:00' },
    { boxId: 2, startTime: '15:00', endTime: '18:00' }
  ];

  carWashId!: number;
  orders: OrderDTO[] = [];

  constructor(private http: HttpClient) {
    this.http.get<Configuration>("api/car-wash/configuration").pipe(
      switchMap((configuration: Configuration) => {
        this.carWashId = configuration.id;
        // Выполнить второй запрос, используя ID автомойки
        return this.http.get<OrderDTO[]>("api/order/car-wash/by-date?id=" + this.carWashId + "&date=16-03-2024");
      })
    ).subscribe(
      (orders: OrderDTO[]) => {
        this.orders = orders;
        console.log(this.orders);
        // Дополнительные действия при успешном получении приглашений
      },
      error => {
        console.error('Ошибка при получении заказов', error);
        // Дополнительные действия при ошибке получении приглашений
      }
    );
  }

  isBooked(box: Box, time: string): boolean {
    return this.bookings.some(booking => {
      return booking.boxId === box.id && time >= booking.startTime && time < booking.endTime;
    });
  }

  getDate(order: OrderDTO): string {
    const datePart = order.createdAt.split('T')[0];
    return datePart;
  }

  getTime(order: OrderDTO): string {
    const timePart = order.createdAt.split('T')[1].split('.')[0];
    return timePart;
  }

}
