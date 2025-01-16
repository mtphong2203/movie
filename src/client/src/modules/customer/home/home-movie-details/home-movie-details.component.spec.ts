import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeMovieDetailsComponent } from './home-movie-details.component';

describe('HomeMovieDetailsComponent', () => {
  let component: HomeMovieDetailsComponent;
  let fixture: ComponentFixture<HomeMovieDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeMovieDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeMovieDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
