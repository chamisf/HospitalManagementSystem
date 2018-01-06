/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { FamilyHistoryComponent } from '../../../../../../main/webapp/app/entities/family-history/family-history.component';
import { FamilyHistoryService } from '../../../../../../main/webapp/app/entities/family-history/family-history.service';
import { FamilyHistory } from '../../../../../../main/webapp/app/entities/family-history/family-history.model';

describe('Component Tests', () => {

    describe('FamilyHistory Management Component', () => {
        let comp: FamilyHistoryComponent;
        let fixture: ComponentFixture<FamilyHistoryComponent>;
        let service: FamilyHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [FamilyHistoryComponent],
                providers: [
                    FamilyHistoryService
                ]
            })
            .overrideTemplate(FamilyHistoryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FamilyHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FamilyHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new FamilyHistory(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.familyHistories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
