/* tslint:disable:no-unused-variable */

import { addProviders, async, inject } from '@angular/core/testing';
import {Dashboard} from './dashboard';

describe('Dashboard', () => {
  it('should create an instance', () => {
    expect(new Dashboard(1, 'Testing Dashboard', 'Dashboard for testers', '/api/dashboards/1')).toBeTruthy();
  });
});
