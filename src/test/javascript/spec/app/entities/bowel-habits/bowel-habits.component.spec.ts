/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { BowelHabitsComponent } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits.component';
import { BowelHabitsService } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits.service';
import { BowelHabits } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits.model';

describe('Component Tests', () => {

    describe('BowelHabits Management Component', () => {
        let comp: BowelHabitsComponent;
        let fixture: ComponentFixture<BowelHabitsComponent>;
        let service: BowelHabitsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [BowelHabitsComponent],
                providers: [
                    BowelHabitsService
                ]
            })
            .overrideTemplate(BowelHabitsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BowelHabitsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BowelHabitsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new BowelHabits(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bowelHabits[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
