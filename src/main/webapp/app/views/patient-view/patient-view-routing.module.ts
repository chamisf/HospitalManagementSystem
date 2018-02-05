import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PatientViewComponent} from "./patient-view.component";
import {UserRouteAccessService} from "../../shared";

const routes: Routes = [{
    path: 'patients/:id',
    component: PatientViewComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Patient Details'
    },
    canActivate: [UserRouteAccessService]
}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PatientViewRoutingModule {
}
