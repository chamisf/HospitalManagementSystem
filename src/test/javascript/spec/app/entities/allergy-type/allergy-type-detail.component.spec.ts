/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AllergyTypeDetailComponent } from '../../../../../../main/webapp/app/entities/allergy-type/allergy-type-detail.component';
import { AllergyTypeService } from '../../../../../../main/webapp/app/entities/allergy-type/allergy-type.service';
import { AllergyType } from '../../../../../../main/webapp/app/entities/allergy-type/allergy-type.model';

describe('Component Tests', () => {

    describe('AllergyType Management Detail Component', () => {
        let comp: AllergyTypeDetailComponent;
        let fixture: ComponentFixture<AllergyTypeDetailComponent>;
        let service: AllergyTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AllergyTypeDetailComponent],
                providers: [
                    AllergyTypeService
                ]
            })
            .overrideTemplate(AllergyTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AllergyTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllergyTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new AllergyType(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.allergyType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
