import { InjectionToken } from "@angular/core";
import { IAuthService } from "../services/auth/auth-service.interface";
import { IRoleService } from "../services/role/role-service.interface";

export const AUTH_SERVICE = new InjectionToken<IAuthService>('AUTH_SERVICE');

export const ROLE_SERVICE = new InjectionToken<IRoleService>('ROLE_SERVICE');