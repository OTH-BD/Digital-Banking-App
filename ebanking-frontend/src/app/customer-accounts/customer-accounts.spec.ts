
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerAccountsComponent } from './customer-accounts';

describe('CustomerAccountsComponent', () => {
  let component: CustomerAccountsComponent;
  let fixture: ComponentFixture<CustomerAccountsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerAccountsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerAccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
