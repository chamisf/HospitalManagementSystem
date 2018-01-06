/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { MedicineDetailComponent } from '../../../../../../main/webapp/app/entities/medicine/medicine-detail.component';
import { MedicineService } from '../../../../../../main/webapp/app/entities/medicine/medicine.service';
import { Medicine } from '../../../../../../main/webapp/app/entities/medicine/medicine.model';

describe('Component Tests', () => {

    describe('Medicine Management Detail Component', () => {
        let comp: MedicineDetailComponent;
        let fixture: ComponentFixture<MedicineDetailComponent>;
        let service: MedicineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [MedicineDetailComponent],
                providers: [
                    MedicineService
                ]
            })
            .overrideTemplate(MedicineDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicineDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicineService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Medicine(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.medicine).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
