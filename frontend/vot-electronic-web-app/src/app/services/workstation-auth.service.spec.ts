import { TestBed } from '@angular/core/testing';

import { WorkstationAuthService } from './workstation-auth.service';

describe('WorkstationAuthService', () => {
  let service: WorkstationAuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkstationAuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
