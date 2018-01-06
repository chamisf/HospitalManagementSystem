/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { PersonalSocialDetailsComponent } from '../../../../../../main/webapp/app/entities/personal-social-details/personal-social-details.component';
import { PersonalSocialDetailsService } from '../../../../../../main/webapp/app/entities/personal-social-details/personal-social-details.service';
import { PersonalSocialDetails } from '../../../../../../main/webapp/app/entities/personal-social-details/personal-social-details.model';

describe('Component Tests', () => {

    describe('PersonalSocialDetails Management Component', () => {
        let comp: PersonalSocialDetailsComponent;
        let fixture: ComponentFixture<PersonalSocialDetailsComponent>;
        let service: PersonalSocialDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [PersonalSocialDetailsComponent],
                providers: [
                    PersonalSocialDetailsService
                ]
            })
            .overrideTemplate(PersonalSocialDetailsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonalSocialDetailsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonalSocialDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PersonalSocialDetails(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.personalSocialDetails[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
