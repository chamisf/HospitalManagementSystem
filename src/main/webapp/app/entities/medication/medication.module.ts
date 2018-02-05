import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    MedicationService,
    MedicationPopupService,
    MedicationComponent,
    MedicationDetailComponent,
    MedicationDialogComponent,
    MedicationPopupComponent,
    MedicationDeletePopupComponent,
    MedicationDeleteDialogComponent,
    medicationRoute,
    medicationPopupRoute,
    MedicationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...medicationRoute,
    ...medicationPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MedicationComponent,
        MedicationDetailComponent,
        MedicationDialogComponent,
        MedicationDeleteDialogComponent,
        MedicationPopupComponent,
        MedicationDeletePopupComponent,
    ],
    entryComponents: [
        MedicationComponent,
        MedicationDialogComponent,
        MedicationPopupComponent,
        MedicationDeleteDialogComponent,
        MedicationDeletePopupComponent,
    ],
    providers: [
        MedicationService,
        MedicationPopupService,
        MedicationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemMedicationModule {}
