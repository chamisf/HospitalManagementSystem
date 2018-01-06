/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { GynaecologicalHistoryComponent } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history.component';
import { GynaecologicalHistoryService } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history.service';
import { GynaecologicalHistory } from '../../../../../../main/webapp/app/entities/gynaecological-history/gynaecological-history.model';

describe('Component Tests', () => {

    describe('GynaecologicalHistory Management Component', () => {
        let comp: GynaecologicalHistoryComponent;
        let fixture: ComponentFixture<GynaecologicalHistoryComponent>;
        let service: GynaecologicalHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [GynaecologicalHistoryComponent],
                providers: [
                    GynaecologicalHistoryService
                ]
            })
            .overrideTemplate(GynaecologicalHistoryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GynaecologicalHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GynaecologicalHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new GynaecologicalHistory(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.gynaecologicalHistories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
