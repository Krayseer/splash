import { Component } from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-invitation-modal',
  standalone: true,
  imports: [
    MatDialogContent,
    MatDialogActions,
    MatButton,
    MatDialogTitle,
    FormsModule
  ],
  templateUrl: './invitation-modal.component.html',
  styleUrl: './invitation-modal.component.css'
})
export class InvitationModalComponent {

  email: string = '';
  role: string = '';

  constructor(public dialogRef: MatDialogRef<InvitationModalComponent>) {}

  close(): void {
    this.dialogRef.close();
  }

  submit(): void {
    // Логика для отправки данных
    this.dialogRef.close();
  }
}
