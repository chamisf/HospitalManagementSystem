import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    DrugHistoryService,
    DrugHistoryPopupService,
    DrugHistoryComponent,
    DrugHistoryDetailComponent,
    DrugHistoryDialogComponent,
    DrugHistoryPopupComponent,
    DrugHistoryDeletePopupComponent,
    DrugHistoryDeleteDialogComponent,
    drugHistoryRoute,
    drugHistoryPopupRoute,
    DrugHistoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...drugHistoryRoute,
    ...drugHistoryPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DrugHistoryComponent,
        DrugHistoryDetailComponent,
        DrugHistoryDialogComponent,
        DrugHistoryDeleteDialogComponent,
        DrugHistoryPopupComponent,
        DrugHistoryDeletePopupComponent,
    ],
    entryComponents: [
        DrugHistoryComponent,
        DrugHistoryDialogComponent,
        DrugHistoryPopupComponent,
        DrugHistoryDeleteDialogComponent,
        DrugHistoryDeletePopupComponent,
    ],
    providers: [
        DrugHistoryService,
        DrugHistoryPopupService,
        DrugHistoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemDrugHistoryModule {}
