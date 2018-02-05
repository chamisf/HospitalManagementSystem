/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { FamilyHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/family-history/family-history-detail.component';
import { FamilyHistoryService } from '../../../../../../main/webapp/app/entities/family-history/family-history.service';
import { FamilyHistory } from '../../../../../../main/webapp/app/entities/family-history/family-history.model';

describe('Component Tests', () => {

    describe('FamilyHistory Management Detail Component', () => {
        let comp: FamilyHistoryDetailComponent;
        let fixture: ComponentFixture<FamilyHistoryDetailComponent>;
        let service: FamilyHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [FamilyHistoryDetailComponent],
                providers: [
                    FamilyHistoryService
                ]
            })
            .overrideTemplate(FamilyHistoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FamilyHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FamilyHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new FamilyHistory(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.familyHistory).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
