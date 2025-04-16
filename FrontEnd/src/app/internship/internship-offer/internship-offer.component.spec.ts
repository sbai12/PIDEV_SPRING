import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternshipOfferUserComponent } from './internship-offer.component';

describe('InternshipOfferUserComponent', () => {
  let component: InternshipOfferUserComponent;
  let fixture: ComponentFixture<InternshipOfferUserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternshipOfferUserComponent]
    });
    fixture = TestBed.createComponent(InternshipOfferUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
