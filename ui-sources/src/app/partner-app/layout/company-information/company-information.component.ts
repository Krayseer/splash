import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {PartnerHeaderComponent} from "../../components/partner-header/partner-header.component";
import {PartnerSettingsComponent} from "../../components/partner-settings/partner-settings.component";
import {IConfig, NgxMaskDirective, provideEnvironmentNgxMask, provideNgxMask} from "ngx-mask";
import {bootstrapApplication} from "@angular/platform-browser";
import {AppComponent} from "../../../app.component";
import {FormsModule} from "@angular/forms";
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

  selectedStartTime: string = '';
  selectedEndTime: string = '';
  blockedTimes: string[] = ['14:00-16:00'];
  configuration!: Configuration;
  photos: (string | ArrayBuffer | null)[] = [];
  selectedFiles: File[] = [];

  constructor(private http: HttpClient) {
    this.http.get<Configuration>("api/car-wash/configuration").subscribe(
      (data) => {
        this.configuration = data;
        console.log('Данные успешно загружены:', this.configuration);
      }
    );
  }

  ngOnInit(): void {
    this.loadExistingPhotos();
    }

  isTimeDisabled(time: string): boolean {
    return this.blockedTimes.some(blockedTime => {
      const [start, end] = blockedTime.split('-');
      return time >= start.trim() && time <= end.trim();
    });
  }

  onTimeStartChange(time: string) {
    console.log('Start time:', time);
  }

  onTimeEndChange(time: string) {
    console.log('End time:', time);
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
}
