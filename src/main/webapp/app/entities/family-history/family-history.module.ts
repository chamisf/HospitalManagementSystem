import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    FamilyHistoryService,
    FamilyHistoryPopupService,
    FamilyHistoryComponent,
    FamilyHistoryDetailComponent,
    FamilyHistoryDialogComponent,
    FamilyHistoryPopupComponent,
    FamilyHistoryDeletePopupComponent,
    FamilyHistoryDeleteDialogComponent,
    familyHistoryRoute,
    familyHistoryPopupRoute,
    FamilyHistoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...familyHistoryRoute,
    ...familyHistoryPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FamilyHistoryComponent,
        FamilyHistoryDetailComponent,
        FamilyHistoryDialogComponent,
        FamilyHistoryDeleteDialogComponent,
        FamilyHistoryPopupComponent,
        FamilyHistoryDeletePopupComponent,
    ],
    entryComponents: [
        FamilyHistoryComponent,
        FamilyHistoryDialogComponent,
        FamilyHistoryPopupComponent,
        FamilyHistoryDeleteDialogComponent,
        FamilyHistoryDeletePopupComponent,
    ],
    providers: [
        FamilyHistoryService,
        FamilyHistoryPopupService,
        FamilyHistoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemFamilyHistoryModule {}
