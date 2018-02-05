import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PatientViewRoutingModule} from './patient-view-routing.module';
import {PatientViewComponent} from './patient-view.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
    imports: [
        CommonModule,
        PatientViewRoutingModule,
        NgbModule.forRoot()
    ],
    declarations: [PatientViewComponent]
})
export class PatientViewModule {
}
