import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MedicationComponent } from './medication.component';
import { MedicationDetailComponent } from './medication-detail.component';
import { MedicationPopupComponent } from './medication-dialog.component';
import { MedicationDeletePopupComponent } from './medication-delete-dialog.component';

@Injectable()
export class MedicationResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const medicationRoute: Routes = [
    {
        path: 'medication',
        component: MedicationComponent,
        resolve: {
            'pagingParams': MedicationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medications'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'medication/:id',
        component: MedicationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medicationPopupRoute: Routes = [
    {
        path: 'medication-new',
        component: MedicationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medication/:id/edit',
        component: MedicationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medication/:id/delete',
        component: MedicationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
