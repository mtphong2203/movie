import { TestBed } from '@angular/core/testing';

import { ShowdateService } from './showdate.service';

describe('ShowdateService', () => {
  let service: ShowdateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShowdateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
