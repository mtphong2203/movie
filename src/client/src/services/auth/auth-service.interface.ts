import { Observable } from "rxjs";

export interface IAuthService {

    register(param: any): Observable<any>;

    login(param: string): Observable<any>;

}