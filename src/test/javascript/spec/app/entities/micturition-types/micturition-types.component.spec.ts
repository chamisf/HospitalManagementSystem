/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { MicturitionTypesComponent } from '../../../../../../main/webapp/app/entities/micturition-types/micturition-types.component';
import { MicturitionTypesService } from '../../../../../../main/webapp/app/entities/micturition-types/micturition-types.service';
import { MicturitionTypes } from '../../../../../../main/webapp/app/entities/micturition-types/micturition-types.model';

describe('Component Tests', () => {

    describe('MicturitionTypes Management Component', () => {
        let comp: MicturitionTypesComponent;
        let fixture: ComponentFixture<MicturitionTypesComponent>;
        let service: MicturitionTypesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [MicturitionTypesComponent],
                providers: [
                    MicturitionTypesService
                ]
            })
            .overrideTemplate(MicturitionTypesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MicturitionTypesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MicturitionTypesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MicturitionTypes(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.micturitionTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
