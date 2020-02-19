import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAuthModalComponent } from './admin-auth-modal.component';

describe('AdminAuthModalComponent', () => {
  let component: AdminAuthModalComponent;
  let fixture: ComponentFixture<AdminAuthModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminAuthModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAuthModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
