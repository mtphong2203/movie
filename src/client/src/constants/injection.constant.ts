import { InjectionToken } from "@angular/core";
import { IAuthService } from "../services/auth/auth-service.interface";
import { IRoleService } from "../services/role/role-service.interface";
import { IUserService } from "../services/user/user-service.interface";
import { IPermissionService } from "../services/permission/permission-service.interface";

export const AUTH_SERVICE = new InjectionToken<IAuthService>('AUTH_SERVICE');

export const ROLE_SERVICE = new InjectionToken<IRoleService>('ROLE_SERVICE');

export const USER_SERVICE = new InjectionToken<IUserService>('USER_SERVICE');

export const PERMISSION_SERVICE = new InjectionToken<IPermissionService>('PERMISSION_SERVICE');