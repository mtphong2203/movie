import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowdateListComponent } from './showdate-list.component';

describe('ShowdateListComponent', () => {
  let component: ShowdateListComponent;
  let fixture: ComponentFixture<ShowdateListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowdateListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowdateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
