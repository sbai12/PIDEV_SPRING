import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteOfferConfirmationComponent } from './delete-confirmation.component';

describe('DeleteOfferConfirmationComponent', () => {
  let component: DeleteOfferConfirmationComponent;
  let fixture: ComponentFixture<DeleteOfferConfirmationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteOfferConfirmationComponent]
    });
    fixture = TestBed.createComponent(DeleteOfferConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
