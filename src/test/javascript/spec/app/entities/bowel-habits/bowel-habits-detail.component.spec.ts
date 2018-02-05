/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { BowelHabitsDetailComponent } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits-detail.component';
import { BowelHabitsService } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits.service';
import { BowelHabits } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits.model';

describe('Component Tests', () => {

    describe('BowelHabits Management Detail Component', () => {
        let comp: BowelHabitsDetailComponent;
        let fixture: ComponentFixture<BowelHabitsDetailComponent>;
        let service: BowelHabitsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [BowelHabitsDetailComponent],
                providers: [
                    BowelHabitsService
                ]
            })
            .overrideTemplate(BowelHabitsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BowelHabitsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BowelHabitsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new BowelHabits(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bowelHabits).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
