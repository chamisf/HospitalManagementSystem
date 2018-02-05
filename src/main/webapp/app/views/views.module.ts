import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {PatientViewModule} from "./patient-view/patient-view.module";

@NgModule({
    imports: [
        PatientViewModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemViewsModule {
}
