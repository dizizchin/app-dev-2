import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModkitsComponent } from './modkits.component';

describe('ModkitsComponent', () => {
  let component: ModkitsComponent;
  let fixture: ComponentFixture<ModkitsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModkitsComponent]
    });
    fixture = TestBed.createComponent(ModkitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
