/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { DrugHistoryComponent } from '../../../../../../main/webapp/app/entities/drug-history/drug-history.component';
import { DrugHistoryService } from '../../../../../../main/webapp/app/entities/drug-history/drug-history.service';
import { DrugHistory } from '../../../../../../main/webapp/app/entities/drug-history/drug-history.model';

describe('Component Tests', () => {

    describe('DrugHistory Management Component', () => {
        let comp: DrugHistoryComponent;
        let fixture: ComponentFixture<DrugHistoryComponent>;
        let service: DrugHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [DrugHistoryComponent],
                providers: [
                    DrugHistoryService
                ]
            })
            .overrideTemplate(DrugHistoryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DrugHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DrugHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new DrugHistory(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.drugHistories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
