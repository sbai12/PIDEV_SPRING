import { TestBed } from '@angular/core/testing';

import { EncadrementService } from './encadrement.service';

describe('EncadrementService', () => {
  let service: EncadrementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EncadrementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
