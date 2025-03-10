import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingsAvailableComponent } from './trainings-available.component';

describe('TrainingsAvailableComponent', () => {
  let component: TrainingsAvailableComponent;
  let fixture: ComponentFixture<TrainingsAvailableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainingsAvailableComponent]
    });
    fixture = TestBed.createComponent(TrainingsAvailableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
