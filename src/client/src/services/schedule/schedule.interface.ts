import { Observable } from "rxjs";
import { IMasterService } from "../master-service.interface";
import { ShowDateMasterDTO } from "../../models/showdate/showdate.model";
import { CinemaRoom } from "../../models/room/cinema-room.model";

export interface IScheduleService extends IMasterService{
    getAllAvailableSchedule(showDate: ShowDateMasterDTO, cinemaRoom: CinemaRoom, movieId: string): Observable<any>;
}
