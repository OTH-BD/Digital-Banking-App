import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotAuthorized } from './not-authorized';

describe('NotAuthorized', () => {
  let component: NotAuthorized;
  let fixture: ComponentFixture<NotAuthorized>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotAuthorized]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotAuthorized);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
