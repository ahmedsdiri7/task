import { TestBed } from '@angular/core/testing';

import { ContratService } from './task.service';

describe('ContratService', () => {
  let service: ContratService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContratService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
