import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PatientViewComponent} from "./patient-view.component";
import {UserRouteAccessService} from "../../shared";
import {PatientDetailComponent} from "../../entities/patient";
import {PatientComponent} from "../../entities/patient/patient.component";
import {PatientResolvePagingParams} from "../../entities/patient/patient.route";
import {GynaecologicalHistoryDetailComponent} from "../../entities/gynaecological-history";
import {DrugHistoryComponent} from "./drug-history/drug-history.component";
import {AllergyComponent, AllergyDetailComponent} from "../../entities/allergy";
import {FamilyHistoryDetailComponent} from "../../entities/family-history";
import {PersonalSocialDetailsDetailComponent} from "../../entities/personal-social-details";
import {AllergiesHistoryComponent} from "./allergies-history/allergies-history.component";
import {AllergyResolvePagingParams} from "../../entities/allergy/allergy.route";
import {DrugHistoryResolvePagingParams} from "../../entities/drug-history/drug-history.route";
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
                path: 'gynaecological',
                component: GynaecologicalHistoryDetailComponent
            },
            {
                path: 'drug-history',
                resolve: {
                    'pagingParams': DrugHistoryResolvePagingParams
                },
                component: DrugHistoryComponent
            },
            {
                path: 'allergies',
                resolve: {
                    'pagingParams': AllergyResolvePagingParams
                },
                component: AllergiesHistoryComponent
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
