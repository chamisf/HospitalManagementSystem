/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { MedicineDialogComponent } from '../../../../../../main/webapp/app/entities/medicine/medicine-dialog.component';
import { MedicineService } from '../../../../../../main/webapp/app/entities/medicine/medicine.service';
import { Medicine } from '../../../../../../main/webapp/app/entities/medicine/medicine.model';

describe('Component Tests', () => {

    describe('Medicine Management Dialog Component', () => {
        let comp: MedicineDialogComponent;
        let fixture: ComponentFixture<MedicineDialogComponent>;
        let service: MedicineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [MedicineDialogComponent],
                providers: [
                    MedicineService
                ]
            })
            .overrideTemplate(MedicineDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicineDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicineService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Medicine(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.medicine = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'medicineListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Medicine();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.medicine = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'medicineListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
