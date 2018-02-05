/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AllergyDetailComponent } from '../../../../../../main/webapp/app/entities/allergy/allergy-detail.component';
import { AllergyService } from '../../../../../../main/webapp/app/entities/allergy/allergy.service';
import { Allergy } from '../../../../../../main/webapp/app/entities/allergy/allergy.model';

describe('Component Tests', () => {

    describe('Allergy Management Detail Component', () => {
        let comp: AllergyDetailComponent;
        let fixture: ComponentFixture<AllergyDetailComponent>;
        let service: AllergyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AllergyDetailComponent],
                providers: [
                    AllergyService
                ]
            })
            .overrideTemplate(AllergyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AllergyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllergyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Allergy(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.allergy).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
