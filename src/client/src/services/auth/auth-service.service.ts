import { Injectable } from "@angular/core";
import { IAuthService } from "./auth-service.interface";
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService implements IAuthService {

    private apiUrl: string = 'http://localhost:8080/api/v1/auth';

    private accessToken:string = '';

    constructor(private httpClient: HttpClient) {
        this.accessToken = localStorage.getItem('accessToken') || '';
     }

    public isAuthenticated(): boolean {
        return !!this.accessToken;
    }

    public isManager(): boolean {
        const role = localStorage.getItem('role');

        if(role?.includes('Admin') || role?.includes('Manager') || role == ''){
            return true;
        }
        return false;
    }

    public getAccessToken(): string {
        return this.accessToken;
    }

    login(param: string): Observable<any> {
        return this.httpClient.post(`${this.apiUrl}/login`, param).pipe(
            tap((res: any) => {
                const token = res.accessToken;
                const role = res.userDTO.role;
                const userDTO = res.userDTO;
                if (token != null) {
                    localStorage.setItem('accessToken', token);
                    localStorage.setItem('role', role);
                    localStorage.setItem('userDTO', userDTO);
                }
            })
        );
    }

    register(param: any): Observable<any> {
        return this.httpClient.post(`${this.apiUrl}/register`, param);
    }

}