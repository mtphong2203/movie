import { Injectable } from '@angular/core';
import { MasterService } from '../master-service.service';
import { IMovietypeService } from './movietype.interface';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class MovieTypeService extends MasterService implements IMovietypeService{

  constructor(public override httpClient: HttpClient) {
    super('movieTypes', httpClient);
  }
}
