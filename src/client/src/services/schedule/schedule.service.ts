import { Injectable } from '@angular/core';
import { MasterService } from '../master-service.service';
import { IScheduleService } from './schedule.interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CinemaRoom } from '../../models/room/cinema-room.model';
import { ShowDateMasterDTO } from '../../models/showdate/showdate.model';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService extends MasterService implements IScheduleService{

  constructor(public override httpClient: HttpClient) {
    super('schedules', httpClient);
  }
  getAllAvailableSchedule(showDate: ShowDateMasterDTO, cinemaRoom: CinemaRoom, movieId: string): Observable<any> {
    return this.httpClient.get(`${this.baseUrl}/available-schedules`);
  }
  
}
