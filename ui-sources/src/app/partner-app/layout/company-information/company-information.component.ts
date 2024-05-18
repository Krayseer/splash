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
    PartnerFooterComponent
  ],
  providers: [provideNgxMask()]
})
export class CompanyInformationComponent {

  selectedStartTime: string = '';
  selectedEndTime: string = '';
  blockedTimes: string[] = ['14:00-16:00'];

  constructor() {}

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
}
