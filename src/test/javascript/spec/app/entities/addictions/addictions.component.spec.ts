/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { AddictionsComponent } from '../../../../../../main/webapp/app/entities/addictions/addictions.component';
import { AddictionsService } from '../../../../../../main/webapp/app/entities/addictions/addictions.service';
import { Addictions } from '../../../../../../main/webapp/app/entities/addictions/addictions.model';

describe('Component Tests', () => {

    describe('Addictions Management Component', () => {
        let comp: AddictionsComponent;
        let fixture: ComponentFixture<AddictionsComponent>;
        let service: AddictionsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [AddictionsComponent],
                providers: [
                    AddictionsService
                ]
            })
            .overrideTemplate(AddictionsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddictionsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddictionsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Addictions(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.addictions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
