import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { ManagerLayoutComponent } from './layouts/manager-layout/manager-layout.component';

export const routes: Routes = [
    {
        path: 'auth',
        component: AuthLayoutComponent,
        loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
    },
    {
        path: 'manager',
        component: ManagerLayoutComponent,
        loadChildren: () => import('./management/management.module').then(m => m.ManagementModule)
    }
];
