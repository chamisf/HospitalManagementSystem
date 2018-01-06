/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { SleepTypesComponent } from '../../../../../../main/webapp/app/entities/sleep-types/sleep-types.component';
import { SleepTypesService } from '../../../../../../main/webapp/app/entities/sleep-types/sleep-types.service';
import { SleepTypes } from '../../../../../../main/webapp/app/entities/sleep-types/sleep-types.model';

describe('Component Tests', () => {

    describe('SleepTypes Management Component', () => {
        let comp: SleepTypesComponent;
        let fixture: ComponentFixture<SleepTypesComponent>;
        let service: SleepTypesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [SleepTypesComponent],
                providers: [
                    SleepTypesService
                ]
            })
            .overrideTemplate(SleepTypesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SleepTypesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SleepTypesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SleepTypes(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sleepTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
