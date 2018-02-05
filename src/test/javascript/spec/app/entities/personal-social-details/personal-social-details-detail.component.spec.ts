/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { PersonalSocialDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/personal-social-details/personal-social-details-detail.component';
import { PersonalSocialDetailsService } from '../../../../../../main/webapp/app/entities/personal-social-details/personal-social-details.service';
import { PersonalSocialDetails } from '../../../../../../main/webapp/app/entities/personal-social-details/personal-social-details.model';

describe('Component Tests', () => {

    describe('PersonalSocialDetails Management Detail Component', () => {
        let comp: PersonalSocialDetailsDetailComponent;
        let fixture: ComponentFixture<PersonalSocialDetailsDetailComponent>;
        let service: PersonalSocialDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [PersonalSocialDetailsDetailComponent],
                providers: [
                    PersonalSocialDetailsService
                ]
            })
            .overrideTemplate(PersonalSocialDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonalSocialDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonalSocialDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PersonalSocialDetails(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.personalSocialDetails).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
