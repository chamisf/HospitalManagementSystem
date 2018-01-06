/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { GynaecologicalHistoryDialogComponent } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history-dialog.component';
import { GynaecologicalHistoryService } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history.service';
import { GynaecologicalHistory } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history.model';
import { PatientService } from '../../../../../../main/webapp/app/entities/patient';

describe('Component Tests', () => {

    describe('GynaecologicalHistory Management Dialog Component', () => {
        let comp: GynaecologicalHistoryDialogComponent;
        let fixture: ComponentFixture<GynaecologicalHistoryDialogComponent>;
        let service: GynaecologicalHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [GynaecologicalHistoryDialogComponent],
                providers: [
                    PatientService,
                    GynaecologicalHistoryService
                ]
            })
            .overrideTemplate(GynaecologicalHistoryDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GynaecologicalHistoryDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GynaecologicalHistoryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GynaecologicalHistory(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.gynaecologicalHistory = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'gynaecologicalHistoryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GynaecologicalHistory();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.gynaecologicalHistory = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'gynaecologicalHistoryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
