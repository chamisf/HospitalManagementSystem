import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    GynaecologicalHistoryService,
    GynaecologicalHistoryPopupService,
    GynaecologicalHistoryComponent,
    GynaecologicalHistoryDetailComponent,
    GynaecologicalHistoryDialogComponent,
    GynaecologicalHistoryPopupComponent,
    GynaecologicalHistoryDeletePopupComponent,
    GynaecologicalHistoryDeleteDialogComponent,
    gynaecologicalHistoryRoute,
    gynaecologicalHistoryPopupRoute,
    GynaecologicalHistoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...gynaecologicalHistoryRoute,
    ...gynaecologicalHistoryPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GynaecologicalHistoryComponent,
        GynaecologicalHistoryDetailComponent,
        GynaecologicalHistoryDialogComponent,
        GynaecologicalHistoryDeleteDialogComponent,
        GynaecologicalHistoryPopupComponent,
        GynaecologicalHistoryDeletePopupComponent,
    ],
    entryComponents: [
        GynaecologicalHistoryComponent,
        GynaecologicalHistoryDialogComponent,
        GynaecologicalHistoryPopupComponent,
        GynaecologicalHistoryDeleteDialogComponent,
        GynaecologicalHistoryDeletePopupComponent,
    ],
    providers: [
        GynaecologicalHistoryService,
        GynaecologicalHistoryPopupService,
        GynaecologicalHistoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemGynaecologicalHistoryModule {}
