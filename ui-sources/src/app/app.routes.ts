import { Routes } from '@angular/router';
import {HeaderComponent} from "./components/header/header.component";
import {FooterComponent} from "./components/footer/footer.component";
import {RegistrationComponent} from "./layout/registration/registration.component";
import {MainPageComponent} from "./layout/main-page/main-page.component";
import {OrderRegistrationComponent} from "./layout/order-registration/order-registration.component";
import {AuthorizationComponent} from "./layout/authorization/authorization.component";

export const routes: Routes = [
  {path: 'header', component: HeaderComponent},
  {path: 'footer', component: FooterComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'main', component: MainPageComponent},
  {path: 'order', component: OrderRegistrationComponent},
  {path: 'auth', component: AuthorizationComponent}
];
