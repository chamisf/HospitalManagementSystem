/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { MedicationDetailComponent } from '../../../../../../main/webapp/app/entities/medication/medication-detail.component';
import { MedicationService } from '../../../../../../main/webapp/app/entities/medication/medication.service';
import { Medication } from '../../../../../../main/webapp/app/entities/medication/medication.model';

describe('Component Tests', () => {

    describe('Medication Management Detail Component', () => {
        let comp: MedicationDetailComponent;
        let fixture: ComponentFixture<MedicationDetailComponent>;
        let service: MedicationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [MedicationDetailComponent],
                providers: [
                    MedicationService
                ]
            })
            .overrideTemplate(MedicationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Medication(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.medication).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
