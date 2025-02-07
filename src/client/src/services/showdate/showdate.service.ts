import { Injectable } from '@angular/core';
import { MasterService } from '../master-service.service';
import { IShowdateService } from './showdate.interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class ShowdateService extends MasterService implements IShowdateService{

  constructor(public override httpClient: HttpClient) {
    super('showDates', httpClient);
  }
  getAllAvailableShowDates(movieId: string): Observable<any> {
    return this.httpClient.get(`${this.baseUrl}/available-showDates`);
  }
}
