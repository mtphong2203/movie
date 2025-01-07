import { Observable } from "rxjs";
import { IMasterService } from "../master-service.interface";

export interface IUserService extends IMasterService {

    updateInformation(id: string, param: any): Observable<any>;
}