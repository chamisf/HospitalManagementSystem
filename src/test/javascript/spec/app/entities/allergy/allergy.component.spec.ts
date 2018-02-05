/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AllergyComponent } from '../../../../../../main/webapp/app/entities/allergy/allergy.component';
import { AllergyService } from '../../../../../../main/webapp/app/entities/allergy/allergy.service';
import { Allergy } from '../../../../../../main/webapp/app/entities/allergy/allergy.model';

describe('Component Tests', () => {

    describe('Allergy Management Component', () => {
        let comp: AllergyComponent;
        let fixture: ComponentFixture<AllergyComponent>;
        let service: AllergyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AllergyComponent],
                providers: [
                    AllergyService
                ]
            })
            .overrideTemplate(AllergyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AllergyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllergyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Allergy(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.allergies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
