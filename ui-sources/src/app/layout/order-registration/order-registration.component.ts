import {Component, NgModule} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {FooterComponent} from "../../components/footer/footer.component";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {CarWash} from "../../models/car-wash";
import {CommonModule} from "@angular/common";
import {SendOrder} from "../../models/send-order";

@Component({
  selector: 'app-order-registration',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    CommonModule
  ],
  templateUrl: './order-registration.component.html',
  styleUrl: './order-registration.component.css'
})

export class OrderRegistrationComponent{
  carWashes: CarWash[] = [];
  selectedCarWashId: number = -1;
  selectedServicesIndices: number[] = [];

  isVisibleFirst: boolean = true;
  isVisibleSecond: boolean = false;
  isVisibleThird: boolean = false;

  constructor(private http: HttpClient) {
    this.getConfigs();
  }

  selectCarWash(id: number) {
    this.selectedCarWashId = id;
  }

  goToSecondStep() {
    this.isVisibleFirst = false;
    this.isVisibleSecond = true;
  }

  isSelected(index: number) {
    return this.selectedServicesIndices.includes(index);
  }

  toggleServiceSelection(index: number) {
    const isSelected = this.selectedServicesIndices.includes(index);
    if (isSelected) {
      // Если сервис уже выбран, удалить его из массива выбранных сервисов
      this.selectedServicesIndices = this.selectedServicesIndices.filter(i => i !== index);
    } else {
      // Если сервис не выбран, добавить его в массив выбранных сервисов
      this.selectedServicesIndices.push(index);
    }
  }

  getConfigs() {
    this.http.get<CarWash[]>("api/configuration/all").subscribe(
      (data) => {
        this.carWashes = data;
      }
    );
  }

  sendOrder() {
    let order: SendOrder = new SendOrder(this.selectedCarWashId, this.selectedServicesIndices);
    this.http.post("api/order/user", order).subscribe();
    // this.http.post("api/user/set-roles?userId=2", ["ROLE_MANAGER"]).subscribe();
  }
}
