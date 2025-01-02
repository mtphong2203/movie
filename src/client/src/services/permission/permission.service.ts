import { Injectable, Inject } from "@angular/core";
import { Router } from "@angular/router";
import { AUTH_SERVICE } from "../../constants/injection.constant";
import { IAuthService } from "../auth/auth-service.interface";
import { IPermissionService } from "./permission-service.interface";

@Injectable({
    providedIn: 'root'
})
export class PermissionService implements IPermissionService{
    constructor(@Inject(AUTH_SERVICE) private authService: IAuthService, private router: Router){}

    canActivate():boolean{
        if(this.authService.isAuthenticated()){
            if(this.authService.isManager()){
                return true;
            }
        }
        this.router.navigate(['/about']);
        return false;
    }
}