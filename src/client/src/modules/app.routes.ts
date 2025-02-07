import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { ManagementLayoutComponent } from './layouts/management-layout/management-layout.component';
import { CustomerLayoutComponent } from './layouts/customer-layout/customer-layout.component';
import { canActivateTeam } from '../guards/auth.guard';

export const routes: Routes = [
    {
        path: 'demo',
        component: CustomerLayoutComponent,
        loadChildren: () => import('./demo/demo.module').then(m => m.DemoModule)
    },
    {
        path: 'auth',
        component: AuthLayoutComponent,
        loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
    },
    {
        path: 'manager',
        component: ManagementLayoutComponent,
        // canActivate: [canActivateTeam],
        loadChildren: () => import('./management/management.module').then(m => m.ManagementModule)
    },
    {
        path: '',
        component: CustomerLayoutComponent,
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule)
    }
];
