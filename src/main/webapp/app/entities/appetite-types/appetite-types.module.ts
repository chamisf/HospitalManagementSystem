import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    AppetiteTypesService,
    AppetiteTypesPopupService,
    AppetiteTypesComponent,
    AppetiteTypesDetailComponent,
    AppetiteTypesDialogComponent,
    AppetiteTypesPopupComponent,
    AppetiteTypesDeletePopupComponent,
    AppetiteTypesDeleteDialogComponent,
    appetiteTypesRoute,
    appetiteTypesPopupRoute,
    AppetiteTypesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...appetiteTypesRoute,
    ...appetiteTypesPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AppetiteTypesComponent,
        AppetiteTypesDetailComponent,
        AppetiteTypesDialogComponent,
        AppetiteTypesDeleteDialogComponent,
        AppetiteTypesPopupComponent,
        AppetiteTypesDeletePopupComponent,
    ],
    entryComponents: [
        AppetiteTypesComponent,
        AppetiteTypesDialogComponent,
        AppetiteTypesPopupComponent,
        AppetiteTypesDeleteDialogComponent,
        AppetiteTypesDeletePopupComponent,
    ],
    providers: [
        AppetiteTypesService,
        AppetiteTypesPopupService,
        AppetiteTypesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemAppetiteTypesModule {}
