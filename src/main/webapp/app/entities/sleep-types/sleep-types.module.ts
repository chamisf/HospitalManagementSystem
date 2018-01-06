import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    SleepTypesService,
    SleepTypesPopupService,
    SleepTypesComponent,
    SleepTypesDetailComponent,
    SleepTypesDialogComponent,
    SleepTypesPopupComponent,
    SleepTypesDeletePopupComponent,
    SleepTypesDeleteDialogComponent,
    sleepTypesRoute,
    sleepTypesPopupRoute,
    SleepTypesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...sleepTypesRoute,
    ...sleepTypesPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SleepTypesComponent,
        SleepTypesDetailComponent,
        SleepTypesDialogComponent,
        SleepTypesDeleteDialogComponent,
        SleepTypesPopupComponent,
        SleepTypesDeletePopupComponent,
    ],
    entryComponents: [
        SleepTypesComponent,
        SleepTypesDialogComponent,
        SleepTypesPopupComponent,
        SleepTypesDeleteDialogComponent,
        SleepTypesDeletePopupComponent,
    ],
    providers: [
        SleepTypesService,
        SleepTypesPopupService,
        SleepTypesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemSleepTypesModule {}
