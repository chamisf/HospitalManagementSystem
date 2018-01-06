import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HospitalManagementSystemSharedModule } from '../../shared';
import {
    PersonalSocialDetailsService,
    PersonalSocialDetailsPopupService,
    PersonalSocialDetailsComponent,
    PersonalSocialDetailsDetailComponent,
    PersonalSocialDetailsDialogComponent,
    PersonalSocialDetailsPopupComponent,
    PersonalSocialDetailsDeletePopupComponent,
    PersonalSocialDetailsDeleteDialogComponent,
    personalSocialDetailsRoute,
    personalSocialDetailsPopupRoute,
    PersonalSocialDetailsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...personalSocialDetailsRoute,
    ...personalSocialDetailsPopupRoute,
];

@NgModule({
    imports: [
        HospitalManagementSystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PersonalSocialDetailsComponent,
        PersonalSocialDetailsDetailComponent,
        PersonalSocialDetailsDialogComponent,
        PersonalSocialDetailsDeleteDialogComponent,
        PersonalSocialDetailsPopupComponent,
        PersonalSocialDetailsDeletePopupComponent,
    ],
    entryComponents: [
        PersonalSocialDetailsComponent,
        PersonalSocialDetailsDialogComponent,
        PersonalSocialDetailsPopupComponent,
        PersonalSocialDetailsDeleteDialogComponent,
        PersonalSocialDetailsDeletePopupComponent,
    ],
    providers: [
        PersonalSocialDetailsService,
        PersonalSocialDetailsPopupService,
        PersonalSocialDetailsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemPersonalSocialDetailsModule {}
