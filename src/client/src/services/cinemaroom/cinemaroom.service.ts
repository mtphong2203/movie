import { Injectable } from '@angular/core';
import { MasterService } from '../master-service.service';
import { ICinemaRoomService } from './cinemaroom.interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ShowDateMasterDTO } from '../../models/showdate/showdate.model';

@Injectable()
export class CinemaRoomService extends MasterService implements ICinemaRoomService{

  constructor(public override httpClient: HttpClient) {
    super('rooms', httpClient);
  }
  getAvailableRooms(showDate: ShowDateMasterDTO, movieId: string): Observable<any> {
    return this.httpClient.get(`${this.baseUrl}/available-rooms`);
  }
}
