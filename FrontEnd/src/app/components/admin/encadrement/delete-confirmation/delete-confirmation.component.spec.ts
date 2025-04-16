import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteEncadrementConfirmationComponent } from './delete-confirmation.component';

describe('DeleteEncadrementConfirmationComponent', () => {
  let component: DeleteEncadrementConfirmationComponent;
  let fixture: ComponentFixture<DeleteEncadrementConfirmationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteEncadrementConfirmationComponent]
    });
    fixture = TestBed.createComponent(DeleteEncadrementConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
