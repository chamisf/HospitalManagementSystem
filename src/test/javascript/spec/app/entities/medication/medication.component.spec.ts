/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { MedicationComponent } from '../../../../../../main/webapp/app/entities/medication/medication.component';
import { MedicationService } from '../../../../../../main/webapp/app/entities/medication/medication.service';
import { Medication } from '../../../../../../main/webapp/app/entities/medication/medication.model';

describe('Component Tests', () => {

    describe('Medication Management Component', () => {
        let comp: MedicationComponent;
        let fixture: ComponentFixture<MedicationComponent>;
        let service: MedicationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [MedicationComponent],
                providers: [
                    MedicationService
                ]
            })
            .overrideTemplate(MedicationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Medication(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.medications[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
