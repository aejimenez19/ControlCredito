import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Prestamista } from './prestamista';

describe('Prestamista', () => {
  let component: Prestamista;
  let fixture: ComponentFixture<Prestamista>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Prestamista]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Prestamista);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
