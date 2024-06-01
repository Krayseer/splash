import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {PartnerHeaderComponent} from "../../components/partner-header/partner-header.component";
import {PartnerSettingsComponent} from "../../components/partner-settings/partner-settings.component";
import {IConfig, NgxMaskDirective, provideEnvironmentNgxMask, provideNgxMask} from "ngx-mask";
import {bootstrapApplication} from "@angular/platform-browser";
import {AppComponent} from "../../../app.component";
import {FormBuilder, FormGroup, FormsModule, Validators} from "@angular/forms";
import {MatFormField, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from "@angular/material/datepicker";
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";
import {MatButton} from "@angular/material/button";
import {PartnerFooterComponent} from "../../components/partner-footer/partner-footer.component";
import {HttpClient} from "@angular/common/http";
import {Configuration} from "../../../models/wash-config";
import {NgForOf, NgIf} from "@angular/common";

const maskConfig: Partial<IConfig> = {
  validation: false,
};

bootstrapApplication(AppComponent, {
  providers: [provideEnvironmentNgxMask(maskConfig)]
}).catch((err) => console.error(err));

export interface ConfigurationUpdateRequest {
  name: string;
  description: string;
  phoneNumber: string;
  address: string;
  openTime: string;
  closeTime: string;
  photos: File[];
  managementProcessOrders: boolean;
}

@Component({
  selector: 'app-company-information',
  templateUrl: './company-information.component.html',
  styleUrl: './company-information.component.css',
  standalone: true,
  imports: [
    PartnerHeaderComponent,
    PartnerSettingsComponent,
    NgxMaskDirective,
    FormsModule,
    MatFormField,
    MatInput,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatSuffix,
    MatDatepicker,
    NgxMaterialTimepickerModule,
    MatButton,
    PartnerFooterComponent,
    NgForOf,
    NgIf
  ],
  providers: [provideNgxMask()]
})
export class CompanyInformationComponent implements OnInit {

  name: string = '';
  description: string = '';
  phoneNumber: string = '';
  address: string = '';
  openTime: string = '';
  closeTime: string = '';
  managementProcessOrders: boolean = false;
  selectedFiles: File[] = [];
  photos: (string | ArrayBuffer | null)[] = [];
  data: any;

  constructor(private http: HttpClient, private fb: FormBuilder) {
  }

  getConfigs() {
    this.http.get<Configuration>("api/car-wash/configuration").subscribe(
      (data: Configuration) => {
        this.name = data.name;
        this.description = data.description;
        this.phoneNumber = data.phoneNumber;
        this.address = data.address;
        this.openTime = data.openTime;
        this.closeTime = data.closeTime;
        console.log(this.data);
      }, error => {
        console.log('Ошибка в конфигурации', error);
      }
    );
  }

  ngOnInit(): void {
    this.getConfigs();
    this.loadExistingPhotos();
    }

  onTimeStartChange(time: string) {
    this.openTime = time;
  }

  onTimeEndChange(time: string) {
    this.closeTime = time;
  }

  loadExistingPhotos(): void {
    this.http.get<string[]>(`api/existing-photos`).subscribe(
      (data) => {
        this.photos = data;
      },
      (error) => {
        console.error('Ошибка при загрузке существующих фотографий:', error);
      }
    );
  }

  onFileSelected(event: any): void {
    const files = event.target.files;
    if (files && files.length) {
      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.photos.push(e.target.result);
        };
        this.selectedFiles.push(files[i]);
        reader.readAsDataURL(files[i]);
      }
    }
  }

  triggerFileInput(): void {
    document.getElementById('fileInput')!.click();
  }

  uploadPhotos(): void {
    const formData = new FormData();
    for (let file of this.selectedFiles) {
      formData.append('files', file, file.name);
    }

    this.http.post(`api/upload`, formData).subscribe(
      (response) => {
        console.log('Upload success:', response);
      },
      (error) => {
        console.error('Upload error:', error);
      }
    );
  }

  onSave() {
    const updateRequest: ConfigurationUpdateRequest = {
      name: this.name,
      description: this.description,
      phoneNumber: this.phoneNumber,
      address: this.address,
      openTime: this.openTime,
      closeTime: this.closeTime,
      photos: this.selectedFiles,
      managementProcessOrders: this.managementProcessOrders
    };

    this.http.put("api/car-wash/configuration", updateRequest).subscribe(
      response => {
        console.log('Данные сохранены успешно', response);
      },
      error => {
        console.error('Ошибка при сохранении данных', error);
      }
    );
  }
}
