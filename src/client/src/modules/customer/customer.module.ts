import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { USER_SERVICE } from '../../constants/injection.constant';
import { UserService } from '../../services/user/user-service.service';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireStorageModule } from '@angular/fire/compat/storage';
import { environment } from '../../environments/environment';
import { ScheduleComponent } from './schedule/schedule.component';
import { TicketComponent } from './ticket/ticket.component';
import { PromotionComponent } from './promotion/promotion.component';

const routes: Routes = [
  {
    path: 'contact',
    component: ContactComponent,
  },
  {
    path: 'about',
    component: AboutComponent,
  },
  {
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: 'schedule',
    component: ScheduleComponent
  },
  {
    path: 'ticket',
    component: TicketComponent
  },
  {
    path: 'promotion',
    component: PromotionComponent
  },
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];

@NgModule({
  declarations: [],
  providers: [
    {
      provide: USER_SERVICE,
      useClass: UserService
    },
  ],
  imports: [
    CommonModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireStorageModule,
    RouterModule.forChild(routes),
  ]
})
export class CustomerModule { }
