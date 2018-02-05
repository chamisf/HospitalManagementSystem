/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { BowelHabitsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits-delete-dialog.component';
import { BowelHabitsService } from '../../../../../../main/webapp/app/entities/bowel-habits/bowel-habits.service';

describe('Component Tests', () => {

    describe('BowelHabits Management Delete Component', () => {
        let comp: BowelHabitsDeleteDialogComponent;
        let fixture: ComponentFixture<BowelHabitsDeleteDialogComponent>;
        let service: BowelHabitsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [BowelHabitsDeleteDialogComponent],
                providers: [
                    BowelHabitsService
                ]
            })
            .overrideTemplate(BowelHabitsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BowelHabitsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BowelHabitsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
