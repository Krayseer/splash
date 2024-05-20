import { Component } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {PositiveIntegerDirective} from "../../../direcrtives/positive-integer.directive";

@Component({
  selector: 'app-add-service-modal',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    PositiveIntegerDirective,
    FormsModule
  ],
  templateUrl: './add-service-modal.component.html',
  styleUrl: './add-service-modal.component.css'
})
export class AddServiceModalComponent {

  constructor(private dialogRef: MatDialogRef<AddServiceModalComponent>) {}

  close(): void {
    this.dialogRef.close();
  }

  submit(): void {
    // Логика для отправки данных
    this.dialogRef.close();
  }

}
