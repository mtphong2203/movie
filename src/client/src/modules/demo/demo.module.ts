import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DemoControlComponent } from './demo-control/demo-control.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'control',
    component: DemoControlComponent,
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
  ]
})
export class DemoModule { }
