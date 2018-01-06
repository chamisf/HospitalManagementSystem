/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { MedicationDialogComponent } from '../../../../../../main/webapp/app/entities/medication/medication-dialog.component';
import { MedicationService } from '../../../../../../main/webapp/app/entities/medication/medication.service';
import { Medication } from '../../../../../../main/webapp/app/entities/medication/medication.model';
import { DrugHistoryService } from '../../../../../../main/webapp/app/entities/drug-history';
import { MedicineService } from '../../../../../../main/webapp/app/entities/medicine';

describe('Component Tests', () => {

    describe('Medication Management Dialog Component', () => {
        let comp: MedicationDialogComponent;
        let fixture: ComponentFixture<MedicationDialogComponent>;
        let service: MedicationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [MedicationDialogComponent],
                providers: [
                    DrugHistoryService,
                    MedicineService,
                    MedicationService
                ]
            })
            .overrideTemplate(MedicationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedicationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Medication(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.medication = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'medicationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Medication();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.medication = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'medicationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
