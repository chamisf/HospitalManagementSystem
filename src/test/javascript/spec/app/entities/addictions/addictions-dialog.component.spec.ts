/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AddictionsDialogComponent } from '../../../../../../main/webapp/app/entities/addictions/addictions-dialog.component';
import { AddictionsService } from '../../../../../../main/webapp/app/entities/addictions/addictions.service';
import { Addictions } from '../../../../../../main/webapp/app/entities/addictions/addictions.model';

describe('Component Tests', () => {

    describe('Addictions Management Dialog Component', () => {
        let comp: AddictionsDialogComponent;
        let fixture: ComponentFixture<AddictionsDialogComponent>;
        let service: AddictionsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AddictionsDialogComponent],
                providers: [
                    AddictionsService
                ]
            })
            .overrideTemplate(AddictionsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddictionsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddictionsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Addictions(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.addictions = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'addictionsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Addictions();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.addictions = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'addictionsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
