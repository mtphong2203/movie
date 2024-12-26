import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { RoleListComponent } from './role/role-list.component';
import { ROLE_SERVICE } from '../../constants/injection.constant';
import { RoleService } from '../../services/role/role-service.service';

const routes: Routes = [
  {
    path: 'roles',
    component: RoleListComponent
  },

]

@NgModule({
  declarations: [],
  providers: [
    {
      provide: ROLE_SERVICE,
      useClass: RoleService
    }
  ],
  imports: [
    CommonModule, RouterModule.forChild(routes)
  ]
})
export class ManagementModule { }
