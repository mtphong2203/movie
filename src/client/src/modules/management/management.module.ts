import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { RoleListComponent } from './role/role-list.component';
import { CINEMAROOM_SERVICE, MOVIE_SERVICE, MOVIETYPE_SERVICE, ROLE_SERVICE, SCHEDULE_SERVICE, SHOWDATE_SERVICE, USER_SERVICE } from '../../constants/injection.constant';
import { RoleService } from '../../services/role/role-service.service';
import { AccountListComponent } from './account/account-list.component';
import { UserService } from '../../services/user/user-service.service';
import { MovieListComponent } from './movie/movie-list.component';
import { MovieService } from '../../services/movie/movie.service';
import { MovieTypeService } from '../../services/movietype/movietype.service';
import { ScheduleService } from '../../services/schedule/schedule.service';
import { CinemaRoomService } from '../../services/cinemaroom/cinemaroom.service';
import { ShowdateService } from '../../services/showdate/showdate.service';

const routes: Routes = [
  {
    path: 'movies',
    component: MovieListComponent
  },
  {
    path: 'roles',
    component: RoleListComponent
  },
  {
    path: 'accounts',
    component: AccountListComponent
  },
  {
    path: '**',
    redirectTo: 'movies',
    pathMatch: 'full'
  },
]

@NgModule({
  declarations: [],
  providers: [
    {
      provide: ROLE_SERVICE,
      useClass: RoleService
    },
    {
      provide: USER_SERVICE,
      useClass: UserService
    },
    {
      provide: USER_SERVICE,
      useClass: UserService
    },
    {
      provide: MOVIE_SERVICE,
      useClass: MovieService
    },
    {
      provide: MOVIETYPE_SERVICE,
      useClass: MovieTypeService
    },
    {
      provide: SCHEDULE_SERVICE,
      useClass: ScheduleService
    },
    {
      provide: CINEMAROOM_SERVICE,
      useClass: CinemaRoomService
    },
    {
      provide: SHOWDATE_SERVICE,
      useClass: ShowdateService
    }
  ],
  imports: [
    CommonModule, RouterModule.forChild(routes)
  ]
})
export class ManagementModule { }
