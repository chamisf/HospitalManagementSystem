import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    MedicineService,
    MedicinePopupService,
    MedicineComponent,
    MedicineDetailComponent,
    MedicineDialogComponent,
    MedicinePopupComponent,
    MedicineDeletePopupComponent,
    MedicineDeleteDialogComponent,
    medicineRoute,
    medicinePopupRoute,
    MedicineResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...medicineRoute,
    ...medicinePopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MedicineComponent,
        MedicineDetailComponent,
        MedicineDialogComponent,
        MedicineDeleteDialogComponent,
        MedicinePopupComponent,
        MedicineDeletePopupComponent,
    ],
    entryComponents: [
        MedicineComponent,
        MedicineDialogComponent,
        MedicinePopupComponent,
        MedicineDeleteDialogComponent,
        MedicineDeletePopupComponent,
    ],
    providers: [
        MedicineService,
        MedicinePopupService,
        MedicineResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemMedicineModule {}
