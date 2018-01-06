/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AppetiteTypesComponent } from '../../../../../../main/webapp/app/entities/appetite-types/appetite-types.component';
import { AppetiteTypesService } from '../../../../../../main/webapp/app/entities/appetite-types/appetite-types.service';
import { AppetiteTypes } from '../../../../../../main/webapp/app/entities/appetite-types/appetite-types.model';

describe('Component Tests', () => {

    describe('AppetiteTypes Management Component', () => {
        let comp: AppetiteTypesComponent;
        let fixture: ComponentFixture<AppetiteTypesComponent>;
        let service: AppetiteTypesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AppetiteTypesComponent],
                providers: [
                    AppetiteTypesService
                ]
            })
            .overrideTemplate(AppetiteTypesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppetiteTypesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppetiteTypesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new AppetiteTypes(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.appetiteTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
