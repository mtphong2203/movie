import { Injectable } from "@angular/core";
import { MasterService } from "../master-service.service";
import { IUserService } from "./user-service.interface";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService extends MasterService implements IUserService {

    constructor(protected override httpClient: HttpClient) {
        super('users', httpClient);
    }

    updateInformation(id: string, param: any): Observable<any> {
        return this.httpClient.put(`${this.baseUrl}/update/${id}`, param);
    }
}