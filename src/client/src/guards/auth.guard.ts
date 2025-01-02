import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from "@angular/router";
import { PERMISSION_SERVICE } from "../constants/injection.constant";

export const canActivateTeam: CanActivateFn = 
( route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const permissionService = inject(PERMISSION_SERVICE);
    return permissionService.canActivate();
}


