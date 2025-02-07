import { TestBed } from '@angular/core/testing';

import { CinemaroomService } from './cinemaroom.service';

describe('CinemaroomService', () => {
  let service: CinemaroomService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CinemaroomService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
