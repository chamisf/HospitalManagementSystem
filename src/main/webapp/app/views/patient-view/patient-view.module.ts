import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PatientViewRoutingModule} from './patient-view-routing.module';
import {PatientViewComponent} from './patient-view.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HospitalManagementSystemPatientModule} from '../../entities/patient/patient.module';
import {PatientHeaderComponent} from './patient-header/patient-header.component';
import {AllergiesHistoryComponent} from "./allergies-history/allergies-history.component";
import {HospitalManagementSystemSharedModule} from "../../shared";
import {AllergyTypeResolvePagingParams} from "../../entities/allergy-type";
import {DrugHistoryComponent} from "./drug-history/drug-history.component";
@NgModule({
    imports: [
        CommonModule,
        PatientViewRoutingModule,
        NgbModule.forRoot(),
        HospitalManagementSystemPatientModule,
        HospitalManagementSystemSharedModule
    ],
    declarations: [
        PatientViewComponent, PatientHeaderComponent, AllergiesHistoryComponent,DrugHistoryComponent
    ],
    providers: [
        AllergyTypeResolvePagingParams
    ]
})
export class PatientViewModule {
}
