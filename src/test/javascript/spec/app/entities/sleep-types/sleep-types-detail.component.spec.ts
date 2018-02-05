/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { SleepTypesDetailComponent } from '../../../../../../main/webapp/app/entities/sleep-types/sleep-types-detail.component';
import { SleepTypesService } from '../../../../../../main/webapp/app/entities/sleep-types/sleep-types.service';
import { SleepTypes } from '../../../../../../main/webapp/app/entities/sleep-types/sleep-types.model';

describe('Component Tests', () => {

    describe('SleepTypes Management Detail Component', () => {
        let comp: SleepTypesDetailComponent;
        let fixture: ComponentFixture<SleepTypesDetailComponent>;
        let service: SleepTypesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [SleepTypesDetailComponent],
                providers: [
                    SleepTypesService
                ]
            })
            .overrideTemplate(SleepTypesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SleepTypesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SleepTypesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SleepTypes(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sleepTypes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
