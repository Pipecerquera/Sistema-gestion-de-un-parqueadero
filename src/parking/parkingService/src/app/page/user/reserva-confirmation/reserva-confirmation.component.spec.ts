import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservaConfirmationComponent } from './reserva-confirmation.component';

describe('ReservaConfirmationComponent', () => {
  let component: ReservaConfirmationComponent;
  let fixture: ComponentFixture<ReservaConfirmationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservaConfirmationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReservaConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
