import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../models/user";

@Component({
  selector: 'app-authorization',
  standalone: true,
  imports: [],
  templateUrl: './authorization.component.html',
  styleUrl: './authorization.component.css'
})
export class AuthorizationComponent {

  user!: User;

  constructor(private http: HttpClient) {
  }

  onClick() {
    this.http.get<User>("api/user").subscribe(
      user => this.user = user
    );
  }
}
