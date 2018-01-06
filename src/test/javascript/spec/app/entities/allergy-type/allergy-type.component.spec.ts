/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AllergyTypeComponent } from '../../../../../../main/webapp/app/entities/allergy-type/allergy-type.component';
import { AllergyTypeService } from '../../../../../../main/webapp/app/entities/allergy-type/allergy-type.service';
import { AllergyType } from '../../../../../../main/webapp/app/entities/allergy-type/allergy-type.model';

describe('Component Tests', () => {

    describe('AllergyType Management Component', () => {
        let comp: AllergyTypeComponent;
        let fixture: ComponentFixture<AllergyTypeComponent>;
        let service: AllergyTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AllergyTypeComponent],
                providers: [
                    AllergyTypeService
                ]
            })
            .overrideTemplate(AllergyTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AllergyTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllergyTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new AllergyType(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.allergyTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
