/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { GynaecologicalHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history-detail.component';
import { GynaecologicalHistoryService } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history.service';
import { GynaecologicalHistory } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history.model';

describe('Component Tests', () => {

    describe('GynaecologicalHistory Management Detail Component', () => {
        let comp: GynaecologicalHistoryDetailComponent;
        let fixture: ComponentFixture<GynaecologicalHistoryDetailComponent>;
        let service: GynaecologicalHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [GynaecologicalHistoryDetailComponent],
                providers: [
                    GynaecologicalHistoryService
                ]
            })
            .overrideTemplate(GynaecologicalHistoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GynaecologicalHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GynaecologicalHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new GynaecologicalHistory(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.gynaecologicalHistory).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
