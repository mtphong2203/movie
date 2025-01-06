import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { RoleListComponent } from './role/role-list.component';
import { MOVIE_SERVICE, ROLE_SERVICE, USER_SERVICE } from '../../constants/injection.constant';
import { RoleService } from '../../services/role/role-service.service';
import { AccountListComponent } from './account/account-list.component';
import { UserService } from '../../services/user/user-service.service';
import { MovieListComponent } from './movie/movie-list.component';
import { MovieService } from '../../services/movie/movie.service';

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
  redirectTo: 'roles',
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
    }
  ],
  imports: [
    CommonModule, RouterModule.forChild(routes)
  ]
})
export class ManagementModule { }
