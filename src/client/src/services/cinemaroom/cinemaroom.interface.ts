import { Observable } from "rxjs";
import { IMasterService } from "../master-service.interface";
import { ShowDateMasterDTO } from "../../models/showdate/showdate.model";

export interface ICinemaRoomService extends IMasterService{
    getAvailableRooms(showDate: ShowDateMasterDTO, movieId: string): Observable<any>;
}
