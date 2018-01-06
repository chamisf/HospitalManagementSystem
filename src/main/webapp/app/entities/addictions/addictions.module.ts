import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    AddictionsService,
    AddictionsPopupService,
    AddictionsComponent,
    AddictionsDetailComponent,
    AddictionsDialogComponent,
    AddictionsPopupComponent,
    AddictionsDeletePopupComponent,
    AddictionsDeleteDialogComponent,
    addictionsRoute,
    addictionsPopupRoute,
    AddictionsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...addictionsRoute,
    ...addictionsPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AddictionsComponent,
        AddictionsDetailComponent,
        AddictionsDialogComponent,
        AddictionsDeleteDialogComponent,
        AddictionsPopupComponent,
        AddictionsDeletePopupComponent,
    ],
    entryComponents: [
        AddictionsComponent,
        AddictionsDialogComponent,
        AddictionsPopupComponent,
        AddictionsDeleteDialogComponent,
        AddictionsDeletePopupComponent,
    ],
    providers: [
        AddictionsService,
        AddictionsPopupService,
        AddictionsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemAddictionsModule {}
