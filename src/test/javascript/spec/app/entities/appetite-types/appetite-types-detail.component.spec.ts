/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AppetiteTypesDetailComponent } from '../../../../../../main/webapp/app/entities/appetite-types/appetite-types-detail.component';
import { AppetiteTypesService } from '../../../../../../main/webapp/app/entities/appetite-types/appetite-types.service';
import { AppetiteTypes } from '../../../../../../main/webapp/app/entities/appetite-types/appetite-types.model';

describe('Component Tests', () => {

    describe('AppetiteTypes Management Detail Component', () => {
        let comp: AppetiteTypesDetailComponent;
        let fixture: ComponentFixture<AppetiteTypesDetailComponent>;
        let service: AppetiteTypesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AppetiteTypesDetailComponent],
                providers: [
                    AppetiteTypesService
                ]
            })
            .overrideTemplate(AppetiteTypesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppetiteTypesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppetiteTypesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new AppetiteTypes(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.appetiteTypes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
