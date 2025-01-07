import { Injectable } from "@angular/core";
import { IAuthService } from "./auth-service.interface";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable, tap } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService implements IAuthService {

    private apiUrl: string = 'http://localhost:8080/api/v1/auth';

    private accessToken: string = '';

    private authenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

    public authenticated$: Observable<boolean> = this.authenticated.asObservable();

    private userInformation: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    public userInformation$: Observable<any> = this.userInformation.asObservable();

    constructor(private httpClient: HttpClient) {
        this.accessToken = localStorage.getItem('accessToken') || '';

        this.authenticated.next(!!this.accessToken);
        const userInformationRaw = localStorage.getItem('userDTO');
        if (userInformationRaw) {
            this.userInformation.next(JSON.parse(userInformationRaw));
        }

    }

    getUserInformation(): any {
        return this.userInformation$;
    }

    logout(): void {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('userDTO');
        localStorage.removeItem('role');

        this.authenticated.next(false);
        this.userInformation.next(null);
    }

    public isAuthenticated(): Observable<boolean> {
        return this.authenticated$;
    }

    public isManager(): boolean {
        const userDTO = JSON.parse(localStorage.getItem('userDTO')?.toString() || '');
        const role = userDTO?.role;
        if (role?.includes('Admin') || role?.includes('Manager') || role == '') {
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
                const userDTO = JSON.stringify(res.userDTO);
                const now = Math.floor(new Date().getTime() / 1000);
                const expireTime = res.expireTime + now;
                if (token != null) {
                    localStorage.setItem('accessToken', token);
                    localStorage.setItem('userDTO', userDTO);
                    localStorage.setItem('expireTime', expireTime);
                }
                this.authenticated.next(true);
                this.userInformation.next(res.userDTO);
            })
        );
    }

    register(param: any): Observable<any> {
        return this.httpClient.post(`${this.apiUrl}/register`, param);
    }

}