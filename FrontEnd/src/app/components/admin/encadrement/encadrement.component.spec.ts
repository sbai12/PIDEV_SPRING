import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EncadrementComponent } from './encadrement.component';

describe('EncadrementComponent', () => {
  let component: EncadrementComponent;
  let fixture: ComponentFixture<EncadrementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EncadrementComponent]
    });
    fixture = TestBed.createComponent(EncadrementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
