import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PatientViewComponent} from "./patient-view.component";
import {UserRouteAccessService} from "../../shared";
import {PatientDetailComponent} from "../../entities/patient";
import {PatientComponent} from "../../entities/patient/patient.component";
import {PatientResolvePagingParams} from "../../entities/patient/patient.route";
import {GynaecologicalHistoryDetailComponent} from "../../entities/gynaecological-history";
import {DrugHistoryDetailComponent} from "../../entities/drug-history";
import {AllergyDetailComponent} from "../../entities/allergy";
import {FamilyHistoryDetailComponent} from "../../entities/family-history";
import {PersonalSocialDetailsDetailComponent} from "../../entities/personal-social-details";

const routes: Routes = [
    {
        path: 'patients',
        component: PatientComponent,
        resolve: {
            'pagingParams': PatientResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patients/:id',
        component: PatientViewComponent,
        children: [
            {
                path: 'bio',
                component: PatientDetailComponent
            },
            {
                path: 'personal',
                component: PersonalSocialDetailsDetailComponent
            },
            {
                path: 'gynological',
                component: GynaecologicalHistoryDetailComponent
            },
            {
                path: 'drug-history',
                component: DrugHistoryDetailComponent
            },
            {
                path: 'allergies',
                component: AllergyDetailComponent
            },
            {
                path: 'family',
                component: FamilyHistoryDetailComponent
            },
            {
                path: 'present-illness',
                component: PatientDetailComponent
            },
            {
                path: 'cardiac',
                component: PatientDetailComponent
            }
        ],
        resolve: {
            'pagingParams': PatientResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patients'
        },
        canActivate: [UserRouteAccessService]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PatientViewRoutingModule {
}
