import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternshipChartComponent } from './internship-chart.component';

describe('InternshipChartComponent', () => {
  let component: InternshipChartComponent;
  let fixture: ComponentFixture<InternshipChartComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternshipChartComponent]
    });
    fixture = TestBed.createComponent(InternshipChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
