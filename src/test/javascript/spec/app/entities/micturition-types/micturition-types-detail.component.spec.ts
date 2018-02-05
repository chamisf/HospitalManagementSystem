/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { HospitalManagementSystemTestModule } from '../../../test.module';
import { MicturitionTypesDetailComponent } from '../../../../../../main/webapp/app/entities/micturition-types/micturition-types-detail.component';
import { MicturitionTypesService } from '../../../../../../main/webapp/app/entities/micturition-types/micturition-types.service';
import { MicturitionTypes } from '../../../../../../main/webapp/app/entities/micturition-types/micturition-types.model';

describe('Component Tests', () => {

    describe('MicturitionTypes Management Detail Component', () => {
        let comp: MicturitionTypesDetailComponent;
        let fixture: ComponentFixture<MicturitionTypesDetailComponent>;
        let service: MicturitionTypesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HospitalManagementSystemTestModule],
                declarations: [MicturitionTypesDetailComponent],
                providers: [
                    MicturitionTypesService
                ]
            })
            .overrideTemplate(MicturitionTypesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MicturitionTypesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MicturitionTypesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MicturitionTypes(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.micturitionTypes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
