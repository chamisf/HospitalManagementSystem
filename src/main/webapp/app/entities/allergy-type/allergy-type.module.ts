import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    AllergyTypeService,
    AllergyTypePopupService,
    AllergyTypeComponent,
    AllergyTypeDetailComponent,
    AllergyTypeDialogComponent,
    AllergyTypePopupComponent,
    AllergyTypeDeletePopupComponent,
    AllergyTypeDeleteDialogComponent,
    allergyTypeRoute,
    allergyTypePopupRoute,
    AllergyTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...allergyTypeRoute,
    ...allergyTypePopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AllergyTypeComponent,
        AllergyTypeDetailComponent,
        AllergyTypeDialogComponent,
        AllergyTypeDeleteDialogComponent,
        AllergyTypePopupComponent,
        AllergyTypeDeletePopupComponent,
    ],
    entryComponents: [
        AllergyTypeComponent,
        AllergyTypeDialogComponent,
        AllergyTypePopupComponent,
        AllergyTypeDeleteDialogComponent,
        AllergyTypeDeletePopupComponent,
    ],
    providers: [
        AllergyTypeService,
        AllergyTypePopupService,
        AllergyTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemAllergyTypeModule {}
