import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../../models/user";

@Component({
  selector: 'app-partner-header',
  standalone: true,
  imports: [],
  templateUrl: './partner-header.component.html',
  styleUrl: './partner-header.component.css'
})
export class PartnerHeaderComponent {

  user: User | null = null;

  constructor(private http: HttpClient) {
    this.http.get<User>("auth/user").subscribe(
      (user: User) => {
        this.user = user;
        console.log('User data:', user);
      },
      (error) => {
        this.user = null;
        console.error('Error fetching user data:', error);
      }
    );
  }

}
