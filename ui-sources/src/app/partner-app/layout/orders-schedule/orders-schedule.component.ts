import { Component } from '@angular/core';
import {NgForOf} from "@angular/common";
import {PartnerHeaderComponent} from "../../components/partner-header/partner-header.component";
import {PartnerFooterComponent} from "../../components/partner-footer/partner-footer.component";

interface Booking {
  boxId: number;
  startTime: string;
  endTime: string;
}

interface Box {
  id: number;
  name: string;
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

  constructor() {}

  isBooked(box: Box, time: string): boolean {
    return this.bookings.some(booking => {
      return booking.boxId === box.id && time >= booking.startTime && time < booking.endTime;
    });
  }

}
