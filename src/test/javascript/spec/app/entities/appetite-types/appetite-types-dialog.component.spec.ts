/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AppetiteTypesDialogComponent } from '../../../../../../main/webapp/app/entities/appetite-types/appetite-types-dialog.component';
import { AppetiteTypesService } from '../../../../../../main/webapp/app/entities/appetite-types/appetite-types.service';
import { AppetiteTypes } from '../../../../../../main/webapp/app/entities/appetite-types/appetite-types.model';

describe('Component Tests', () => {

    describe('AppetiteTypes Management Dialog Component', () => {
        let comp: AppetiteTypesDialogComponent;
        let fixture: ComponentFixture<AppetiteTypesDialogComponent>;
        let service: AppetiteTypesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AppetiteTypesDialogComponent],
                providers: [
                    AppetiteTypesService
                ]
            })
            .overrideTemplate(AppetiteTypesDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppetiteTypesDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppetiteTypesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AppetiteTypes(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.appetiteTypes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'appetiteTypesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AppetiteTypes();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.appetiteTypes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'appetiteTypesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
