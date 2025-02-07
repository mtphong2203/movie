import { Observable } from "rxjs";
import { IMasterService } from "../master-service.interface";

export interface IShowdateService extends IMasterService{
    getAllAvailableShowDates(movieId: string): Observable<any>;
}
