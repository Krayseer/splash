import { Component } from '@angular/core';
import {NgIf, NgOptimizedImage} from "@angular/common";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    NgOptimizedImage,
    NgIf
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  user: any = null;

  constructor(private router: Router, private http: HttpClient) {
    http.get('api/auth-server-user').subscribe(
      user => {
        this.user = user;
      }
    );
  }

  goToOrder() {
    this.router.navigate(["/order"]);
  }

  goToMain() {
    this.router.navigate(["/main"]);
  }

  redirectToLogin() {
    this.router.navigate(['/auth']);
  }
}
