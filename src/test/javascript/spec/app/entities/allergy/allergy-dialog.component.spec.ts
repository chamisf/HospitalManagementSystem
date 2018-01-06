/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AllergyDialogComponent } from '../../../../../../main/webapp/app/entities/allergy/allergy-dialog.component';
import { AllergyService } from '../../../../../../main/webapp/app/entities/allergy/allergy.service';
import { Allergy } from '../../../../../../main/webapp/app/entities/allergy/allergy.model';
import { AllergyTypeService } from '../../../../../../main/webapp/app/entities/allergy-type';
import { PatientService } from '../../../../../../main/webapp/app/entities/patient';

describe('Component Tests', () => {

    describe('Allergy Management Dialog Component', () => {
        let comp: AllergyDialogComponent;
        let fixture: ComponentFixture<AllergyDialogComponent>;
        let service: AllergyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AllergyDialogComponent],
                providers: [
                    AllergyTypeService,
                    PatientService,
                    AllergyService
                ]
            })
            .overrideTemplate(AllergyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AllergyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllergyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Allergy(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.allergy = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'allergyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Allergy();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.allergy = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'allergyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
