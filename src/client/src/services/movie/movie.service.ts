import { Injectable } from '@angular/core';
import { MasterService } from '../master-service.service';
import { IMovie } from './movie.interface';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class MovieService extends MasterService implements IMovie{

  constructor(public override httpClient: HttpClient) {
    super('movies', httpClient);
  }
}
