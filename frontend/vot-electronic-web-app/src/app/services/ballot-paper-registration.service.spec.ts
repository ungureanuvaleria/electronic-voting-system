import { TestBed } from '@angular/core/testing';

import { BallotPaperRegistrationService } from './ballot-paper-registration.service';

describe('BallotPaperRegistrationService', () => {
  let service: BallotPaperRegistrationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BallotPaperRegistrationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
