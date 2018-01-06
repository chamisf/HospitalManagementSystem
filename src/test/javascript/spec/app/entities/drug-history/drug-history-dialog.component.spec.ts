/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { DrugHistoryDialogComponent } from '../../../../../../main/webapp/app/entities/drug-history/drug-history-dialog.component';
import { DrugHistoryService } from '../../../../../../main/webapp/app/entities/drug-history/drug-history.service';
import { DrugHistory } from '../../../../../../main/webapp/app/entities/drug-history/drug-history.model';
import { PatientService } from '../../../../../../main/webapp/app/entities/patient';

describe('Component Tests', () => {

    describe('DrugHistory Management Dialog Component', () => {
        let comp: DrugHistoryDialogComponent;
        let fixture: ComponentFixture<DrugHistoryDialogComponent>;
        let service: DrugHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [DrugHistoryDialogComponent],
                providers: [
                    PatientService,
                    DrugHistoryService
                ]
            })
            .overrideTemplate(DrugHistoryDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DrugHistoryDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DrugHistoryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DrugHistory(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.drugHistory = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'drugHistoryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DrugHistory();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.drugHistory = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'drugHistoryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
