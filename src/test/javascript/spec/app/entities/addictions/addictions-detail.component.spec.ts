/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AddictionsDetailComponent } from '../../../../../../main/webapp/app/entities/addictions/addictions-detail.component';
import { AddictionsService } from '../../../../../../main/webapp/app/entities/addictions/addictions.service';
import { Addictions } from '../../../../../../main/webapp/app/entities/addictions/addictions.model';

describe('Component Tests', () => {

    describe('Addictions Management Detail Component', () => {
        let comp: AddictionsDetailComponent;
        let fixture: ComponentFixture<AddictionsDetailComponent>;
        let service: AddictionsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AddictionsDetailComponent],
                providers: [
                    AddictionsService
                ]
            })
            .overrideTemplate(AddictionsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddictionsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddictionsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Addictions(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.addictions).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
