import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    BowelHabitsService,
    BowelHabitsPopupService,
    BowelHabitsComponent,
    BowelHabitsDetailComponent,
    BowelHabitsDialogComponent,
    BowelHabitsPopupComponent,
    BowelHabitsDeletePopupComponent,
    BowelHabitsDeleteDialogComponent,
    bowelHabitsRoute,
    bowelHabitsPopupRoute,
    BowelHabitsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bowelHabitsRoute,
    ...bowelHabitsPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BowelHabitsComponent,
        BowelHabitsDetailComponent,
        BowelHabitsDialogComponent,
        BowelHabitsDeleteDialogComponent,
        BowelHabitsPopupComponent,
        BowelHabitsDeletePopupComponent,
    ],
    entryComponents: [
        BowelHabitsComponent,
        BowelHabitsDialogComponent,
        BowelHabitsPopupComponent,
        BowelHabitsDeleteDialogComponent,
        BowelHabitsDeletePopupComponent,
    ],
    providers: [
        BowelHabitsService,
        BowelHabitsPopupService,
        BowelHabitsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemBowelHabitsModule {}
