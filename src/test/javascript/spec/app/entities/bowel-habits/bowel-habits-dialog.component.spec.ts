/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { BowelHabitsDialogComponent } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits-dialog.component';
import { BowelHabitsService } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits.service';
import { BowelHabits } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits.model';

describe('Component Tests', () => {

    describe('BowelHabits Management Dialog Component', () => {
        let comp: BowelHabitsDialogComponent;
        let fixture: ComponentFixture<BowelHabitsDialogComponent>;
        let service: BowelHabitsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [BowelHabitsDialogComponent],
                providers: [
                    BowelHabitsService
                ]
            })
            .overrideTemplate(BowelHabitsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BowelHabitsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BowelHabitsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BowelHabits(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.bowelHabits = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bowelHabitsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BowelHabits();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.bowelHabits = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bowelHabitsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
