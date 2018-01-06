/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { DrugHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/drug-history/drug-history-detail.component';
import { DrugHistoryService } from '../../../../../../main/webapp/app/entities/drug-history/drug-history.service';
import { DrugHistory } from '../../../../../../main/webapp/app/entities/drug-history/drug-history.model';

describe('Component Tests', () => {

    describe('DrugHistory Management Detail Component', () => {
        let comp: DrugHistoryDetailComponent;
        let fixture: ComponentFixture<DrugHistoryDetailComponent>;
        let service: DrugHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [DrugHistoryDetailComponent],
                providers: [
                    DrugHistoryService
                ]
            })
            .overrideTemplate(DrugHistoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DrugHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DrugHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new DrugHistory(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.drugHistory).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
