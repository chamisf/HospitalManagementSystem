import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    MicturitionTypesService,
    MicturitionTypesPopupService,
    MicturitionTypesComponent,
    MicturitionTypesDetailComponent,
    MicturitionTypesDialogComponent,
    MicturitionTypesPopupComponent,
    MicturitionTypesDeletePopupComponent,
    MicturitionTypesDeleteDialogComponent,
    micturitionTypesRoute,
    micturitionTypesPopupRoute,
    MicturitionTypesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...micturitionTypesRoute,
    ...micturitionTypesPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MicturitionTypesComponent,
        MicturitionTypesDetailComponent,
        MicturitionTypesDialogComponent,
        MicturitionTypesDeleteDialogComponent,
        MicturitionTypesPopupComponent,
        MicturitionTypesDeletePopupComponent,
    ],
    entryComponents: [
        MicturitionTypesComponent,
        MicturitionTypesDialogComponent,
        MicturitionTypesPopupComponent,
        MicturitionTypesDeleteDialogComponent,
        MicturitionTypesDeletePopupComponent,
    ],
    providers: [
        MicturitionTypesService,
        MicturitionTypesPopupService,
        MicturitionTypesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemMicturitionTypesModule {}
