import { Component } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {AddServiceModalComponent} from "../add-service-modal/add-service-modal.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PositiveIntegerDirective} from "../../../direcrtives/positive-integer.directive";

@Component({
  selector: 'app-add-box-modal',
  standalone: true,
  imports: [
    FormsModule,
    PositiveIntegerDirective,
    ReactiveFormsModule
  ],
  templateUrl: './add-box-modal.component.html',
  styleUrl: './add-box-modal.component.css'
})
export class AddBoxModalComponent {

  constructor(public dialogRef: MatDialogRef<AddServiceModalComponent>) {}

  close(): void {
    this.dialogRef.close();
  }

  submit(): void {
    // Логика для отправки данных
    this.dialogRef.close();
  }

}
