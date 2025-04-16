import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignCandidateComponent } from './assign-candidate.component';

describe('AssignCandidateComponent', () => {
  let component: AssignCandidateComponent;
  let fixture: ComponentFixture<AssignCandidateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssignCandidateComponent]
    });
    fixture = TestBed.createComponent(AssignCandidateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
