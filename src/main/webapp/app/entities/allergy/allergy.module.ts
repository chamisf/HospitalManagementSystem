import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    AllergyService,
    AllergyPopupService,
    AllergyComponent,
    AllergyDetailComponent,
    AllergyDialogComponent,
    AllergyPopupComponent,
    AllergyDeletePopupComponent,
    AllergyDeleteDialogComponent,
    allergyRoute,
    allergyPopupRoute,
    AllergyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...allergyRoute,
    ...allergyPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AllergyComponent,
        AllergyDetailComponent,
        AllergyDialogComponent,
        AllergyDeleteDialogComponent,
        AllergyPopupComponent,
        AllergyDeletePopupComponent,
    ],
    entryComponents: [
        AllergyComponent,
        AllergyDialogComponent,
        AllergyPopupComponent,
        AllergyDeleteDialogComponent,
        AllergyDeletePopupComponent,
    ],
    providers: [
        AllergyService,
        AllergyPopupService,
        AllergyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemAllergyModule {}
