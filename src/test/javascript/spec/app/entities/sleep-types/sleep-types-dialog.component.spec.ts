/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { SleepTypesDialogComponent } from '../../../../../../main/webapp/app/entities/sleep-types/sleep-types-dialog.component';
import { SleepTypesService } from '../../../../../../main/webapp/app/entities/sleep-types/sleep-types.service';
import { SleepTypes } from '../../../../../../main/webapp/app/entities/sleep-types/sleep-types.model';

describe('Component Tests', () => {

    describe('SleepTypes Management Dialog Component', () => {
        let comp: SleepTypesDialogComponent;
        let fixture: ComponentFixture<SleepTypesDialogComponent>;
        let service: SleepTypesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [SleepTypesDialogComponent],
                providers: [
                    SleepTypesService
                ]
            })
            .overrideTemplate(SleepTypesDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SleepTypesDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SleepTypesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SleepTypes(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.sleepTypes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sleepTypesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SleepTypes();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.sleepTypes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sleepTypesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
