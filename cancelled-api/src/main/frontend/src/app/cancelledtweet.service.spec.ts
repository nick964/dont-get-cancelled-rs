import { TestBed } from '@angular/core/testing';

import { CancelledtweetService } from './cancelledtweet.service';

describe('CancelledtweetService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CancelledtweetService = TestBed.get(CancelledtweetService);
    expect(service).toBeTruthy();
  });
});
