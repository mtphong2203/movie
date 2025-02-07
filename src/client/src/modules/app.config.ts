import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { AUTH_SERVICE, PERMISSION_SERVICE } from '../constants/injection.constant';
import { AuthService } from '../services/auth/auth-service.service';
import { authInterceptor } from '../interceptors/auth.interceptor';
import { PermissionService } from '../services/permission/permission.service';

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes), 
    provideHttpClient(withInterceptors([authInterceptor]), withFetch()),
  {
    provide: AUTH_SERVICE,
    useClass: AuthService
  },
  {
    provide: PERMISSION_SERVICE,
    useClass: PermissionService
  }
  ]
};
