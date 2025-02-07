import { Observable } from "rxjs";
import { IMasterService } from "./master-service.interface";
import { BaseService } from "./base-service.service";
import { HttpClient } from "@angular/common/http";

export class MasterService extends BaseService implements IMasterService {

    constructor(protected override apiUrl: String, protected httpClient: HttpClient) {
        super(apiUrl);
    }

    getAll(): Observable<any> {
        return this.httpClient.get(this.baseUrl);
    }
    getById(id: string): Observable<any> {
        return this.httpClient.get(`${this.baseUrl}/${id}`);
    }
    search(param: any): Observable<any> {
        return this.httpClient.get(`${this.baseUrl}/search-paginated`, { params: param });
    }
    create(param: any): Observable<any> {
        return this.httpClient.post(this.baseUrl, param);
    }
    update(id: string, param: any): Observable<any> {
        return this.httpClient.put(`${this.baseUrl}/${id}`, param);
    }
    delete(id: string): Observable<any> {
        return this.httpClient.delete(`${this.baseUrl}/${id}`);
    }

}