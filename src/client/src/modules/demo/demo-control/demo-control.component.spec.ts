import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemoControlComponent } from './demo-control.component';

describe('DemoControlComponent', () => {
  let component: DemoControlComponent;
  let fixture: ComponentFixture<DemoControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemoControlComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DemoControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
