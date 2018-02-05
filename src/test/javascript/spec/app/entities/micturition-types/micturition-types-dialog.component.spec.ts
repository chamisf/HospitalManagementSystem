/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { MicturitionTypesDialogComponent } from '../../../../../../main/webapp/app/entities/micturition-types/micturition-types-dialog.component';
import { MicturitionTypesService } from '../../../../../../main/webapp/app/entities/micturition-types/micturition-types.service';
import { MicturitionTypes } from '../../../../../../main/webapp/app/entities/micturition-types/micturition-types.model';

describe('Component Tests', () => {

    describe('MicturitionTypes Management Dialog Component', () => {
        let comp: MicturitionTypesDialogComponent;
        let fixture: ComponentFixture<MicturitionTypesDialogComponent>;
        let service: MicturitionTypesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [MicturitionTypesDialogComponent],
                providers: [
                    MicturitionTypesService
                ]
            })
            .overrideTemplate(MicturitionTypesDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MicturitionTypesDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MicturitionTypesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MicturitionTypes(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.micturitionTypes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'micturitionTypesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MicturitionTypes();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.micturitionTypes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'micturitionTypesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
