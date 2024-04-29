import {Component, NgModule} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {FooterComponent} from "../../components/footer/footer.component";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {CarWash} from "../../models/car-wash";
import {CommonModule} from "@angular/common";

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
  selectedCarWashId: number | null = null;

  constructor(private http: HttpClient) {
    this.getConfigs();
  }

  selectCarWash(id: number) {
    this.selectedCarWashId = id;
  }

  getConfigs() {
    this.http.get<CarWash[]>("api/configuration/all").subscribe(
      (data) => {
        this.carWashes = data;
      }
    );
  }
}
