/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { MedicineComponent } from '../../../../../../main/webapp/app/entities/medicine/medicine.component';
import { MedicineService } from '../../../../../../main/webapp/app/entities/medicine/medicine.service';
import { Medicine } from '../../../../../../main/webapp/app/entities/medicine/medicine.model';

describe('Component Tests', () => {

    describe('Medicine Management Component', () => {
        let comp: MedicineComponent;
        let fixture: ComponentFixture<MedicineComponent>;
        let service: MedicineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [MedicineComponent],
                providers: [
                    MedicineService
                ]
            })
            .overrideTemplate(MedicineComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicineComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicineService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Medicine(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.medicines[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
