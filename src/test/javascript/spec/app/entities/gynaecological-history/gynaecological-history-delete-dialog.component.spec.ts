/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { GynaecologicalHistoryDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history-delete-dialog.component';
import { GynaecologicalHistoryService } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history.service';

describe('Component Tests', () => {

    describe('GynaecologicalHistory Management Delete Component', () => {
        let comp: GynaecologicalHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<GynaecologicalHistoryDeleteDialogComponent>;
        let service: GynaecologicalHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [GynaecologicalHistoryDeleteDialogComponent],
                providers: [
                    GynaecologicalHistoryService
                ]
            })
            .overrideTemplate(GynaecologicalHistoryDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GynaecologicalHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GynaecologicalHistoryService);
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
