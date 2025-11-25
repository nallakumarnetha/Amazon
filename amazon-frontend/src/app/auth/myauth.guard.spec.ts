import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { myauthGuard } from './myauth.guard';

describe('myauthGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => myauthGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
