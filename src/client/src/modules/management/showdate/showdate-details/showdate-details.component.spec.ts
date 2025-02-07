import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowdateDetailsComponent } from './showdate-details.component';

describe('ShowdateDetailsComponent', () => {
  let component: ShowdateDetailsComponent;
  let fixture: ComponentFixture<ShowdateDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowdateDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowdateDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
